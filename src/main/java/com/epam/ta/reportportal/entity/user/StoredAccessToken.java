/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
@Entity
@Table(name = "oauth_access_token", schema = "public")
public class StoredAccessToken implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "token_id")
	private String tokenId;

	@Column(name = "token")
	private byte[] token;

	@Column(name = "authentication_id")
	private String authenticationId;

	@Column(name = "username")
	private String userName;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "client_id")
	private String clientId;

	@Column(name = "authentication")
	private byte[] authentication;

	@Column(name = "refresh_token")
	private String refreshToken;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public byte[] getToken() {
		return token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public byte[] getAuthentication() {
		return authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
