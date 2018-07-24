package com.epam.ta.reportportal.entity.oauth;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "oauth_registration_restriction", schema = "public")
public class OAuthRegistrationRestriction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "oauth_registration_fk", nullable = false)
	private OAuthRegistration registration;

	@Column(name = "type", nullable = false, length = 256)
	private String type;

	@Column(name = "value", nullable = false, length = 256)
	private String value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OAuthRegistration getRegistration() {
		return registration;
	}

	public void setRegistration(OAuthRegistration registration) {
		this.registration = registration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OAuthRegistrationRestriction that = (OAuthRegistrationRestriction) o;
		return Objects.equals(registration.getId(), that.registration.getId()) && Objects.equals(type, that.type) && Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {

		return Objects.hash(registration.getId(), type, value);
	}
}
