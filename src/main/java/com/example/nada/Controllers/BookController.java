package com.example.nada.Controllers;

import com.example.nada.Dtos.BookDtos.BookRequestDto;
import com.example.nada.Dtos.BookDtos.BookResponseDto;
import com.example.nada.Models.Book;
import com.example.nada.Services.BookService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
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
@RequestMapping(value = "/books")
@Tag(name="Books")
public class BookController {

    private final BookService bookService;

    public BookController (BookService bookService){
        this.bookService=bookService;
    }

    @GetMapping
    @Operation(description = "get all books")
    public ResponseEntity<ApiResponse<Page<BookResponseDto>>> index(
            @ParameterObject
            @PageableDefault(size = 10,page = 0,sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable
            ){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Books find successfully",200,this.bookService.getAll(pageable))
        );
    }

    @PostMapping
    @Operation(description = "created a new book")
    public ResponseEntity<ApiResponse<BookResponseDto>>store(@RequestBody BookRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Book created successfully",201,this.bookService.store(dto))
        );
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "get a book")
    public ResponseEntity<ApiResponse<BookResponseDto>> show(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Books find successfully",200,this.bookService.show(id))
        );
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "update a book")
    public ResponseEntity<ApiResponse<BookResponseDto>>update(@PathVariable UUID id,@RequestBody BookRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Book updated successfully",200,this.bookService.update(id,dto))
        );
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "delete a book")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id){
            this.bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Books find successfully",200)
        );
    }

}
