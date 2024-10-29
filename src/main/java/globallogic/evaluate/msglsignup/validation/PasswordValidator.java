package globallogic.evaluate.msglsignup.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Validates whether a password field meets the defined criteria.
     *
     * <p>The password must comply with the following rules:
     * <ul>
     *   <li>Must not be null.</li>
     *   <li>Must be between 8 and 12 characters long (inclusive).</li>
     *   <li>Must contain exactly one uppercase letter.</li>
     *   <li>Must contain at least two numeric digits.</li>
     * </ul>
     *
     * <p>Valid examples:
     * <ul>
     *   <li>"Password12"</li>
     *   <li>"12Dummy"</li>
     * </ul>
     *
     * @param passwordField The password to validate.
     * @param constraintValidatorContext The validator context used to build violation messages.
     * @return {@code true} if the password is valid according to the defined rules; otherwise, {@code false}.
     */
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
