package com.example.blogging.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Object> userNotFoundException(UserNotFound ex){
        String msg="User Not Found!!";
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> usernameNotFoundException(UsernameNotFoundException ex){
        String msg="User Not Found";
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<Object> userAlreadyExistsException(UserAlreadyExists ex){
        String msg=ex.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> resp=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String fieldName=((FieldError)error).getField();
            String errorMsg=error.getDefaultMessage();
            resp.put(fieldName,errorMsg);
        });

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<Object> categoryNotFound(CategoryNotFound ex){
        String msg=ex.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CaregoryAlreadyExists.class)
    public ResponseEntity<Object> categoryAlreadyExists(CaregoryAlreadyExists ex){
        String msg=ex.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
    }


    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> postNotFoundException(PostNotFoundException ex){
        String msg=ex.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> fileNotFoundException(){
        FileNotFoundException ex=new FileNotFoundException("File Not Exists");
        String msg=ex.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Object> commentNotFoundException(CommentNotFoundException ex){
        String msg=ex.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> invalidCredentialsException(){
        InvalidCredentialsException ex=new InvalidCredentialsException("Invalid Credentials");
        String msg=ex.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> roleNotFoundException(){
        RoleNotFoundException ex=new RoleNotFoundException();
        String msg="Role Not Found!!!";
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<Object> roleAlreadyExistsException(){
        RoleAlreadyExistsException ex=new RoleAlreadyExistsException();
        String msg="Role Already Exists!!!";
        return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> noSuchElementException(NoSuchElementException ex){
        String msg=ex.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileFormatNotSupportedException.class)
    public ResponseEntity<Object> fileFormatNotSupportedException(FileFormatNotSupportedException ex){
        String msg="File Format Not Supported";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> ExpiredJwtException(ExpiredJwtException ex){
        String msg="Jwt Token Expired";
        return new ResponseEntity<>(msg, HttpStatus.UNAUTHORIZED);
    }
}
