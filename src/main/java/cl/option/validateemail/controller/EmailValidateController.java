package cl.option.validateemail.controller;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.option.validateemail.model.ValidationResult;
import cl.option.validateemail.service.EmailValidation;

@RestController
@RequestMapping("/api/v1/email")
@Validated
public class EmailValidateController {
    @Autowired
    private EmailValidation service;

    @GetMapping("/validate")
    public ResponseEntity<ValidationResult> getValidation(
        @RequestParam(value = "email") @NotEmpty String email
    ) {
        return service.validate(email);
    }
}