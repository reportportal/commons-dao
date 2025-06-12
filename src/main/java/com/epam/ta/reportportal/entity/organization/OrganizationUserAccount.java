package com.epam.ta.reportportal.entity.organization;

import com.epam.ta.reportportal.entity.user.UserRole;
import com.epam.ta.reportportal.entity.user.UserType;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class OrganizationUserAccount {

  private Long id;
  private String fullName;
  private Instant createdAt;
  private Instant updatedAt;
  private UserRole instanceRole;
  private OrganizationRole orgRole;
  private UserType authProvider;
  private String email;
  private Instant lastLoginAt;
  private String externalId;
  private UUID uuid;
  private Integer projectCount;

}
