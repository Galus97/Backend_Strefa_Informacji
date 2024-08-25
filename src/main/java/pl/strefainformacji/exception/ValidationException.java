package pl.strefainformacji.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ValidationException extends Exception{

    private final Map<String, String> validationErrors;
}
