package com.example.nada.Controllers;

import com.example.nada.Dtos.CategoryDtos.CategoryRequestDto;
import com.example.nada.Dtos.CategoryDtos.CategoryResponseDto;
import com.example.nada.Services.CategoryService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/categories")
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService service){
        this.categoryService=service;
    }

    @GetMapping
    @Operation(description = "Show all categories")
    public ResponseEntity<ApiResponse<Page<CategoryResponseDto>>> index(
            @ParameterObject
            @PageableDefault(size = 10,page = 0, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
            ){

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Categories listed successfully",200, this.categoryService.getAll(pageable))
        );

    }

    @PostMapping
    @Operation(description = "Create a new category")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> store(@Valid @RequestBody CategoryRequestDto categoryRequestDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Category created Successfully",201,this.categoryService.store(categoryRequestDto))
        );
    }


    @GetMapping(value = "/{id}")
    @Operation(description = "Show a signle caletegory")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> show(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Category found successfully",200, this.categoryService.show(id))
        );
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Update a category")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> update(@RequestBody CategoryRequestDto dto
            , @PathVariable UUID id){

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Category updated successfully",200,this.categoryService.update(dto,id))
        );

    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "Delete a category")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id){
        this.categoryService.delete(id);
       return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ApiResponse<>("Category Deleted successfully",204)
        );
    }

}
