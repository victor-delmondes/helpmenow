package com.br.helpmenow.service;

import com.br.helpmenow.model.UserApp;
import com.br.helpmenow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAppService {

    private final UserRepository userAppRepository;

    public UserAppService(UserRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    public List<UserApp> findAll() {
        return userAppRepository.findAll();
    }

    public UserApp findById(Long id) {
        return userAppRepository.findById(id).orElse(null);
    }

    public UserApp save(UserApp user) {
        return userAppRepository.save(user);
    }

    public UserApp createUser(UserApp user) {
        return userAppRepository.save(user);
    }

    public UserApp updateUser(UserApp user) {
        return userAppRepository.save(user);
    }

}