package co.bk.task.jackson.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.List;

/**
 * Exception handler that maps exceptions to http error responses.
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    private static final List VALID_HTTP_STATUS_CODES = Arrays.asList(new String[]{"400","404"});

    /**
     * ALWAYS HAVE THIS IN A RESTFUL SPRING MICROSERVICE.
     *
     * Spring controllers annotations can throw exceptions. For example "required=true" attribute
     * on @RequestParam.
     *
     * Convert the exception so that clients are able to detect the HttpStatus code
     * and throw correct Exception sub-type.
     *
     * Exceptions thrown:
     * - HttpMessageNotReadableException -> if cmd object has strict enum types then this could be thrown.
     *
     * @param ex the spring controller exception thrown when params are missing.
     * @return error response.
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class,
            ServletRequestBindingException.class, HttpMessageNotReadableException.class})
    protected ResponseEntity<ErrorResponse> handleContainerException(
            final Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
                .message(String.join(" ", HttpStatus.BAD_REQUEST.toString(), ex.getMessage()))
                .http_status(HttpStatus.BAD_REQUEST.value())
                .application_code(TaskServiceException.ErrorCode.RESERVED_FOR_UNKNOWN_ERRORS.getApplicationCode()).build(),
                HttpStatus.BAD_REQUEST);
    }


    /**
     * ALWAYS HAVE THIS IN A RESTFUL SPRING MICROSERVICE.
     *
     * Handles microservice exception.
     * @param ex the ServiceException.
     * @return error response.
     */
    @ExceptionHandler(TaskServiceException.class)
    protected ResponseEntity<ErrorResponse> handleServiceException(
            final TaskServiceException ex) {

        log.error("Error: ", ex);
        HttpStatus code = HttpStatus.BAD_REQUEST;
        if (ex.getMessage() != null && ex.getMessage().length() >= 3) {

            final String codeFromMessage = ex.getMessage().substring(0,3);
            if (VALID_HTTP_STATUS_CODES.contains(codeFromMessage)) {
                code = HttpStatus.valueOf(Integer.parseInt(codeFromMessage));
            }
        }
        return new ResponseEntity<ErrorResponse>(ErrorResponse.builder()
                .message(ex.getMessage())
                .http_status(code.value())
                .application_code(ex.getApplicationCode()).build(),
                code);
    }

}
