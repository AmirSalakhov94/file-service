package tech.itpark.fileservice.exception;

import org.springframework.http.HttpStatus;

public class ExtractPrincipalException extends FileServiceException {

    public ExtractPrincipalException() {
        super(HttpStatus.FORBIDDEN);
    }

    public ExtractPrincipalException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
