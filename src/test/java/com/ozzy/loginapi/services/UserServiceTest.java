package com.ozzy.loginapi.services;

import com.ozzy.loginapi.dtos.UserAuthenticatedDto;
import com.ozzy.loginapi.dtos.UserDto;
import com.ozzy.loginapi.exceptions.DataNotValidException;
import com.ozzy.loginapi.models.User;
import com.ozzy.loginapi.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


class UserServiceTest {
    
    @Mock
    private UserRepository mockUserRepository;
    
    @InjectMocks
    private UserService sut;
    
    
    @BeforeEach
    void setUp() {
        openMocks(this);
    }
    
    @AfterEach
    void tearDown() {
        mockUserRepository = null;
    }
    
    @Test
    void test_saveUserWithValidUserData() {
        //Arrange
        Long expectedId=1L;
        String expectedUsername;
        
        UserDto userDto = new UserDto("Ozzy",
                                      "Castillo",
                                      "ozzy.castillo",
                                      "ozzy.castillo@gmail.com","P@ssw0rd");
        
        when(mockUserRepository.create(any(User.class))).thenReturn(expectedId);
        
        expectedUsername = userDto.getUsername();
        
        //Act
        UserAuthenticatedDto actualResult = sut.saveUser(userDto);
        
        //Assert
       assertEquals(expectedUsername,actualResult.getUsername());
       verify(mockUserRepository, times(1)).create(any(User.class));
    }
    
    @Test
    public void test_saveUserWithWrongFirstname(){
        //Arrange
        UserDto userDto = new UserDto("12345",
                                      "Castillo",
                                      "ozzy.castillo",
                                      "ozzy.castillo@gmail.com","P@ssw0rd");
        
        ValidationResult expectedValidationResult = ValidationResult.FIRSTNAME_INVALID;
        
        //Act
        
        //Assert
        Throwable throwable = assertThrows(DataNotValidException.class, ()->{
           sut.saveUser(userDto);
        });
        
        assertEquals(String.valueOf(expectedValidationResult), throwable.getMessage());
    }
}