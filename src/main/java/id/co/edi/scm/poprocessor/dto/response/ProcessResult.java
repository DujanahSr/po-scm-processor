package id.co.edi.scm.poprocessor.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProcessResult {

    private final boolean success;
    private final String source;
    private final String message;
    private final LocalDateTime processedAt;
    private Integer recordsImported;
    private String outputFile;

    private ProcessResult(boolean success, String source, String message) {
        this.success = success;
        this.source = source;
        this.message = message;
        this.processedAt = LocalDateTime.now();
    }

    public static ProcessResult success(String source, String message) {
        return new ProcessResult(true, source, message);
    }

    public static ProcessResult failure(String source, String message) {
        return new ProcessResult(false, source, message);
    }

    public ProcessResult withRecordsImported(int count) {
        this.recordsImported = count;
        return this;
    }

    public ProcessResult withOutputFile(String filePath) {
        this.outputFile = filePath;
        return this;
    }
}
