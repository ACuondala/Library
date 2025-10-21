package com.example.nada.Controllers;

import com.example.nada.Dtos.PublisherDto.PublisherRequestDto;
import com.example.nada.Dtos.PublisherDto.PublisherResponseDto;
import com.example.nada.Services.PublisherService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/publishers")
@Tag(name="Publisher")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService){
        this.publisherService=publisherService;
    }

    @GetMapping
    @Operation(description = "get All publishers")
    public ResponseEntity<ApiResponse<Page<PublisherResponseDto>>> index(
            @ParameterObject
            @PageableDefault(size = 10,page = 0, sort="id", direction = Sort.Direction.DESC)Pageable pageable
            ){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Publisher Listed successfully",200,this.publisherService.getAll(pageable))
        );
    }

    @PostMapping
    @Operation(description = "Create a publisher")
    public ResponseEntity<ApiResponse<PublisherResponseDto>> store(@RequestBody PublisherRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("publisher created successfully",201,this.publisherService.store(dto))
        );
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Show a single publisher")
    public ResponseEntity<ApiResponse<PublisherResponseDto>>show(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Publisher found Successfully",200,this.publisherService.show(id))
        );
    }

    @PutMapping(value="/{id}")
    @Operation(description = "Update a publisher")
    public ResponseEntity<ApiResponse<PublisherResponseDto>>update(@PathVariable UUID id, @RequestBody PublisherRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Publisher updated Successfully",200,this.publisherService.update(id,dto))
        );
    }

    @DeleteMapping(value="/{id}")
    @Operation(description = "Update a publisher")
    public ResponseEntity<ApiResponse<String>>delete(@PathVariable UUID id){
        this.publisherService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Publisher updated Successfully",204)
        );
    }
}
