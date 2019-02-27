package org.duohuo.paper.repository;

import org.duohuo.paper.model.BaseLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("baseLineRepository")
public interface BaseLineRepository extends JpaRepository<BaseLine, String> {

    List<BaseLine> findAllByCategory_CategoryIdIn(List<Integer> categoryIdList);

    List<BaseLine> findAllByYear(String year);

    Boolean existsByYearLessThan(String year);

    void deleteAllByYearLessThan(String year);
}
