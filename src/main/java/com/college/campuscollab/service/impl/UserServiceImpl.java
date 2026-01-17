package com.college.campuscollab.service.impl;

import com.college.campuscollab.dto.RegisterRequest;
import com.college.campuscollab.entity.Role;
import com.college.campuscollab.entity.User;
import com.college.campuscollab.repository.UserRepository;
import com.college.campuscollab.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepository.existsByRollNumber(request.getRollNumber())) {
            throw new RuntimeException("Roll number already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRollNumber(request.getRollNumber());
        user.setCourse(request.getCourse());
        user.setSemester(request.getSemester());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        //  AUTO ROLE ASSIGNMENT LOGIC
        if (request.getSemester() != null && request.getSemester() >= 5) {
            user.setRole(Role.SENIOR);
        } else {
            user.setRole(Role.STUDENT);
        }

        userRepository.save(user);
        return user;
    }




    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
