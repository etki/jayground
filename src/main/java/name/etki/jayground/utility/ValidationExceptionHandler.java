package name.etki.jayground.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationViolationMap> handleValidationException(MethodArgumentNotValidException exception) {
        ValidationViolationMap map = new ValidationViolationMap();
        List<String> list;
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            if (!map.containsKey(error.getField())) {
                list = new ArrayList<String>();
                map.put(error.getField(), list);
            } else {
                list = map.get(error.getObjectName());
            }
            list.add(error.getDefaultMessage());
        }
        return new ResponseEntity<ValidationViolationMap>(map, HttpStatus.BAD_REQUEST);
    }
}
