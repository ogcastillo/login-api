package com.ozzy.loginapi.services;

import com.ozzy.loginapi.models.User;

import java.util.function.Function;

public interface UserRegistrationValidator extends Function<User, ValidationResult> {


}
