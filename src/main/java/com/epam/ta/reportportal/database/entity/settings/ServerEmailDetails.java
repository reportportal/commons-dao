/*
 * Copyright 2016 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.epam.ta.reportportal.database.entity.settings;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

public class ServerEmailDetails implements Serializable {

    private Boolean enabled;

    private String host;

    private Integer port;

    private String protocol;

    private Boolean authEnabled;

    private Boolean starTlsEnabled;

    private Boolean sslEnabled;

    private String username;

    private String password;

    private String from;

    public ServerEmailDetails() {
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getStarTlsEnabled() {
        return starTlsEnabled;
    }

    public void setStarTlsEnabled(Boolean starTlsEnabled) {
        this.starTlsEnabled = starTlsEnabled;
    }

    public Boolean getSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(Boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol.toLowerCase();
    }

    public String getProtocol() {
        return protocol;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getPassword() {
        return password;
    }

    public void setAuthEnabled(Boolean authEnabled) {
        this.authEnabled = authEnabled;
    }

    public boolean isStarTlsEnabled() {
        return starTlsEnabled;
    }

    public void setStarTlsEnabled(boolean starTlsEnabled) {
        this.starTlsEnabled = starTlsEnabled;
    }

    public Boolean getAuthEnabled() {
        return authEnabled;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("enabled", enabled).add("host", host).add("port", port).add("protocol", protocol)
                .add("authEnabled", authEnabled).add("starTlsEnabled", starTlsEnabled).add("sslEnabled", sslEnabled)
                .add("username", username).add("password", password).add("from", from).toString();
    }
}
