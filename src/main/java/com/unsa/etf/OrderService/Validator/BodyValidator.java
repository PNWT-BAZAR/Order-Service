package com.unsa.etf.OrderService.Validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class BodyValidator {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    public boolean isValid(Object object){
        return validator.validate(object).isEmpty();
    }

    public String determineConstraintViolation (Object object){

        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        ConstraintViolation<Object> violation;

        if(!violations.isEmpty()){
            violation = violations.iterator().next();
            if(violation.getMessageTemplate().equals("{javax.validation.constraints.NotBlank.message}")){
                return "Parameter " + violation.getPropertyPath() + " is missing";
            }else if (violation.getMessageTemplate().equals("{javax.validation.constraints.NotNull.message}")){
                return "Parameter " + violation.getPropertyPath() + " is null";
            }else if (violation.getMessageTemplate().equals("{javax.validation.constraints.Size.message}")){
                return "The size of parameter " + violation.getPropertyPath() + " is incorrect";
            }else if (violation.getMessageTemplate().equals("{javax.validation.constraints.Min.message}")){
                return "The parameter " + violation.getPropertyPath() + " must me greater than 0";
            } else if (violation.getMessageTemplate().equals("{javax.validation.constraints.Email.message}")){
                return "The parameter " + violation.getPropertyPath() + " is incorrect";
            }
        }

        return "";
    }
}
