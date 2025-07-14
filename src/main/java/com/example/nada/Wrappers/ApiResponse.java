package com.example.nada.Wrappers;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@JsonPropertyOrder({ "message", "status", "data" })
public class ApiResponse<T>{

    private String message;
    private Integer Status;
    private T data;

    public ApiResponse(String message, Integer Status){
        this.message=message;
        this.Status=Status;
    }


}
