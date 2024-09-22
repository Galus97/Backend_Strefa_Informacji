package pl.strefainformacji.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ValidationException extends Exception {

    private final Map<String, String> validationErrors;
}