package com.epam.ta.reportportal.entity.item;

import lombok.Data;

@Data
public class NestedItemAttachment {

  private Long itemId;
  private String fileId;
  private String fileName;
  private String contentType;

}
