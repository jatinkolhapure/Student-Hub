package com.college.campuscollab.controller;
import com.college.campuscollab.dto.LoginRequest;
import com.college.campuscollab.dto.LoginResponse;
import com.college.campuscollab.dto.RegisterRequest;
import com.college.campuscollab.entity.User;
import com.college.campuscollab.security.jwt.JwtUtil;
import com.college.campuscollab.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Password and Confirm Password do not match");
        }

        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        User user = userService.getUserByEmail(request.getEmail());

        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(
                new LoginResponse(
                        token,
                        user.getEmail(),
                        user.getRole().name()
                )
        );
    }

}
