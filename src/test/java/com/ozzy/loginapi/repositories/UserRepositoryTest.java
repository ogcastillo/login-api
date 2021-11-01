package com.ozzy.loginapi.repositories;

import com.ozzy.loginapi.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    private final UserRepository sut;
    
    @Autowired
    public UserRepositoryTest(UserRepository sut) {
        this.sut = sut;
    }
    
    @Test
    @DirtiesContext
    @Rollback
    void create() {
        //Arrange
        User user = new User("Jack", "Doe", "jack.doe", "jack.doe@gmail.com", "P@ssw0rd");
        
        Long expectedId=3L;
        
        //Act
        Long currentId = sut.create(user);
        
        //Assert
        assertEquals(expectedId,currentId);
        
    }
    
    @Test
    void read() {
        //Arrange
        Long userId = 2L;
        Optional<User> currentUser = Optional.empty();
    
        User user = new User(2L,"Joe", "Doe", "joe.doe", "joe.doe@gmail.com", "P@ssw0rd");
        Optional<User> expectedUser = Optional.of(user);
        
        //Act
        currentUser = sut.read(userId);
        
        //Assert
        assertEquals(expectedUser.get().getUsername(), currentUser.get().getUsername());
        
    }
    
    @Test
    @DirtiesContext
    @Rollback
    void update() {
        //Arrange
        Long userId = 1L;
        Optional<User> currentUser = Optional.empty();
    
        User user = new User(1L,"Jane", "Doe", "jane.doe", "jane.doe@outlook.com", "P@ssw0rd");
        Optional<User> expectedUser = Optional.of(user);
        
        int rowUpdated;
        
        //Act
        rowUpdated= sut.update(user);
        currentUser=sut.read(userId);
        
        //Assert
        assertNotEquals(0,rowUpdated);
        assertEquals(user.getEmail(),currentUser.get().getEmail());
        
    }
    
    @Test
    void listAll() {
        //Arrange
        List<User> userList = new ArrayList<>();
        int expectSize= 2;
        
        //Act
        userList = sut.listAll();
        
        //Assert
        assertNotNull(userList);
        assertEquals(expectSize, userList.size());
    }
}