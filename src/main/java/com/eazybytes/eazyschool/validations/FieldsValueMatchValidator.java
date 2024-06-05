package com.eazybytes.eazyschool.validations;

import com.eazybytes.eazyschool.annotation.FieldsValueMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;
    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {

        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object fieldValue = new BeanWrapperImpl(value).
                getPropertyValue(this.field);
        Object fieldMatchValue = new BeanWrapperImpl(value).
                getPropertyValue(this.fieldMatch);

        //we don´t need to validate hash password if we add
        // 'spring.jpa.properties.javax.persistence.validation.mode=none'
        // into application.properties
/*        if (fieldValue != null){
            if (fieldValue.toString().startsWith("$2a")){
                return true;
            } else {
                return (fieldValue.equals(fieldMatchValue));
            }
        } else {
            return fieldMatchValue == null;
        }*/

        if (fieldValue != null){
            return (fieldValue.equals(fieldMatchValue));
        } else {
            return fieldMatchValue == null;
        }
    }
}
