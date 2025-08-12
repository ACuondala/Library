package com.example.nada.Controllers;

import com.example.nada.Dtos.BookDto.BookDto;
import com.example.nada.Services.BookService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
@Tag(name="Book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService=bookService;
    }

    public ResponseEntity<ApiResponse<Page<BookDto>>>index(
            /*@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String name*/
            @PageableDefault(page=0,size = 10, sort="id") Pageable pageable
    ){
        Page<BookDto> bookDtos=this.bookService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "Books find successfully",
                        200,
                        bookDtos
                )
        );
    }
}
