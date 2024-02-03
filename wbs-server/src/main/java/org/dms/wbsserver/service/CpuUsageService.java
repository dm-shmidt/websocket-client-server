package org.dms.wbsserver.service;

import lombok.RequiredArgsConstructor;
import org.dms.dto.CpuUsageDto;
import org.dms.wbsserver.mapper.CpuUsageMapper;
import org.dms.wbsserver.repository.CpuUsageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CpuUsageService {

  private final CpuUsageRepository cpuUsageRepository;
  private final CpuUsageMapper cpuUsageMapper;

  @Transactional
  public void saveCpuUsage(CpuUsageDto dto) {
    cpuUsageRepository.save(cpuUsageMapper.toEntity(dto));
  }

  public Page<CpuUsageDto> getCpuUsage(Pageable pageable) {
    return cpuUsageRepository.findAll(pageable)
        .map(cpuUsageMapper::toDto);
  }

}
