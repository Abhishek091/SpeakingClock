package com.wisdomleaf.SpeakingClock;

import com.wisdomleaf.SpeakingClock.Exceptions.InvalidTimeException;
import com.wisdomleaf.SpeakingClock.Service.ClockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ClockServiceTest {

    @InjectMocks
    private ClockService clockService;

    @Test
    void convertToWords_ValidTime_ReturnsWords() throws InvalidTimeException {
        String result = clockService.convertToWords("5:30");
        assertEquals("It's five thirty", result);
    }

    @Test
    void convertToWords_Noon_ReturnsMidday() throws InvalidTimeException {
        String result = clockService.convertToWords("12:00");
        assertEquals("It's Midday", result);
    }

    @Test
    void convertToWords_Midnight_ReturnsMidnight() throws InvalidTimeException {
        String result = clockService.convertToWords("00:00");
        assertEquals("It's Midnight", result);
    }

    @Test
    void convertToWords_InvalidTime_ThrowsInvalidTimeException() {
        InvalidTimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                InvalidTimeException.class,
                () -> clockService.convertToWords("invalidTime")
        );
        assertEquals("Invalid time format", exception.getMessage());
    }

}
