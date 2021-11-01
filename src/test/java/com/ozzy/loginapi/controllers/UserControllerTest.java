package com.ozzy.loginapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozzy.loginapi.dtos.UserAuthenticatedDto;
import com.ozzy.loginapi.dtos.UserDto;
import com.ozzy.loginapi.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
class UserControllerTest {
    private final WebApplicationContext webContext;
    private final ObjectMapper objectMapper;
    
    private MockMvc mockMvc;
    
    @Autowired
    public UserControllerTest(WebApplicationContext webContext, ObjectMapper objectMapper) {
        this.webContext = webContext;
        this.objectMapper = objectMapper;
    }
    
    @MockBean
    private UserService mockUserService;
    
    @BeforeEach
    void setUp(){this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }
    
    @AfterEach
    void tearDown(){ mockUserService = null;}
    
    
    @Test
    void test_saveUser() throws Exception {
        //Arrange
        UserDto userDto = new UserDto("Jane", "Doe", "jane.doe", "jane.doe@gmail.com", "P@ssw0rd");
        
        Long expectedId = 1L;
        String expectedUsername = userDto.getUsername();
        UserAuthenticatedDto userAuthenticatedDto = new UserAuthenticatedDto(expectedId,expectedUsername);
        
        String jsonNewUser = objectMapper.writeValueAsString(userDto);
        
        //Return the value when the mock is invoked
        when(mockUserService.saveUser(any(UserDto.class))).thenReturn(userAuthenticatedDto);
        
        //Act
        //Assert
        this.mockMvc.perform(post("/user/save")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(jsonNewUser)
                                     .accept(MediaType.APPLICATION_JSON))
                                     .andExpect(status().isCreated())
                                     .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                     .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(expectedUsername));
    }
}