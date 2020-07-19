package com.coding.test.FindPathBetweenCities;

import com.coding.test.FindPathBetweenCities.controller.CityConnectionController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CityConnectionController.class)
@AutoConfigureMockMvc
@ComponentScan("com.coding.test.FindPathBetweenCities")
public class CityConnectionControllerTests {

    @Autowired
    private CityConnectionController cityConnectionController;

    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestBuilders builder;
    private MockMvcResultMatchers matcher;

    @Test
    public void testDirectlyConnectedCities() throws Exception {

        ResultActions resultActions = this.mockMvc
                .perform(
                        builder
                                .get("/connected")
                                .param("origin", "Boston".toLowerCase())
                                .param("destination", "Providence".toLowerCase())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(matcher.status().isOk())
                .andExpect(matcher.content().string(Matchers.containsString("Yes")));
    }

    @Test
    public void testInvalidSourceCity() throws Exception{
        ResultActions resultActions = this.mockMvc
                .perform(
                        builder
                                .get("/connected")
                                .param("origin", "London".toLowerCase())
                                .param("destination", "Providence".toLowerCase())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(matcher.status().isOk())
                .andExpect(matcher.content().string(Matchers.containsString("No")));
    }

    @Test
    public void testInvalidDestinationCity() throws Exception{
        ResultActions resultActions = this.mockMvc
                .perform(
                        builder
                                .get("/connected")
                                .param("origin", "Boston".toLowerCase())
                                .param("destination", "London".toLowerCase())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(matcher.status().isOk())
                .andExpect(matcher.content().string(Matchers.containsString("No")));

    }

    @Test
    public void testTwoLevelConnectedCity() throws Exception{
        ResultActions resultActions = this.mockMvc
                .perform(
                        builder
                                .get("/connected")
                                .param("origin", "Dallas".toLowerCase())
                                .param("destination", "Houston".toLowerCase())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(matcher.status().isOk())
                .andExpect(matcher.content().string(Matchers.containsString("Yes")));
    }

    @Test
    public void testMultiLevelConnectedCity() throws Exception{
        ResultActions resultActions = this.mockMvc
                .perform(
                        builder
                                .get("/connected")
                                .param("origin", "Dallas".toLowerCase())
                                .param("destination", "Seattle".toLowerCase())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(matcher.status().isOk())
                .andExpect(matcher.content().string(Matchers.containsString("Yes")));
    }

    @Test
    public void testDisconnectedCities() throws Exception {

        ResultActions resultActions = this.mockMvc
                .perform(
                        builder
                                .get("/connected")
                                .param("origin", "San Fransisco".toLowerCase())
                                .param("destination", "Houston".toLowerCase())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(matcher.status().isOk())
                .andExpect(matcher.content().string(Matchers.containsString("No")));
    }

    @Test
    public void testEmptySourceCity() throws Exception {

        ResultActions resultActions = this.mockMvc
                .perform(
                        builder
                                .get("/connected")
                                .param("origin", "")
                                .param("destination", "Miami".toLowerCase())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(matcher.status().isOk())
                .andExpect(matcher.content().string(Matchers.containsString("Invalid Input")));
    }

    @Test
    public void testEmptyDestinationCity() throws Exception {

        ResultActions resultActions = this.mockMvc
                .perform(
                        builder
                                .get("/connected")
                                .param("origin", "New York".toLowerCase())
                                .param("destination", "")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(matcher.status().isOk())
                .andExpect(matcher.content().string(Matchers.containsString("Invalid Input")));
    }

    @Test
    public void testEmptyDestinationCities() throws Exception {

        ResultActions resultActions = this.mockMvc
                .perform(
                        builder
                                .get("/connected")
                                .param("origin", "")
                                .param("destination", "")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(matcher.status().isOk())
                .andExpect(matcher.content().string(Matchers.containsString("Invalid Input")));
    }
}
