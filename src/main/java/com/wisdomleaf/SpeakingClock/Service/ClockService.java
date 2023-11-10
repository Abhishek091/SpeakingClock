package com.wisdomleaf.SpeakingClock.Service;

import com.wisdomleaf.SpeakingClock.Exceptions.InvalidTimeException;
import com.wisdomleaf.SpeakingClock.Util.DefaultClockUtility;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Service
public class ClockService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public String convertToWords(String time) throws InvalidTimeException {
        try {
            // Split the input time into hour and minute parts
            String[] parts = time.split(":");
            if (parts.length == 2) {
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);

                // Ensure the hour part has two digits by adding a leading zero
                String formattedTime = String.format("%02d:%02d", hour, minute);

                LocalTime localTime = LocalTime.parse(formattedTime, formatter);

                String result = "It's ";

                if (localTime.equals(LocalTime.NOON)) {
                    result += "Midday";
                } else if (localTime.equals(LocalTime.MIDNIGHT)) {
                    result += "Midnight";
                } else {
                    result += convertNumberToWords(localTime.getHour());

                    if (localTime.getMinute() != 0) {
                        result += " " + convertNumberToWords(localTime.getMinute());
                    }
                }
                return result;
            }
            return "Invalid time format";
        } catch (DateTimeParseException ex) {
            throw new InvalidTimeException("Invalid time format");
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid time format";
        }
    }


    private String convertNumberToWords(int num) {
        if (num < 20) {
            return DefaultClockUtility.units[num];
        } else {
            int tenPart = num / 10;
            int unitPart = num % 10;
            if (unitPart == 0) {
                return DefaultClockUtility.tens[tenPart];
            } else {
                return DefaultClockUtility.tens[tenPart] + " " + DefaultClockUtility.units[unitPart];
            }
        }
    }
}
