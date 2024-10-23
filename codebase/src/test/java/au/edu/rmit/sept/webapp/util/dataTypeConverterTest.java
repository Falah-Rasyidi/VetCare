package au.edu.rmit.sept.webapp.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class dataTypeConverterTest {
    @Test
    @DisplayName("should add space for valid input")
    void htmlTimeToSQL_ValidInput_ReturnsCorrectFormat() {
        String htmlDateTime = "2005-04-21T15:30";
        String expectedSQLDateTime = "2005-04-21 15:30:00";
        String actualSQLDateTime = dataTypeConverter.htmlTimeToSQL(htmlDateTime);
        assertEquals(expectedSQLDateTime, actualSQLDateTime);
    }

    @Test
    @DisplayName("should throw null exception for no input")
    void htmlTimeToSQL_NullInput_ThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            dataTypeConverter.htmlTimeToSQL(null);
        });
    }

    @Test
    @DisplayName("should keep input the same")
    void htmlTimeToSQL_InvalidInput_ReturnsCorrectFormat() {
        String htmlDateTime = "2004-09-14 15:30";
        String expectedSQLDateTime = "2004-09-14 15:30:00";
        String actualSQLDateTime = dataTypeConverter.htmlTimeToSQL(htmlDateTime);
        assertEquals(expectedSQLDateTime, actualSQLDateTime);
    }

}
