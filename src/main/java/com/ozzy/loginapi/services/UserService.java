package com.ozzy.loginapi.services;

import com.ozzy.loginapi.dtos.UserAuthenticatedDto;
import com.ozzy.loginapi.dtos.UserDto;
import com.ozzy.loginapi.exceptions.DataNotCreatedException;
import com.ozzy.loginapi.exceptions.DataNotValidException;
import com.ozzy.loginapi.models.User;
import com.ozzy.loginapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ozzy.loginapi.services.UserRegistrationValidator.*;


@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Transactional
    public UserAuthenticatedDto saveUser(UserDto userDto ){
        ValidationResult result = isFirstNameValid()
                                            .and(isLastNameValid())
                                            .and(isUsernameValid())
                                            .and(isEmailValid())
                                            .and(isPasswordComplexityValid())
                                            .apply(userDto);
        
        if (result != ValidationResult.SUCCESS){
            throw new DataNotValidException(result.name());
        }
        
        User user = new User(userDto.getFirstname(), userDto.getLastname(), userDto.getUsername(), userDto.getEmail(), userDto.getPassword());
        user.setId(userRepository.create(user));
        if (user.getId()==0L){
            throw new DataNotCreatedException("DATA_COULD_NOT_BE_SAVED");
        }
    
        return new UserAuthenticatedDto(user.getId(), user.getUsername());
    }
}
