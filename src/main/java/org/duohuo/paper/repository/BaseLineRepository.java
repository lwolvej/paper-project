package org.duohuo.paper.repository;

import org.duohuo.paper.model.BaseLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("baseLineRepository")
public interface BaseLineRepository extends JpaRepository<BaseLine, String> {

    List<BaseLine> findAllByCategory_CategoryIdIn(final List<Integer> categoryIdList);

    List<BaseLine> findAllByYear(final String year);

    Boolean existsByYearLessThan(final String year);

    void deleteAllByYearLessThan(final String year);

    @Query(value = "SELECT * FROM base_line_info WHERE category_id=(?1) AND percent=(?2) AND year = (SELECT MAX(year) FROM base_line_info)", nativeQuery = true)
    BaseLine findByCategory_CategoryIdAndPercentAndYear(final Integer categoryId, final String percent);
}
