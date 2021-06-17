package tech.itpark.fileservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FileServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    public FileServiceException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public FileServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public FileServiceException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}
