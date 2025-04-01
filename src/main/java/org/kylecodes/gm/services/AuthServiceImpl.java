package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.AuthUserDto;
import org.kylecodes.gm.exceptions.AlreadyLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtServiceImpl jwtServiceImpl;

    public String verify(AuthUserDto authUserDto) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            throw new AlreadyLoggedInException();
        }
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(authUserDto.getEmail(), authUserDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtServiceImpl.generateToken(authUserDto.getEmail());
        } else {
            return "Failed to generate token";
        }

    }
}
