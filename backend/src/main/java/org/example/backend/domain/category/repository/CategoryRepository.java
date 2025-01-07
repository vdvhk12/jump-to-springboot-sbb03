package org.example.backend.domain.category.repository;

import org.example.backend.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
