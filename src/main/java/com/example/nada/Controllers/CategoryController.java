package com.example.nada.Controllers;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.CategoryDto.CategoryFilter;
import com.example.nada.Dtos.CategoryDto.CategoryRequestDto;
import com.example.nada.Models.Category;
import com.example.nada.Services.CategoryService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<ApiResponse<Page<CategoryDto>>> index(
            @ParameterObject
            @PageableDefault(page=0,size = 10,sort="id", direction=Sort.Direction.DESC) Pageable pageable
    ){

            Page<CategoryDto> categories=this.categoryService.getAllCategories(pageable);


        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Category found successfuly",HttpStatus.OK.value(), categories)
        );
    }

    @Operation(summary = "Create a new Category")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> store(@Valid @RequestBody CategoryRequestDto category){
        CategoryDto categoryDto=this.categoryService.saveCategory(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Category created successfuly",201,categoryDto)
        );
    }

    @Operation(summary = "Show a single category")
    @GetMapping(value="/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> show(@PathVariable UUID id){
        CategoryDto categoryDto=this.categoryService.show(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Category found successfuly",200,categoryDto)
        );
    }


    @Operation(summary="Update a category")
    @PutMapping(value="/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>>update(@RequestBody CategoryRequestDto categoryDto, @PathVariable UUID id){

        CategoryDto dto=this.categoryService.update(categoryDto,id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Category updated successfuly",200,dto)
        );
    }

    @Operation(summary = "Delete a category")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id){
        this.categoryService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ApiResponse<>("Category deleted successfuly",204)
        );
    }
}
