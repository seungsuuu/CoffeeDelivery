package com.sparta.coffeedeliveryproject.exceptions;

import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // validation exception handler : valid 에러 메세지 클라이언트에 전달
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponseDto> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        String errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        MessageResponseDto responseDto = new MessageResponseDto(errorMessages);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    // DB exception handler : DB 저장 에러 메세지 클라이언트에 전달
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MessageResponseDto> ConstraintViolationExceptionHandler(ConstraintViolationException e) {

        StringBuilder errorMessages = new StringBuilder();

        e.getConstraintViolations().forEach(violation -> {
            errorMessages.append(violation.getPropertyPath() + ": " + violation.getMessage() + "\n");
        });

        MessageResponseDto responseDto = new MessageResponseDto(errorMessages.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResponseDto> IllegalArgumentExceptionHandler(IllegalArgumentException e) {

        MessageResponseDto responseDto = new MessageResponseDto("Exception caught: " + e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<MessageResponseDto> PasswordMismatchExceptionHandler(PasswordMismatchException e) {

        MessageResponseDto responseDto = new MessageResponseDto("Exception caught: " + e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(RecentlyUsedPasswordException.class)
    public ResponseEntity<MessageResponseDto> RecentlyUsedPasswordExceptionHandler(RecentlyUsedPasswordException e) {

        MessageResponseDto responseDto = new MessageResponseDto("Exception caught: " + e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageResponseDto> UserNotFoundExceptionHandler(UserNotFoundException e) {

        MessageResponseDto responseDto = new MessageResponseDto("Exception caught: " + e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

}
