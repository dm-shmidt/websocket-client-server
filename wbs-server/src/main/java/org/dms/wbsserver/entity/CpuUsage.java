package org.dms.wbsserver.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CpuUsage {

  @Id
  private Long id;

  private LocalDateTime dateTime;

  private BigDecimal value;

}
