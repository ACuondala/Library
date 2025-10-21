package com.example.nada.Controllers;

import com.example.nada.Dtos.AuthorDtos.AuthorRequestDto;
import com.example.nada.Dtos.AuthorDtos.AuthorResponseDto;
import com.example.nada.Services.AuthorService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
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
@RequestMapping(value = "/authors")
@Tag(name="Author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService=authorService;
    }

    @PostMapping
    @Operation(description = "create a new Author")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> store(@RequestBody AuthorRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Author created successfully",200,this.authorService.store(dto))
        );
    }

    @GetMapping
    @Operation(description = "show all authors")
    public ResponseEntity<ApiResponse<Page<AuthorResponseDto>>> index(
            @ParameterObject
            @PageableDefault(size = 10,page = 0, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
            ){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Author listed successfully", 200, this.authorService.getAll(pageable))
        );
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "show a single author")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> show(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Author found successfully",200,this.authorService.show(id))
        );
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "update an author")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> update(@PathVariable UUID id, AuthorRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Author update successfully",200, this.authorService.update(id,dto))
        );
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "delete an author")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id){
        this.authorService.delete(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>("Author delete successfully",204)
        );
    }


}
