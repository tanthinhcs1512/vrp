package webserviceapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import webserviceapi.model.response.ErrorMessage;

import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), new Date());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
