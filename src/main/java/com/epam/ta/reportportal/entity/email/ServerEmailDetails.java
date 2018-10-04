package com.epam.ta.reportportal.entity.email;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Ivan Budaev
 */
@Entity
@Table(name = "server_email_details")
public class ServerEmailDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "enabled")
	private Boolean enabled;

	@Column(name = "host")
	private String host;

	@Column(name = "port")
	private Integer port;

	@Column(name = "protocol")
	private String protocol;

	@Column(name = "auth_enabled")
	private Boolean authEnabled;

	@Column(name = "start_tls_enabled")
	private Boolean startTlsEnabled;

	@Column(name = "ssl_enabled")
	private Boolean sslEnabled;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "from")
	private String from;

	public ServerEmailDetails() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Boolean getAuthEnabled() {
		return authEnabled;
	}

	public void setAuthEnabled(Boolean authEnabled) {
		this.authEnabled = authEnabled;
	}

	public Boolean getStartTlsEnabled() {
		return startTlsEnabled;
	}

	public void setStartTlsEnabled(Boolean startTlsEnabled) {
		this.startTlsEnabled = startTlsEnabled;
	}

	public Boolean getSslEnabled() {
		return sslEnabled;
	}

	public void setSslEnabled(Boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}
