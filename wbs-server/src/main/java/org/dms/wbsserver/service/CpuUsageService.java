package org.dms.wbsserver.service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.dms.dto.CpuUsageDto;
import org.dms.wbsserver.dto.CpuUsageInfoDto;
import org.dms.wbsserver.entity.CpuUsage;
import org.dms.wbsserver.mapper.CpuUsageMapper;
import org.dms.wbsserver.repository.CpuUsageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class CpuUsageService {

  private final CpuUsageRepository cpuUsageRepository;
  private final CpuUsageMapper cpuUsageMapper;

  @Transactional
  public Mono<CpuUsage> saveCpuUsage(CpuUsageDto dto) {
    return cpuUsageRepository.save(cpuUsageMapper.toEntity(dto));
  }

  public Flux<CpuUsageInfoDto> getCpuUsage(long minutes) {
    return cpuUsageRepository.findByDateTimeAfter(LocalDateTime.now().minusMinutes(minutes))
        .map(cpuUsageMapper::toCpuUsageInfo);
  }

  @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.MINUTES)
  public void cleanUpDatabase() {
    cpuUsageRepository.count()
        .publishOn(Schedulers.boundedElastic())
        .map(count -> {
          if (count > 100) {
            final var idsToRetain = cpuUsageRepository.findFirst100ByOrderByIdDesc()
                .map(CpuUsage::getId).collectList().block();
            return cpuUsageRepository.deleteAllByIdNotIn(idsToRetain).subscribe();
          }
          return Mono.empty().subscribe();
        }).subscribe();
  }
}
