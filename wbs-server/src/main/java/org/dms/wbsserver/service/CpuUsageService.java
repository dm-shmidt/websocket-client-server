package org.dms.wbsserver.service;

import java.time.LocalDateTime;
import java.util.List;
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
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CpuUsageService {

  private final CpuUsageRepository cpuUsageRepository;
  private final CpuUsageMapper cpuUsageMapper;

  @Transactional
  public void saveCpuUsage(CpuUsageDto dto) {
    cpuUsageRepository.save(cpuUsageMapper.toEntity(dto));
  }

  public Mono<List<CpuUsageInfoDto>> getCpuUsage(long minutes) {
    return Mono.just(
        cpuUsageRepository.findByDateTimeAfter(LocalDateTime.now().minusMinutes(minutes))
            .stream().map(cpuUsageMapper::toCpuUsageInfo).toList());
  }

  @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.MINUTES)
  @Transactional
  void cleanUpDatabase() {
    if (cpuUsageRepository.count() > 100) {
      final var idsToRetain = cpuUsageRepository.findFirst100ByOrderByIdDesc()
          .stream().map(CpuUsage::getId).toList();
      cpuUsageRepository.deleteAllByIdNotIn(idsToRetain);
    }
  }
}
