package it.unicam.cs.ids.digitalterritory.error;

import it.unicam.cs.ids.digitalterritory.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthenticationCredentialsNotFoundController {
    @ExceptionHandler(value = AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Response<String>> exception(AuthenticationCredentialsNotFoundException exception) {
        return new ResponseEntity<>(new Response<>("", false, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
