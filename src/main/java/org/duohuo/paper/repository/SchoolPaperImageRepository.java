package org.duohuo.paper.repository;

import org.duohuo.paper.model.SchoolPaperImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("schoolPaperImageRepository")
public interface SchoolPaperImageRepository extends JpaRepository<SchoolPaperImage, Integer> {
}
