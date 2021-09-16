package com.ozzy.loginapi.controllers;

import com.ozzy.loginapi.dtos.UserAuthenticatedDto;
import com.ozzy.loginapi.dtos.UserDto;
import com.ozzy.loginapi.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value="/save", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserAuthenticatedDto saveUser(@RequestBody UserDto newUser, HttpServletRequest req, HttpServletResponse res){
        return userService.saveUser(newUser);
    }
    
    @GetMapping(value="/get", produces=APPLICATION_JSON_VALUE)
    public UserAuthenticatedDto getUser(@RequestParam Long userId,HttpServletRequest req, HttpServletResponse res){
        return userService.getUser(userId);
    }
    
    @GetMapping(value = "/getAllData", produces=APPLICATION_JSON_VALUE)
    public UserDto getUserData(@RequestParam Long userId, HttpServletRequest req,HttpServletResponse res){
        return userService.getUserAllData(userId);
    }
    
    @PostMapping(value = "/update", consumes = APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE)
    public void updateUser(@RequestParam Long userId, @RequestBody UserDto userDto,HttpServletRequest req, HttpServletResponse res){
        if(userService.updateUser(userId, userDto) == 0){
            res.setStatus(500);
        }else{
            res.setStatus(200);
        };
    }
    
}
