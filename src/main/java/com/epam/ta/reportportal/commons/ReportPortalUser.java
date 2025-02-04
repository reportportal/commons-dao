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

package com.epam.ta.reportportal.commons;

import static java.util.Optional.ofNullable;

import com.epam.reportportal.rules.exception.ErrorType;
import com.epam.reportportal.rules.exception.ReportPortalException;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.entity.user.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * ReportPortal user representation.
 *
 * @author <a href="mailto:andrei_varabyeu@epam.com">Andrei Varabyeu</a>
 */
public class ReportPortalUser extends User {

  private final boolean active;

  @Setter
  @Getter
  private Long userId;

  @Setter
  @Getter
  private UserRole userRole;

  @Setter
  @Getter
  private String email;

  @Setter
  @Getter
  private Map<String, ProjectDetails> projectDetails;

  private ReportPortalUser(String username, String password,
      Collection<? extends GrantedAuthority> authorities, Long userId,
      UserRole role, Map<String, ProjectDetails> projectDetails, String email, boolean isActive) {
    super(username, password, authorities);
    this.userId = userId;
    this.userRole = role;
    this.projectDetails = projectDetails;
    this.email = email;
    this.active = isActive;
  }

  public static ReportPortalUserBuilder userBuilder() {
    return new ReportPortalUserBuilder();
  }

  @Override
  public boolean isEnabled() {
    return active;
  }

  @Override
  public boolean isAccountNonLocked() {
    return active;
  }

  @Getter
//  @Entity
//  @SqlResultSetMapping(
//      name = "ProjectDetailsMapping",
//      classes = @jakarta.persistence.ConstructorResult(
//          targetClass = ReportPortalUser.ProjectDetails.class,
//          columns = {
//              @jakarta.persistence.ColumnResult(name = "projectId", type = Long.class),
//              @jakarta.persistence.ColumnResult(name = "projectName", type = String.class),
//              @jakarta.persistence.ColumnResult(name = "projectRole", type = String.class)
//          }
//      )
//  )
  public static class ProjectDetails implements Serializable {

//    @Id
    @JsonProperty(value = "id")
    private Long projectId;

    @JsonProperty(value = "name")
    private String projectName;

    @JsonProperty("role")
    private ProjectRole projectRole;

//    public ProjectDetails() {
//    }

    public ProjectDetails(Long projectId, String projectName, ProjectRole projectRole) {
      this.projectId = projectId;
      this.projectName = projectName;
      this.projectRole = projectRole;
    }

    public ProjectDetails(Long projectId, String projectName, List<ProjectRole> projectRoles) {
      this.projectId = projectId;
      this.projectName = projectName;

      setHighestRole(projectRoles);
    }

    public void setHighestRole(List<ProjectRole> roles) {
      Optional.ofNullable(this.projectRole).ifPresent(roles::add);

      this.projectRole = roles.stream().max(ProjectRole::compareTo).orElse(null);
    }

    public static ProjectDetailsBuilder builder() {
      return new ProjectDetailsBuilder();
    }

    public static class ProjectDetailsBuilder {

      private Long projectId;
      private String projectName;
      private ProjectRole projectRole;

      private ProjectDetailsBuilder() {
      }

      public ProjectDetailsBuilder withProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
      }

      public ProjectDetailsBuilder withProjectName(String projectName) {
        this.projectName = projectName;
        return this;
      }

      public ProjectDetailsBuilder withProjectRole(String projectRole) {
        this.projectRole = ProjectRole.forName(projectRole)
            .orElseThrow(() -> new ReportPortalException(ErrorType.ROLE_NOT_FOUND, projectRole));
        return this;
      }

      public ProjectDetails build() {
        return new ProjectDetails(projectId, projectName, projectRole);
      }
    }
  }

  public static class ReportPortalUserBuilder {

    private boolean active;
    private String username;
    private String password;
    private Long userId;
    private UserRole userRole;
    private String email;
    private Map<String, ProjectDetails> projectDetails;
    private Collection<? extends GrantedAuthority> authorities;

    private ReportPortalUserBuilder() {

    }

    public ReportPortalUserBuilder withActive(boolean active) {
      this.active = active;
      return this;
    }

    public ReportPortalUserBuilder withUserName(String userName) {
      this.username = userName;
      return this;
    }

    public ReportPortalUserBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public ReportPortalUserBuilder withAuthorities(
        Collection<? extends GrantedAuthority> authorities) {
      this.authorities = authorities;
      return this;
    }

    public ReportPortalUserBuilder withUserDetails(UserDetails userDetails) {
      this.username = userDetails.getUsername();
      this.password = userDetails.getPassword();
      this.authorities = userDetails.getAuthorities();
      this.active = userDetails.isEnabled();
      return this;
    }

    public ReportPortalUserBuilder withUserId(Long userId) {
      this.userId = userId;
      return this;
    }

    public ReportPortalUserBuilder withUserRole(UserRole userRole) {
      this.userRole = userRole;
      return this;
    }

    public ReportPortalUserBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public ReportPortalUserBuilder withProjectDetails(Map<String, ProjectDetails> projectDetails) {
      this.projectDetails = projectDetails;
      return this;
    }

    public ReportPortalUser fromUser(com.epam.ta.reportportal.entity.user.User user) {
      this.active = user.getActive();
      this.username = user.getLogin();
      this.email = user.getPassword();
      this.userId = user.getId();
      this.userRole = user.getRole();
      this.password = ofNullable(user.getPassword()).orElse("");
      this.authorities = Collections.singletonList(
          new SimpleGrantedAuthority(user.getRole().getAuthority()));
      this.projectDetails = user.getProjects().stream().collect(Collectors.toMap(
          it -> it.getProject().getName(),
          it -> ProjectDetails.builder()
              .withProjectId(it.getProject().getId())
              .withProjectRole(it.getProjectRole().name())
              .withProjectName(it.getProject().getName())
              .build()
      ));
      return build();
    }

    public ReportPortalUser build() {
      return new ReportPortalUser(username, password, authorities, userId, userRole, projectDetails,
          email, active);
    }
  }

  public static class ProjectDetailsMapper {
    private final Long projectId;
    private final String projectName;
    private final String[] projectRoles;

    public ProjectDetailsMapper(Long projectId, String projectName, String[] projectRoles) {
      this.projectId = projectId;
      this.projectName = projectName;
      this.projectRoles = projectRoles;
    }

    public ProjectDetails toProjectDetails() {
      List<ProjectRole> projectRoles = Arrays.stream(this.projectRoles)
          .map(ProjectRole::valueOf)
          .collect(Collectors.toList());

      return new ProjectDetails(projectId, projectName, projectRoles);
    }
  }
}
