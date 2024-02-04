package org.dms.wbsserver;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.dms.wbsserver.dto.CpuUsageInfoDto;
import org.dms.wbsserver.service.CpuUsageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class CpuUsageController {

  private final CpuUsageService cpuUsageService;

  @GetMapping("/cpu-usage/{minutes}")
  public Mono<List<CpuUsageInfoDto>> getCpuUsage(@PathVariable("minutes") long minutes) {
    return cpuUsageService.getCpuUsage(minutes);
  }

}
