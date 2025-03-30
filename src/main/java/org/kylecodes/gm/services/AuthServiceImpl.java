package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtServiceImpl jwtServiceImpl;

    public String verify(UserDto userDto) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtServiceImpl.generateToken(userDto.getEmail());
        } else {
            return "fail";
        }

    }
}
