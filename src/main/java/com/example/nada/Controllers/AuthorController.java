package com.example.nada.Controllers;

import com.example.nada.Dtos.Author.AuthorDto;
import com.example.nada.Services.AuthorService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService=authorService;
    }

    @Operation(description = "Show all author")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AuthorDto>>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy

    ){
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy).descending());

        Page<AuthorDto> authorDtos=this.authorService.getAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Authors found successfully",
                            200,
                            authorDtos
                        )
        );

    }
}
