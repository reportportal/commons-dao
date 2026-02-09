package com.epam.ta.reportportal.entity.item;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@AllArgsConstructor
public class NestedItemAttachment {

  private Long itemId;
  private String name;

  @Column(name = "path", columnDefinition = "ltree")
  @Type(com.epam.ta.reportportal.entity.LTreeType.class)
  private String path;

  private String fileId;
  private String fileName;
  private String contentType;

}
