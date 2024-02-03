package org.dms.wbsserver;

import lombok.RequiredArgsConstructor;
import org.dms.dto.CpuUsageDto;
import org.dms.wbsserver.service.CpuUsageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CpuUsageController {

  private final CpuUsageService cpuUsageService;
  @GetMapping("/cpu-usage")
  public Page<CpuUsageDto> getCpuUsage(Pageable pageable) {
    return cpuUsageService.getCpuUsage(pageable);
  }

}
