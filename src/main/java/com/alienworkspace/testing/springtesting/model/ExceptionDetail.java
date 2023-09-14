package com.alienworkspace.testing.springtesting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDetail {

    private LocalDateTime timestamp;

    private String path;

    private String message;

    private String errorCode;

}
