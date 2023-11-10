package com.wisdomleaf.SpeakingClock;

import com.wisdomleaf.SpeakingClock.Controller.ClockController;
import com.wisdomleaf.SpeakingClock.Exceptions.InvalidTimeException;
import com.wisdomleaf.SpeakingClock.Service.ClockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClockController.class)
public class ClockControllerTest {

    @MockBean
    private ClockService clockService;

    @InjectMocks
    private ClockController clockController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(clockController).build();
    }

    @Test
    public void testSpeakTime_ValidTime_ReturnsWords() throws Exception {
        when(clockService.convertToWords("08:30")).thenReturn("It's eight thirty");
        mockMvc.perform(MockMvcRequestBuilders.get("/speak/08:30")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("It's eight thirty"));
    }

    @Test
    public void testSpeakTime_InvalidTime_ReturnsErrorMessage() throws Exception {
        when(clockService.convertToWords("invalidTime")).thenThrow(new InvalidTimeException("Invalid time format"));
        mockMvc.perform(MockMvcRequestBuilders.get("/speak/invalidTime")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cannot convert time to words: Invalid time format"));
    }

    @Test
    public void testGetCurrentTimeInWords() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime currentTime = LocalTime.now();
        when(clockService.convertToWords("12:00")).thenReturn("It's Midday");
        mockMvc.perform(MockMvcRequestBuilders.get("/current-time")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("It's Midday"));
    }
}