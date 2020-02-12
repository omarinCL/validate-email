package cl.option.validateemail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtValidationResult {
    private String email;
    private boolean format_valid;
}