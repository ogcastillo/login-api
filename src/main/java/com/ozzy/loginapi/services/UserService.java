package com.ozzy.loginapi.services;

import com.ozzy.loginapi.dtos.UserAuthenticatedDto;
import com.ozzy.loginapi.dtos.UserDto;
import com.ozzy.loginapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public UserAuthenticatedDto saveUser(UserDto userDto ){
        
        return null;
    }
}
