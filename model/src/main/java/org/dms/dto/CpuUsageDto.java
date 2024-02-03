package org.dms.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record CpuUsageDto(LocalDateTime dateTime, BigDecimal value) {

  @Override
  public String toString() {
    return String.format("InfoDto[dateTime: %s, value: %s]",
        dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), value
        );
  }
}
