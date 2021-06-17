package tech.itpark.fileservice.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends FileServiceException {

    public ResourceNotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
