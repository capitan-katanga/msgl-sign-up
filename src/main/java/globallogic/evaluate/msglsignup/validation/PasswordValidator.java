package globallogic.evaluate.msglsignup.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext constraintValidatorContext) {
        var regexOneUpperCase = "^(?=.*[A-Z])(?!.*[A-Z].*[A-Z])(?=.*\\d)[a-zA-Z\\d]+$";
        var regexTowNumbers = "^[a-zA-Z]*\\d[a-zA-Z]*\\d[a-zA-Z]*$";
        return passwordField != null &&
                passwordField.length() > 7 &&
                passwordField.length() < 13 &&
                passwordField.matches(regexOneUpperCase) &&
                passwordField.matches(regexTowNumbers);
    }
}
