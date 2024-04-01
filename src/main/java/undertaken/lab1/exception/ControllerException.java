package undertaken.lab1.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerException {
    private final Logger logger = LoggerFactory.getLogger(ControllerException.class);

    @ExceptionHandler({MissingServletRequestParameterException.class}) //отсутсвие параметра запроса
    public ResponseEntity<String> badRequestException(MissingServletRequestParameterException ex, WebRequest request) {
        logger.error("error 400 {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error 400(bad request)");
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<String> notFoundException(NoHandlerFoundException ex, WebRequest request) {
        logger.error("error 404 {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error 404(not found)");
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<String> notSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        logger.error("error 405 {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("error 405(method not supported)");
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> internalServerErrorException(RuntimeException ex, WebRequest request) {
        logger.error("error 500 {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error 500(internal server error)");
    }
}
