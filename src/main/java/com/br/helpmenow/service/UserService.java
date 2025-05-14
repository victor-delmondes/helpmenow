package com.br.helpmenow.service;

import com.br.helpmenow.model.UserApp;
import com.br.helpmenow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserApp> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserStatus(Long id, boolean active) {
        UserApp user = userRepository.findById(id).orElseThrow();
        user.setActive(active);
        userRepository.save(user);
    }

}
