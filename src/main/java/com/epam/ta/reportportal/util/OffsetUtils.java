package com.epam.ta.reportportal.util;

import com.epam.ta.reportportal.model.Offset;
import com.epam.ta.reportportal.model.Offset.OrderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class OffsetUtils {

  private OffsetUtils() {
  }

  public static <T extends Offset> T withOffsetData(T offsetObject, Page page) {
    return (T) offsetObject
        .offset((int) page.getPageable().getOffset())
        .limit(page.getPageable().getPageSize())
        .totalCount((int) page.getTotalElements())
        .sort(getSortFields(page.getPageable()))
        .order(getOrderEnum(page.getPageable()));
  }

  private static String getSortFields(Pageable pageable) {
    return pageable.getSort().stream()
        .map(Order::getProperty)
        .reduce((s1, s2) -> s1 + ", " + s2)
        .orElse("");
  }

  private static OrderEnum getOrderEnum(Pageable pageable) {
    return pageable.getSort().stream()
        .map(Order::getDirection)
        .findFirst()
        .map(direction -> direction.equals(Direction.ASC) ? OrderEnum.ASC : OrderEnum.DESC)
        .orElse(OrderEnum.ASC);
  }


}
