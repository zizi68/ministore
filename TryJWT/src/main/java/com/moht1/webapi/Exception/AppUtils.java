package com.moht1.webapi.Exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.moht1.webapi.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppUtils {
	public static String getExceptionSql(ConstraintViolationException e){
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<?> o : violations) {
                builder.append(" Column: " + o.getPropertyPath() + " " + o.getMessage())
                        .append(System.getProperty("line.separator"));
            }
            errorMessage = builder.toString();
            System.out.println(Constants.VALIDATION_EMAIL_E002.getMessage());
        } else {
            errorMessage = "ConstraintViolationException occured.";

        }
        return errorMessage;
}

	public static ResponseEntity<ResponseObject> returnJS(HttpStatus httpStatus, String message, Object object) {

        return ResponseEntity.status(httpStatus)
                .body(new ResponseObject(message, object));
    }

}
