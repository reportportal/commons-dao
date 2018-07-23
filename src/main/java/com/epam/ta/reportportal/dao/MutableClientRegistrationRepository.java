package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.oauth.OAuthRegistration;
import com.epam.ta.reportportal.entity.oauth.OAuthRegistrationScope;
import com.epam.ta.reportportal.exception.DataStorageException;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MutableClientRegistrationRepository implements ClientRegistrationRepository {

	private OAuthRegistrationRepository oAuthRegistrationRepository;

	public static final Collector<ClientRegistration, ?, Map<String, ClientRegistration>> KEY_MAPPER = Collectors.toMap(ClientRegistration::getRegistrationId,
			r -> r
	);

	public static final Function<OAuthRegistration, ClientRegistration> REGISTRATION_MAPPER = registration -> ClientRegistration.withRegistrationId(
			registration.getClientName())
			.clientId(registration.getClientId())
			.clientSecret(registration.getClientSecret())
			.clientAuthenticationMethod(new ClientAuthenticationMethod(registration.getClientAuthMethod()))
			.authorizationGrantType(new AuthorizationGrantType(registration.getAuthGrantType()))
			.redirectUriTemplate(registration.getRedirectUrlTemplate())
			.authorizationUri(registration.getAuthorizationUri())
			.tokenUri(registration.getTokenUri())
			.userInfoUri(registration.getUserInfoEndpointUri())
			.userNameAttributeName(registration.getUserInfoEndpointNameAttribute())
			.jwkSetUri(registration.getJwkSetUri())
			.clientName(registration.getClientName())
			.scope(Optional.ofNullable(registration.getScopes()).orElseThrow(() -> new DataStorageException("Inconsistent data. Scopes for clientRegistration not provided."))
					.stream().map(OAuthRegistrationScope::getScope).toArray(String[]::new))
			.build();

	public static final Function<ClientRegistration, OAuthRegistration> REGISTRATION_REVERSE_MAPPER = fullRegistration -> {
		OAuthRegistration registration = new OAuthRegistration();
		registration.setId(fullRegistration.getRegistrationId());
		registration.setClientId(fullRegistration.getClientId());
		registration.setClientSecret(fullRegistration.getClientSecret());
		registration.setClientAuthMethod(fullRegistration.getClientAuthenticationMethod().getValue());
		registration.setAuthGrantType(fullRegistration.getAuthorizationGrantType().getValue());
		registration.setRedirectUrlTemplate(fullRegistration.getRedirectUriTemplate());
		registration.setScopes(fullRegistration.getScopes().stream().map(scope -> {
			OAuthRegistrationScope scopeNew = new OAuthRegistrationScope();
			scopeNew.setRegistration(registration);
			scopeNew.setScope(scope);
			return scopeNew;
		}).collect(Collectors.toSet()));
		ClientRegistration.ProviderDetails details = fullRegistration.getProviderDetails();
		registration.setAuthorizationUri(details.getAuthorizationUri());
		registration.setTokenUri(details.getTokenUri());
		registration.setUserInfoEndpointUri(details.getUserInfoEndpoint().getUri());
		registration.setUserInfoEndpointNameAttribute(details.getUserInfoEndpoint().getUserNameAttributeName());
		registration.setJwkSetUri(details.getJwkSetUri());
		registration.setClientName(fullRegistration.getClientName());
		return registration;
	};

	public MutableClientRegistrationRepository(OAuthRegistrationRepository oAuthRegistrationRepository) {
		this.oAuthRegistrationRepository = oAuthRegistrationRepository;
	}

	@Override
	public ClientRegistration findByRegistrationId(String registrationId) {
		return this.oAuthRegistrationRepository.findById(registrationId).map(REGISTRATION_MAPPER).orElse(null);
	}

	public boolean exists(String id) {
		return this.oAuthRegistrationRepository.existsById(id);

	}

	public ClientRegistration save(ClientRegistration registration) {
		return REGISTRATION_MAPPER.apply(this.oAuthRegistrationRepository.save(REGISTRATION_REVERSE_MAPPER.apply(registration)));
	}

	public void delete(String id) {
		oAuthRegistrationRepository.deleteById(id);
	}

	public Collection<ClientRegistration> findAll() {
		return StreamSupport.stream(this.oAuthRegistrationRepository.findAll().spliterator(), false)
				.map(REGISTRATION_MAPPER)
				.collect(Collectors.toList());
	}
}
