package org.dms.wbsserver.repository;

import org.dms.wbsserver.entity.CpuUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CpuUsageRepository extends JpaRepository<CpuUsage, Long> {

}
