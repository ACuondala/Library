package com.example.nada.Wrappers;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"message","status","data"})
public class ApiResponse<T> {

    private String message;

    private Integer status;

    private T data;
}
