package com.example.nada.Controllers;

import com.example.nada.Dtos.BookDto.BookDto;
import com.example.nada.Dtos.BookDto.BookRequestDto;
import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Services.BooksService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/books")
@Tag(name="Book")
public class BookController {

    private final BooksService booksService;

    public BookController(BooksService booksService){
        this.booksService=booksService;
    }

    @Operation(description = "Show all books")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookDto>>>index(
            @ParameterObject
            @PageableDefault(page=0,size = 10, sort="id") Pageable pageable
    ){
        Page<BookDto> bookDtos=this.booksService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "Books find successfully",
                        200,
                        bookDtos
                )
        );
    }

    @Operation(description = "Get one book")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<BookDto>> show(@PathVariable UUID id){
        BookDto bookDto= this.booksService.show(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Book found successfully",200, bookDto)
        );
    }

    @Operation(description="Create a new book")
    @PostMapping
    public ResponseEntity<ApiResponse<BookDto>> create(@RequestBody BookRequestDto bookRequestDto){
        BookDto bookDto= this.booksService.create(bookRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("book created successfully", 201, bookDto)
        );
    }
}
