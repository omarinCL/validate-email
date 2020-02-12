package cl.option.validateemail.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.option.validateemail.model.ExtValidationResult;
import cl.option.validateemail.model.ValidationResult;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailValidation {
    @Value("${validation.api.key}")
    private String apiKey;

    @Value("${validation.url}")
    private String url;

    @Value("${validation.api.timeout}")
    private int timeout;

    public ResponseEntity<ValidationResult> validate(String email) {
        var res = new ValidationResult();

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(timeout);
		factory.setConnectTimeout(timeout);
		
        RestTemplate template = new RestTemplate(new BufferingClientHttpRequestFactory(factory));
        String urlWithParams = url.replace("{0}", apiKey).replace("{1}", email);

        ResponseEntity<ExtValidationResult> re = null;
        try {
            re = template.getForEntity(urlWithParams, ExtValidationResult.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        
        res.setValue(email);

        if (re != null && re.getStatusCode() == HttpStatus.OK) {
            res.setValid(re.getBody().isFormat_valid());
            res.setFormatedValue(re.getBody().getEmail());
            res.setValidated(true);
        } 

        return new ResponseEntity<ValidationResult>(res, HttpStatus.OK);
    }
}