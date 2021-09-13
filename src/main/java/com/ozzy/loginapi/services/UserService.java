package com.ozzy.loginapi.services;

import com.ozzy.loginapi.dtos.UserAuthenticatedDto;
import com.ozzy.loginapi.dtos.UserDto;
import com.ozzy.loginapi.exceptions.DataNotCreatedException;
import com.ozzy.loginapi.exceptions.DataNotFoundException;
import com.ozzy.loginapi.exceptions.DataNotUpdatedException;
import com.ozzy.loginapi.exceptions.DataNotValidException;
import com.ozzy.loginapi.models.User;
import com.ozzy.loginapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    
    @Transactional(readOnly = true)
    public UserAuthenticatedDto getUser(Long id){
        Optional<User> user = userRepository.read(id);
        if(!user.isPresent()){
            throw new DataNotFoundException("USER_NOT_FOUND");
        }
        return user.map(value -> new UserAuthenticatedDto(value.getId(), value.getUsername())).orElse(null);
        
    }
    
    @Transactional(readOnly = true)
    public UserDto getUserAllData(Long id){
        Optional<User> user = userRepository.read(id);
        if(!user.isPresent()){
            throw new DataNotFoundException("USER_NOT_FOUND");
        }
        return user.map(value -> new UserDto(value.getFirstname(), value.getLastname(), value.getUsername(), value.getEmail(), value.getPassword())).orElse(null);
    }
    
    @Transactional
    public int updateUser(Long id,UserDto user){
        User userToUpdate = new User(user.getFirstname(),user.getLastname(),user.getUsername(),user.getEmail(),user.getPassword());
        userToUpdate.setId(id);
        int rowsUpdated = userRepository.update(userToUpdate);
        if (rowsUpdated > 0){
            throw new DataNotUpdatedException("USER_NOT_UPDATED");
        }
        return rowsUpdated;
    }
}
