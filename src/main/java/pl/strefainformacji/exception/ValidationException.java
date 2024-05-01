package pl.strefainformacji.exception;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ValidationException extends Exception{

    private final List<String> validationErrors;

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
