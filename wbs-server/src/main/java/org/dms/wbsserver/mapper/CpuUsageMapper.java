package org.dms.wbsserver.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.dms.dto.CpuUsageDto;
import org.dms.wbsserver.dto.CpuUsageInfoDto;
import org.dms.wbsserver.entity.CpuUsage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = DateTimeFormatter.class)
public interface CpuUsageMapper {

  CpuUsage toEntity(CpuUsageDto cpuUsageDto);

  CpuUsageDto toDto(CpuUsage cpuUsage);

  @Mapping(target = "time", expression = "java(dateTimeToString(cpuUsage.getDateTime()))")
  CpuUsageInfoDto toCpuUsageInfo(CpuUsage cpuUsage);

  default String dateTimeToString(LocalDateTime dateTime) {
    return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

}
