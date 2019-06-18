package com.kt.cmmn.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Result<T> {
    public String message;
    public int status;
    public String error;
    public T data;

    public Result(T  data) {
    	this.status = HttpStatus.OK.value();
    	this.message = HttpStatus.OK.name();
        this.data = data;
    }
    public Result(String message, HttpStatus status, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }
}

