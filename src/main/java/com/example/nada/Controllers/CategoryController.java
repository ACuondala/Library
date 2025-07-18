package com.example.nada.Controllers;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.CategoryDto.CategoryRequestDto;
import com.example.nada.Models.Category;
import com.example.nada.Services.CategoryService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ){
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy).descending());
            Page<CategoryDto> categories=this.categoryService.getAllCategories(pageable);


        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Category found successfuly",HttpStatus.OK.value(), categories)
        );
    }

    @Operation(summary = "Create a new Category")
    @PostMapping
    public ResponseEntity<Map<String,Object>> store(@Valid @RequestBody CategoryRequestDto category){
        CategoryDto categoryDto=this.categoryService.saveCategory(category);
        Map<String,Object> response= new HashMap<>();
        response.put("message","Category created successfully");
        response.put("data",categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Show a single category")
    @GetMapping(value="/{id}")
    public ResponseEntity<Map<String,Object>> show(@PathVariable UUID id){
        CategoryDto categoryDto=this.categoryService.show(id);
        Map<String,Object> response= new HashMap<>();
        response.put("message","Category created successfully");
        response.put("data",categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Operation(summary="Update a category")
    @PutMapping(value="/{id}")
    public ResponseEntity<Map<String,Object>>update(@RequestBody CategoryRequestDto categoryDto, @PathVariable UUID id){

        CategoryDto dto=this.categoryService.update(categoryDto,id);

        Map<String,Object> response= new HashMap<>();
        response.put("message","Category created successfully");
        response.put("data",dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete a category")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Map<String, String >> delete(@PathVariable UUID id){
        this.categoryService.delete(id);
        Map<String,String> response= new HashMap<>();
        response.put("message","Category ");
        return ResponseEntity.ok(response);
    }
}
