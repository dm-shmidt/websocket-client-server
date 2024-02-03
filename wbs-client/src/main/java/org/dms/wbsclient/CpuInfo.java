package org.dms.wbsclient;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CpuInfo {

  private final static OperatingSystemMXBean platformMXBean =
      ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

  public static BigDecimal getCpuUsage() {

    return BigDecimal.valueOf(platformMXBean.getCpuLoad() * 100)
        .setScale(3, RoundingMode.HALF_UP);
  }

}
