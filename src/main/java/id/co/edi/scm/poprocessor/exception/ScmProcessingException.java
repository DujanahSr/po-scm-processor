package id.co.edi.scm.poprocessor.exception;

public class ScmProcessingException extends RuntimeException {

    private final String source;

    public ScmProcessingException(String source, String message) {
        super(message);
        this.source = source;
    }

    public ScmProcessingException(String source, String message, Throwable cause) {
        super(message, cause);
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
