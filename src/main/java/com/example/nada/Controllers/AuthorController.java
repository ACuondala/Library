package com.example.nada.Controllers;

import com.example.nada.Dtos.Author.AuthorDto;
import com.example.nada.Dtos.Author.AuthorRequestDto;
import com.example.nada.Services.AuthorService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value="/authors")
@Tag(name = "Author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService=authorService;
    }

    @Operation(description = "Show all author")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AuthorDto>>> index(
            /*@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy*/

            @PageableDefault(page=0,size=10,sort="id", direction=Sort.Direction.DESC) Pageable pageable

    ){
        //Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy).descending());

        Page<AuthorDto> authorDtos=this.authorService.getAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Authors found successfully",
                            200,
                            authorDtos
                        )
        );

    }

    @Operation(summary = "Create a new Author")
    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDto>> store(@RequestBody AuthorRequestDto authorRequestDto){

        AuthorDto authorDto=this.authorService.store(authorRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "Author created successfully",
                        201,
                        authorDto));
    }

    @Operation(summary = "show a single Author")
    @GetMapping(value="/{id}")
    public ResponseEntity<ApiResponse<AuthorDto>> show(@PathVariable UUID id){

        AuthorDto authorDto=this.authorService.show(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<>(
                    "Author found successfully",
                    200,
                    authorDto
            )
        );
    }

    @Operation(summary = "Update a single Author")
    @PutMapping(value="/{id}")
    public ResponseEntity<ApiResponse<AuthorDto>> update(@PathVariable UUID id, AuthorRequestDto authorRequestDto){
        AuthorDto authorDto=this.authorService.update(id, authorRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "Author update successfully",
                        200,
                        authorDto
                )
        );
    }


    @Operation(summary = "Delete an Author")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<ApiResponse>delete(@PathVariable UUID id){
        this.authorService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "Auther deleted successfully",
                        204
                )
        );
    }

}
