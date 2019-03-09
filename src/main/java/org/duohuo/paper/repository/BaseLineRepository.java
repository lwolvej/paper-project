package org.duohuo.paper.repository;

import org.duohuo.paper.model.BaseLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("baseLineRepository")
public interface BaseLineRepository extends JpaRepository<BaseLine, String> {

    List<BaseLine> findAllByCategory_CategoryIdIn(final List<Integer> categoryIdList);

    List<BaseLine> findAllByYear(final String year);

    Boolean existsByYearLessThan(final String year);

    void deleteAllByYearLessThan(final String year);

    Optional<BaseLine> findByCategory_CategoryIdAndPercentAndYear(final Integer categoryId, final String percent, final String year);
}
