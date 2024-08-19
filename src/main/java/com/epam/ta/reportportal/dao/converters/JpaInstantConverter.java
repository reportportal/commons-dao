package com.epam.ta.reportportal.dao.converters;

import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.lang.Nullable;

@Converter(autoApply = true)
public class JpaInstantConverter implements AttributeConverter<Instant, Timestamp> {

  @Nullable
  @Override
  public Timestamp convertToDatabaseColumn(Instant instant) {
    return instant == null ? null : Timestamp.from(instant);
  }

  @Nullable
  @Override
  public Instant convertToEntityAttribute(Timestamp timestamp) {
    return timestamp == null ? null : timestamp.toInstant();
  }
}
