package com.epam.ta.reportportal.entity.user;

import com.epam.ta.reportportal.entity.Modifiable;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Ivan Budaev
 */
@Entity
@Table(name = "restore_password_bid")
@EntityListeners(AuditingEntityListener.class)
public class RestorePasswordBid implements Serializable, Modifiable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5010586530900139611L;

	@Id
	private String uuid;

	@LastModifiedDate
	@Column(name = LAST_MODIFIED)
	private Date lastModifiedDate;

	@Column(name = "email")
	private String email;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public Date getLastModified() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		RestorePasswordBid that = (RestorePasswordBid) o;
		return Objects.equals(uuid, that.uuid) && Objects.equals(lastModifiedDate, that.lastModifiedDate) && Objects.equals(
				email,
				that.email
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid, lastModifiedDate, email);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("RestorePasswordBid{");
		sb.append("uuid='").append(uuid).append('\'');
		sb.append(", lastModifiedDate=").append(lastModifiedDate);
		sb.append(", email='").append(email).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
