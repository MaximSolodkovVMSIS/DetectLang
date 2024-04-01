package undertaken.lab1.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ControllerExceptionHandlerTest {

    @Mock
    private WebRequest mockRequest;

    @InjectMocks
    private ControllerExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void badRequestException() {
        MissingServletRequestParameterException mockException = mock(MissingServletRequestParameterException.class);
        ResponseEntity<String> responseEntity = exceptionHandler.badRequestException(mockException, mockRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("error 400(bad request)", responseEntity.getBody());
    }

    @Test
    void notFoundException() {
        NoHandlerFoundException mockException = mock(NoHandlerFoundException.class);
        ResponseEntity<String> responseEntity = exceptionHandler.notFoundException(mockException, mockRequest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("error 404(not found)", responseEntity.getBody());
    }

    @Test
    void notSupportedException() {
        HttpRequestMethodNotSupportedException mockException = mock(HttpRequestMethodNotSupportedException.class);
        ResponseEntity<String> responseEntity = exceptionHandler.notSupportedException(mockException, mockRequest);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, responseEntity.getStatusCode());
        assertEquals("error 405(method not supported)", responseEntity.getBody());
    }

    @Test
    void internalServerErrorException() {
        RuntimeException mockException = mock(RuntimeException.class);
        ResponseEntity<String> responseEntity = exceptionHandler.internalServerErrorException(mockException, mockRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("error 500(internal server error)", responseEntity.getBody());
    }
}