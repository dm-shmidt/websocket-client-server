package org.dms.wbsserver.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.dms.dto.CpuUsageDto;
import org.dms.wbsserver.mapper.CpuUsageMapper;
import org.dms.wbsserver.repository.CpuUsageRepository;
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

  public Mono<List<CpuUsageDto>> getCpuUsage(long minutes) {
    return Mono.just(
        cpuUsageRepository.findByDateTimeAfter(LocalDateTime.now().minusMinutes(minutes))
            .stream().map(cpuUsageMapper::toDto).toList());
  }

}
