package com.epam.ta.reportportal.entity.user;

import com.epam.ta.reportportal.entity.Modifiable;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ivan Budaev
 */
@Entity
@Table(name = "restore_password_bid")
public class RestorePasswordBid implements Serializable, Modifiable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5010586530900139611L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@LastModifiedDate
	@Column(name = LAST_MODIFIED)
	private Date lastModifiedDate;

	@Column(name = "email")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

		if (id != null ? !id.equals(that.id) : that.id != null) {
			return false;
		}
		if (lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null) {
			return false;
		}
		return !(email != null ? !email.equals(that.email) : that.email != null);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("RestorePasswordBid{");
		sb.append("id='").append(id).append('\'');
		sb.append(", lastModifiedDate=").append(lastModifiedDate);
		sb.append(", email='").append(email).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
