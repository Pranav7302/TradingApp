package com.example.Contact.ExceptionHandler;

import com.example.Contact.Constants.Constants;
import com.example.Contact.Dto.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@Log4j2
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorName(ex.getClass().getSimpleName());
        errorResponse.setPath("http://localhost:8082/"+ex.getRequestPath());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorCode(String.valueOf(HttpStatus.NOT_FOUND));
        log.info(errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<ErrorResponse > handleDuplicateException(DuplicateEntryException e)
  {
      ErrorResponse errorResponse = new ErrorResponse();
      errorResponse.setErrorName(e.getClass().getSimpleName());
      errorResponse.setPath(e.path);
      errorResponse.setMessage(e.getMessage());
      errorResponse.setErrorCode(String.valueOf(HttpStatus.NOT_FOUND));
      log.info(errorResponse);
      return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
  }
  @ExceptionHandler(ArgumentConstraintViolation.class)
    public ResponseEntity<ErrorResponse> handleIllegalException(ArgumentConstraintViolation argumentConstraintViolation)
  {
      ErrorResponse errorResponse = new ErrorResponse();
      errorResponse.setErrorName(argumentConstraintViolation.getClass().getSimpleName());
      errorResponse.setPath(Constants.Dashboard+Constants.Add_Symbols);
      errorResponse.setMessage(argumentConstraintViolation.getMessage());
      errorResponse.setErrorCode(String.valueOf((HttpStatus.NOT_FOUND)));
      log.info(errorResponse);
      return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
  }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException customException)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorName(RuntimeException.class.getSimpleName());
        errorResponse.setPath(customException.path);
        errorResponse.setMessage(customException.getMessage());
        errorResponse.setErrorCode(String.valueOf((HttpStatus.NOT_FOUND)));
        log.info(errorResponse);
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
