package tech.gig.examninja.admin.AdminUploads.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileNotAttachedException extends RuntimeException {
    public FileNotAttachedException(String message){
        super(message);
    }
}
