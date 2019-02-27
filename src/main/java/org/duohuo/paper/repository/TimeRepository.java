package org.duohuo.paper.repository;

import org.duohuo.paper.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("timeRepository")
public interface TimeRepository extends JpaRepository<Time, Integer> {

    Boolean existsByTimeId(Integer timeId);

    Optional<List<Time>> findAllByYear(Integer year);

    Optional<List<Time>> findAllByMonth(Integer month);

    @Query(value = "SELECT * FROM time_period WHERE time_id=(SELECT MAX(time_id) FROM time_period)", nativeQuery = true)
    Optional<Time> findMax();

    @Query(value = "SELECT * FROM time_period WHERE time_id<?1 ORDER BY time_id DESC LIMIT 1", nativeQuery = true)
    Optional<Time> findSecond(Integer maxTimeId);
}
