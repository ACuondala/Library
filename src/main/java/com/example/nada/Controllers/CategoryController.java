package com.example.nada.Controllers;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.CategoryDto.CategoryRequestDto;
import com.example.nada.Models.Category;
import com.example.nada.Services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value="/category")
@Tag(name="Category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @Operation(summary= "Show all categories")
    @GetMapping
    public ResponseEntity<List<CategoryDto>> index(){

            List<CategoryDto> categories=this.categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @Operation(summary = "Create a new Category")
    @PostMapping
    public ResponseEntity<CategoryDto> store(@RequestBody CategoryRequestDto category){
        CategoryDto categoryDto=this.categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }

    @Operation(summary = "Show a single category")
    @GetMapping(value="/{id}")
    public ResponseEntity<CategoryDto> show(@PathVariable UUID id){
        CategoryDto categoryDto=this.categoryService.show(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }
}
