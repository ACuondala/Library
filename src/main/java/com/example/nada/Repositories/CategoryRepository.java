package com.example.nada.Repositories;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>{

    Optional <Category> findCategoryByName(String name);
}
