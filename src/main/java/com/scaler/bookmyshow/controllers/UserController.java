package com.scaler.bookmyshow.controllers;

import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.dtos.SignUpRequestDto;
import com.scaler.bookmyshow.dtos.SignUpResponseDto;
import com.scaler.bookmyshow.models.User;
import com.scaler.bookmyshow.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        SignUpResponseDto responseDto = new SignUpResponseDto();

        User user = userService.signUp(requestDto.getEmail(),
                requestDto.getPassword());

        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        responseDto.setUserId(user.getId());

        return responseDto;
    }
}
