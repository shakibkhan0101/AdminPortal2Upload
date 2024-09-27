package tech.gig.examninja.admin.AdminUploads.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.gig.examninja.admin.AdminUploads.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFileFormatException(InvalidFileFormatException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), "Can't upload file");
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

    @ExceptionHandler(InvalidQuestionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidQuestionException(InvalidQuestionException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), "Upload failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(FileNotAttachedException.class)
    public ResponseEntity<ErrorResponse> handleFileNotAttachedException(FileNotAttachedException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), "File Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), "Error Occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
