package org.duohuo.paper.repository;

import org.duohuo.paper.model.Paper;
import org.duohuo.paper.model.PaperType;
import org.duohuo.paper.model.Time;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("paperRepository")
public interface PaperRepository extends JpaRepository<Paper, Long> {

    Page<Paper> findAllByPaperTypeIn(List<PaperType> paperTypeList, Pageable pageable);

    //c
    Page<Paper> findAllByCategory_CategoryIdInAndPaperTypeIn(List<Integer> categoryIdList, List<PaperType> paperTypeList, Pageable pageable);

    //t
    Page<Paper> findAllByTime_TimeIdInAndPaperTypeIn(List<Integer> timeIdList, List<PaperType> paperTypeList, Pageable pageable);

    //a
    Page<Paper> findAllByAccessionNumberAndPaperTypeIn(String accessionNumber, List<PaperType> paperTypeList, Pageable pageable);

    //d
    Page<Paper> findAllByDoiAndPaperTypeIn(String doi, List<PaperType> paperTypeList, Pageable pageable);

    //k
    @Query(value = "SELECT * FROM paper_info WHERE MATCH (article_name)AGAINST(?1) AND paper_type IN (?2)", nativeQuery = true)
    Page<Paper> findAllByKeyWordAndPaperTypeIn(String keyWord, List<Integer> paperTypeList, Pageable pageable);

    Page<Paper> findAllByCategory_CategoryIdInAndTime_TimeIdInAndPaperTypeIn(List<Integer> categoryIdList, List<Integer> timeIdList, List<PaperType> paperTypeList, Pageable pageable);

    //cd
    Page<Paper> findAllByCategory_CategoryIdInAndAccessionNumberAndPaperTypeIn(List<Integer> categoryIdList, String accessionNumber, List<PaperType> paperTypeList, Pageable pageable);

    //ca
    Page<Paper> findAllByCategory_CategoryIdInAndDoiAndPaperTypeIn(List<Integer> categoryIdList, String doi, List<PaperType> paperTypeList, Pageable pageable);

    //ck
    @Query(value = "SELECT * FROM paper_info WHERE MATCH (article_name)AGAINST(?1) AND category_id IN (?2) AND paper_type IN (?3)", nativeQuery = true)
    Page<Paper> findAllByKeyWordAndCategory_CategoryIdInAndPaperTypeIn(String keyWord, List<Integer> categoryIdList, List<Integer> paperTypeList, Pageable pageable);

    //ta
    Page<Paper> findAllByTime_TimeIdInAndAccessionNumberAndPaperTypeIn(List<Integer> timeIdList, String accessionNumber, List<PaperType> paperTypeList, Pageable pageable);

    //td
    Page<Paper> findAllByTime_TimeIdInAndDoiAndPaperTypeIn(List<Integer> timeIdList, String doi, List<PaperType> paperTypeList, Pageable pageable);

    //tk
    @Query(value = "SELECT * FROM paper_info WHERE MATCH (article_name)AGAINST(?1) AND time_id IN (?2) AND paper_type IN (?3)", nativeQuery = true)
    Page<Paper> findAllByKeyWordAndTime_TimeIdInAndPaperTypeIn(String keyWord, List<Integer> timeIdList, List<Integer> paperTypeList, Pageable pageable);

    //cta
    Page<Paper> findAllByCategory_CategoryIdInAndTime_TimeIdInAndAccessionNumberAndPaperTypeIn(List<Integer> categoryIdList, List<Integer> timeIdList, String accessionNumber, List<PaperType> paperTypeList, Pageable pageable);

    //ctd
    Page<Paper> findAllByCategory_CategoryIdInAndTime_TimeIdInAndDoiAndPaperTypeIn(List<Integer> categoryIdList, List<Integer> timeIdList, String doi, List<PaperType> paperTypeList, Pageable pageable);

    //ctk
    @Query(value = "SELECT * FROM paper_info WHERE MATCH (article_name)AGAINST(?1) AND category_id IN (?2) AND time_id IN (?3) AND paper_type IN (?4)", nativeQuery = true)
    Page<Paper> findAllByKeyWordAndCategory_CategoryIdInAndTime_TimeIdInAndPaperTypeIn(String keyWord, List<Integer> categoryIdList, List<Integer> timeIdList, List<Integer> paperTypeList, Pageable pageable);

    Boolean existsByAccessionNumber(String accessionNumber);

    Optional<Paper> findByAccessionNumberAndTime(String accessionNumber, Time time);

    void deleteAllByTime_TimeIdInAndPaperTypeIn(List<Integer> timeIdList, List<PaperType> paperTypeList);

    @Query(value = "SELECT * FROM paper_info WHERE MATCH (article_name)AGAINST(?1)", nativeQuery = true)
    List<Paper> findAllByKeyWord(String keyWord);

    List<Paper> findAllByPaperIdIn(List<Long> paperIdList);

    @Query(value = "SELECT * FROM paper_info WHERE accession_number=(?1) AND paper_type IN (?2) AND time_id=(SELECT MAX(time_id) FROM paper_info)", nativeQuery = true)
    Optional<Paper> findMaxTimeDataByAccessionNumberPaperTypeIn(String accessionNumber, List<Integer> paperTypeList);

    void deleteAllByTimeAndPaperTypeIn(final Time time, final List<PaperType> paperTypeList);
}
