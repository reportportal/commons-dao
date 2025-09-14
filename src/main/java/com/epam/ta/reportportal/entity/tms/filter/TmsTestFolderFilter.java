package com.epam.ta.reportportal.entity.tms.filter;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TmsTestFolderFilter implements Serializable {

  private Long id;
  private String name;
  private String description;
  private Long parentId;
  private Long projectId;
}
