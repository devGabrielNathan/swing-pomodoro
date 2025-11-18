package br.com.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private HashMap<String, String> errors;

    public ErrorResponse() {}

    public ErrorResponse(LocalDateTime timestamp, HashMap<String, String> errors) {
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }
}
