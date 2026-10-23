package id.co.edi.scm.poprocessor.exception;

import id.co.edi.scm.poprocessor.dto.response.ProcessResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class ScmExceptionHandler {

    @ExceptionHandler(ScmProcessingException.class)
    public ResponseEntity<ProcessResult> onScmException(ScmProcessingException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ProcessResult.failure(ex.getSource(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProcessResult> onGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ProcessResult.failure("SISTEM", "Terjadi kesalahan sistem yang tidak terduga: " + ex.getMessage()));
    }
}
