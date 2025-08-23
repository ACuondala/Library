package com.example.nada.Controllers;

import com.example.nada.Dtos.CategoryDto.CategoryDto;
import com.example.nada.Dtos.PublisherDto.PublisherDto;
import com.example.nada.Dtos.PublisherDto.PublisherRequestDto;
import com.example.nada.Services.PublisherService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping(value="/publisher")
@Tag(name = "Publisher")
public class PublisherController {
    private final PublisherService publisherService;
    public PublisherController(PublisherService publisherService){
        this.publisherService=publisherService;
    }

    @Operation(summary = "show all Publisher")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PublisherDto>>> index(

            @PageableDefault(page=1,size=10,sort="id", direction=Sort.Direction.DESC) Pageable pageable
    ){

        Page<PublisherDto> publisherDto=this.publisherService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("publisher found successfully",HttpStatus.OK.value(), publisherDto));
    }

    @Operation(summary = "create a new Publisher")
    @PostMapping
    public ResponseEntity<ApiResponse<PublisherDto>> store(@Valid @RequestBody PublisherRequestDto publisherRequestDto){
        PublisherDto publisherDto= this.publisherService.store(publisherRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Publisher created successfully", HttpStatus.CREATED.value(), publisherDto)
                );
    }

    @Operation(summary="Show a Single publisher")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<PublisherDto>> show(@PathVariable UUID id){
        PublisherDto dto=this.publisherService.show(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                "Publisher found successfully",
                HttpStatus.OK.value(),
                dto
        ));
    }

    @Operation(summary = "Update a single Publisher")
    @PutMapping(value="/{id}")
    public ResponseEntity<ApiResponse<PublisherDto>> update(@PathVariable UUID id, @Valid @RequestBody PublisherRequestDto dto){
        PublisherDto publisherDto=this.publisherService.update(id,dto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Publisher updated successfuly", 200, publisherDto)
        );
    }


    @Operation(summary = "Delete")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id){
        this.publisherService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(
           "Publisher deleted successfuly",
           HttpStatus.NO_CONTENT.value()

        ));
    }
}
