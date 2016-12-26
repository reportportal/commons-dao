package com.epam.ta.reportportal.database.entity;

import java.util.List;
import java.util.Map;

/**
 * @author Andrei Varabyeu
 */
public class OAuth2LoginDetails {

	private String id;

	private String clientId;

	private String accessTokenUri;

	private List<String> scope;

	private String clientSecret;

	private String authenticationScheme;

	private String grantType;

	private String tokenName = "access_token";

	private Map<String, ?> restrictions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAccessTokenUri() {
		return accessTokenUri;
	}

	public void setAccessTokenUri(String accessTokenUri) {
		this.accessTokenUri = accessTokenUri;
	}

	public List<String> getScope() {
		return scope;
	}

	public void setScope(List<String> scope) {
		this.scope = scope;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getAuthenticationScheme() {
		return authenticationScheme;
	}

	public void setAuthenticationScheme(String authenticationScheme) {
		this.authenticationScheme = authenticationScheme;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public Map<String, ?> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Map<String, ?> restrictions) {
		this.restrictions = restrictions;
	}
}
