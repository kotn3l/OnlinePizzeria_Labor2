package hu.onlinepizzeria.server.controller;

import hu.onlinepizzeria.server.core.exceptions.AlreadyExists;
import hu.onlinepizzeria.server.core.exceptions.InvalidId;
import hu.onlinepizzeria.server.service.jwt.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String methodNotAllowed(HttpRequestMethodNotSupportedException e){
        return "Not allowed method: " +e.getMethod()+", use one of these: "+e.getSupportedHttpMethods();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badRequest(HttpMessageNotReadableException e) {
        Throwable c = e.getCause().getCause();
        return c.getClass().getSimpleName()+ ": "+c.getMessage();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public String unsupported(HttpMediaTypeNotSupportedException e){
        return "Use one of the followings: "+e.getSupportedMediaTypes();

    }

    @ExceptionHandler(InvalidId.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String unknownId(InvalidId e){
        return e.getMessage();
    }

    @ExceptionHandler(AlreadyExists.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String alreadyExists(InvalidId e){
        return e.getMessage();
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String invalidToken(InvalidId e){
        return e.getMessage();
    }
}
