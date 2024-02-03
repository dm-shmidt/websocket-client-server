package org.dms.wbsserver.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.dms.wbsserver.entity.CpuUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuUsageRepository extends JpaRepository<CpuUsage, Long> {

  List<CpuUsage> findByDateTimeAfter(LocalDateTime after);
}
