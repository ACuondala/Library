package com.example.nada.Controllers;

import com.example.nada.Dtos.Books.BooksDto;
import com.example.nada.Dtos.Books.BookRequestDto;
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
    public ResponseEntity<ApiResponse<Page<BooksDto>>>index(
            @ParameterObject
            @PageableDefault(page=0,size = 10, sort="id") Pageable pageable
    ){
        Page<BooksDto> booksDtos=this.booksService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "Books find successfully",
                        200,
                        booksDtos
                )
        );
    }

    @Operation(description = "Get one book")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<BooksDto>> show(@PathVariable UUID id){
        BooksDto booksDto= this.booksService.show(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Book found successfully",200, booksDto)
        );
    }

    @Operation(description="Create a new book")
    @PostMapping
    public ResponseEntity<ApiResponse<BooksDto>> create(@RequestBody BookRequestDto bookRequestDto){
        BooksDto booksDto= this.booksService.create(bookRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("book created successfully", 201, booksDto)
        );
    }
}
