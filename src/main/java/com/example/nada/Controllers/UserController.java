package com.example.nada.Controllers;

import com.example.nada.Dtos.UserDto.UserRequestDto;
import com.example.nada.Dtos.UserDto.UserResponseDto;
import com.example.nada.Dtos.UserDto.UserUpdateDto;
import com.example.nada.Services.UserService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "/users")
@Tag(name="Users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }


    @GetMapping
    @Operation(description = "get All users")
    public ResponseEntity<ApiResponse<Page<UserResponseDto>>> index(
            @ParameterObject
            @PageableDefault(size = 10,page=0, sort={"id","name"}, direction= Sort.Direction.DESC
            ) Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("users find successfully",200,this.userService.getAll(pageable))
        );
    }

    @PostMapping
    @Operation(description = "create a new user")
    public ResponseEntity<ApiResponse<UserResponseDto>> store(@RequestBody UserRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("user created successfully",201,this.userService.store(dto))
        );
    }


    @GetMapping(value = "/{id}")
    @Operation(description = "show a single User")
    public ResponseEntity<ApiResponse<UserResponseDto>> show(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("User found successfully",200, this.userService.show(id))
        );
    }

    @PutMapping(value="/{id}")
    @Operation(description = "update a user")
    public ResponseEntity<ApiResponse<UserResponseDto>> update(@RequestBody UserUpdateDto dto,@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("User Update sucessfully",200, this.userService.update(dto, id))
        );
    }

    @DeleteMapping(value="/{id}")
    @Operation(description = "delete a user")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id){
        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("User deleted successfully",204)
        );
    }


}
