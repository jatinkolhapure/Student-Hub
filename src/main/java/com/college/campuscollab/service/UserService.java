package com.college.campuscollab.service;

import com.college.campuscollab.dto.RegisterRequest;
import com.college.campuscollab.entity.User;

public interface UserService {

    User registerUser(RegisterRequest user);

    User getUserByEmail(String email);

    User getUserById(Long id);


}
