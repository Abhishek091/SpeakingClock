package com.wisdomleaf.SpeakingClock.Controller;

import com.wisdomleaf.SpeakingClock.Exceptions.InvalidTimeException;
import com.wisdomleaf.SpeakingClock.Service.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
public class ClockController {

    @Autowired
    ClockService clockService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @GetMapping("/speak/{time}")
    public String speakTime(@PathVariable String time) {
        try {
            return clockService.convertToWords(time);
        } catch (InvalidTimeException e) {
            return "Cannot convert time to words: " + e.getMessage();
        }
    }

    @GetMapping("/current-time")
    public String getCurrentTimeInWords() {
        LocalTime currentTime = LocalTime.now();
        try {
            return clockService.convertToWords(currentTime.format(formatter));
        } catch (InvalidTimeException e) {
            return "Cannot convert time to words: " + e.getMessage();
        }
    }
}
