package org.dms.wbsserver.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.dms.wbsserver.entity.CpuUsage;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CpuUsageRepository extends ReactiveCrudRepository<CpuUsage, Long> {

  Flux<CpuUsage> findByDateTimeAfter(LocalDateTime after);

  Flux<CpuUsage> findFirst100ByOrderByIdDesc();

  Mono<Void> deleteAllByIdNotIn(List<Long> idsToRetain);
}
