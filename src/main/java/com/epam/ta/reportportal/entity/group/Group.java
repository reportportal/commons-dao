package com.epam.ta.reportportal.entity.group;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "groups", schema = "public")
public class Group implements Serializable {
  @Id
  private Long id;

  @Column(name = "slug")
  private String slug;

  @Column(name = "name")
  private String name;

  @Column(name = "created_by")
  private Long createdBy;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private List<GroupUser> users;

  @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private List<GroupProject> projects;

  public Group(Long id, String slug, String name, Long createdBy) {
    this.id = id;
    this.slug = slug;
    this.name = name;
    this.createdBy = createdBy;
  }
}