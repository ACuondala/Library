package com.example.nada.Controllers;

import com.example.nada.Dtos.PersonDto.PersonDto;
import com.example.nada.Dtos.PersonDto.RegisterDto;
import com.example.nada.Models.Person;
import com.example.nada.Services.PersonService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(name="/users")
@Tag(name = "Users")
public class PersonController {
    @Autowired
    private  PersonService personService;


    @GetMapping
    @Operation(description = "Show all users registered")
    public ResponseEntity<ApiResponse<Page<PersonDto>>> index(
            @ParameterObject
            @PageableDefault(size = 10,page = 0, sort="id", direction= Sort.Direction.DESC)Pageable pageable
            ){

        Page<PersonDto> persons=this.personService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Person listed sucessfully",200, persons)
        );
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Show a single user")
    public ResponseEntity<ApiResponse<PersonDto>> show(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("user found successfuly", 200,
                        this.personService.show(id)
                        )
        );
    }

    @PostMapping
    @Operation(description = "create a new user")
    public ResponseEntity<ApiResponse<PersonDto>> store(@RequestBody RegisterDto registerDto){



        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("User created successfully", 201,
                        this.personService.store(registerDto)
                        )
        );

    }

}
