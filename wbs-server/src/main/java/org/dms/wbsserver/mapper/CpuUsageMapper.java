package org.dms.wbsserver.mapper;

import org.dms.dto.CpuUsageDto;
import org.dms.wbsserver.entity.CpuUsage;
import org.mapstruct.Mapper;

@Mapper
public interface CpuUsageMapper {

  CpuUsage toEntity(CpuUsageDto cpuUsageDto);

  CpuUsageDto toDto(CpuUsage cpuUsage);

}
