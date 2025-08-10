package com.authservice.Service;

import com.authservice.DTO.APIResponse;
import com.authservice.DTO.UserDto;
import com.authservice.Entity.User;
import com.authservice.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    //Register User............................
    public APIResponse<String> register(UserDto userDto){

        //Check Weather username already exist..
        if(userRepository.existsByUsername(userDto.getUsername())){

            //API Response........
            APIResponse<String> response = new APIResponse<>();
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User With this username already exists");

            return response;
        }

        //Check email already exist.......
        if(userRepository.existsByEmail(userDto.getEmail())){

            //API Response........
            APIResponse<String> response = new APIResponse<>();
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User With this email already exists");

            return response;
        }

        //Encode Password before saving Database..........
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());

        //Copy Dto Content to Entity................
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setPassword(encryptedPassword);

        //Finally Save user and return API Response..............
        User savedUser = userRepository.save(user);

        if(savedUser == null){
            //Custome Exception
        }

        //API Response........
        APIResponse<String> response = new APIResponse<>();
        response.setMessage("Registration Success");
        response.setStatus(200);
        response.setData("User has been registered");
        return response;
    }
}
