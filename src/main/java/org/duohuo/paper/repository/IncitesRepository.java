package org.duohuo.paper.repository;

import org.duohuo.paper.model.Incites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("incitesRepository")
public interface IncitesRepository extends JpaRepository<Incites, Integer> {

    Page<Incites> findAllByAccessionNumber(String accessionNumber, Pageable pageable);

    Page<Incites> findAllByDoi(String doi, Pageable pageable);

    @Query(value = "SELECT * FROM incites_info WHERE MATCH (article_name)AGAINST(?1)", nativeQuery = true)
    Page<Incites> findAllByKeyWord(String keyWord, Pageable pageable);

    Page<Incites> findAllByCategory_CategoryIdIn(List<Integer> categoryIdList, Pageable pageable);

    Page<Incites> findAllByAccessionNumberAndCategory_CategoryIdIn(String accessionNumber, List<Integer> categoryIdList, Pageable pageable);

    Page<Incites> findAllByDoiAndCategory_CategoryIdIn(String doi, List<Integer> categoryIdList, Pageable pageable);

    @Query(value = "SELECT * FROM incites_info WHERE MATCH(article_name)AGAINST(?1) AND category_id IN (?2)", nativeQuery = true)
    Page<Incites> findAllByKeyWordAndCategory_CategoryIdIn(String keyWord, List<Integer> categoryIdList, Pageable pageable);

    Optional<Incites> findByAccessionNumber(String accessionNumber);

    List<Incites> findAllByIncitesIdIn(List<Integer> incitesIdList);
}
