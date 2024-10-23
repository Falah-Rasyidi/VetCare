package au.edu.rmit.sept.webapp.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EmailVerificationTest {
    @Test
    @DisplayName("verifyEmailFormat should return true for a valid email")
    void verifyEmailFormat_ValidEmail_ReturnsTrue() {
        String validEmail = "user@example.com";
        assertTrue(EmailVerification.verifyEmailFormat(validEmail));
    }

    @Test
    @DisplayName("verifyEmailFormat should return true for a valid email")
    void verifyEmailFormat_ValidEmailWithPeriod_ReturnsTrue() {
        String validEmail = "user.coolperson@example.com";
        assertTrue(EmailVerification.verifyEmailFormat(validEmail));
    }

    @Test
    @DisplayName("verifyEmailFormat should return true for a valid email")
    void verifyEmailFormat_ValidEmailWithSpecialChar_ReturnsTrue() {
        String validEmail = "user+plus@example.com";
        assertTrue(EmailVerification.verifyEmailFormat(validEmail));
    }

    @Test
    @DisplayName("verifyEmailFormat should return false for an invalid email")
    void verifyEmailFormat_InvalidEmailNoAt_ReturnsFalse() {
        String validEmail = "user.example.com";
        assertFalse(EmailVerification.verifyEmailFormat(validEmail));
    }

//    @Test
//    @DisplayName("verifyEmailFormat should return false for an invalid email")
//    void verifyEmailFormat_InvalidEmailMultipleAt_ReturnsFalse() {
//        String validEmail = "user@coolperson@example.com@";
//        assertFalse(EmailVerification.verifyEmailFormat(validEmail));
//    }

    @Test
    @DisplayName("verifyEmailFormat should return false for an invalid email")
    void verifyEmailFormat_InvalidEmailWithSpace_ReturnsFalse() {
        String validEmail = "user@cool personexample.com@";
        assertFalse(EmailVerification.verifyEmailFormat(validEmail));
    }

    @Test
    @DisplayName("verifyEmailFormat should return exception for empty email")
    void verifyEmailFormat_NullInput_ThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            EmailVerification.verifyEmailFormat(null);
        });
    }
}
