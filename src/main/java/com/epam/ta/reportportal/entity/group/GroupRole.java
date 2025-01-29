package com.epam.ta.reportportal.entity.group;

import lombok.Getter;

@Getter
public enum GroupRole implements Comparable<GroupRole> {
  MEMBER(0, "Member"),
  ADMIN(1, "Admin");

  private final int level;
  private final String name;

  GroupRole(int level, String name) {
    this.level = level;
    this.name = name;
  }
}