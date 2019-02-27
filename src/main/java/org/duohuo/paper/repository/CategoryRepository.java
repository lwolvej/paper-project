package org.duohuo.paper.repository;

import org.duohuo.paper.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByCategoryName(String categoryName);

    Boolean existsByCategoryName(String categoryName);
}
