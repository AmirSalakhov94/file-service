package tech.itpark.fileservice.exception;

import org.springframework.http.HttpStatus;

public class NotFoundFileException extends FileServiceException {

    public NotFoundFileException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
