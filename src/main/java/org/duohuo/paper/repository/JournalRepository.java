package org.duohuo.paper.repository;

import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("journalRepository")
public interface JournalRepository extends JpaRepository<Journal, Long> {

    Page<Journal> findAllByCategory_CategoryIdIn(List<Integer> categoryIdList, Pageable pageable);

    Page<Journal> findAllByTime_TimeIdIn(List<Integer> timeIdList, Pageable pageable);

    @Query(value = "SELECT * FROM journal_info WHERE MATCH (full_title)AGAINST(?1)", nativeQuery = true)
    Page<Journal> findAllByKeyWord(String keyWord, Pageable pageable);

    Page<Journal> findAllByCategory_CategoryIdInAndTime_TimeIdIn(List<Integer> categoryIdList, List<Integer> timeIdList, Pageable pageable);

    @Query(value = "SELECT * FROM journal_info WHERE MATCH (full_title)AGAINST(?1) AND category_id IN (?2)", nativeQuery = true)
    Page<Journal> findAllByKeyWordAndCategory_CategoryIdIn(String keyWord, List<Integer> categoryIdList, Pageable pageable);

    @Query(value = "SELECT * FROM journal_info WHERE MATCH (full_title)AGAINST(?1) AND time_id IN(?2)", nativeQuery = true)
    Page<Journal> findAllByKeyWordAndTime_TimeIdIn(String keyWord, List<Integer> timeIdList, Pageable pageable);

    @Query(value = "SELECT * FROM journal_info WHERE MATCH (full_title)AGAINST(?1) AND category_id IN (?2) AND time_id IN(?3)", nativeQuery = true)
    Page<Journal> findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdIn(String keyWord, List<Integer> categoryIdList, List<Integer> timeIdList, Pageable pageable);

    @Query(value = "SELECT * FROM journal_info WHERE MATCH (full_title)AGAINST(?1) AND category_id IN (?2) AND time_id IN (?3)", nativeQuery = true)
    List<Journal> findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdIn(String keyWord, List<Integer> categoryIdList, List<Integer> timeIdList);

    List<Journal> findAllByTime_TimeIdIn(List<Integer> timeIdList);

    Page<Journal> findAllByJournalIdIn(List<Long> journalIdList, Pageable pageable);

    List<Journal> findAllByJournalIdIn(List<Long> journalIdList);

    @Query(value = "SELECT * FROM journal_info WHERE MATCH (full_title)AGAINST(?1) AND time_id IN (?2)", nativeQuery = true)
    List<Journal> findAllByKeyWordAndTime_TimeIdIn(String keyWord, List<Integer> timeIdList);

    List<Journal> findAllByCategory_CategoryIdInAndTime_TimeIdIn(List<Integer> categoryIdList, List<Integer> timeIdList);

    void deleteAllByTime_TimeIdIn(List<Integer> timeIdList);

    Boolean existsByTime(Time time);

    void deleteAllByTime(Time time);

    void deleteAllByTime_TimeId(Integer timeId);
}
