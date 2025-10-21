package com.example.nada.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError{
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
