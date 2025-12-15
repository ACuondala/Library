package com.example.nada.Controllers;

import com.example.nada.Dtos.AuthDto.LoginDto;
import com.example.nada.Services.AuthenticationService;
import com.example.nada.Wrappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name="Authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("login")
    @Operation(description = "login")
    public ResponseEntity<ApiResponse<String>> authenticate(@RequestBody LoginDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("Token return Successfuly",
                        200,
                         this.authenticationService.autenticate(dto))
        );
    }
}
