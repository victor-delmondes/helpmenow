package com.br.helpmenow.service;

import com.br.helpmenow.model.Department;
import com.br.helpmenow.model.UserApp;
import com.br.helpmenow.model.UserType;
import com.br.helpmenow.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserAppService {

    private final UserRepository userAppRepository;
    private final DepartmentService departmentService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserAppService(UserRepository userAppRepository, DepartmentService departmentService, BCryptPasswordEncoder passwordEncoder) {
        this.userAppRepository = userAppRepository;
        this.departmentService = departmentService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserApp> findAll() {
        return userAppRepository.findAll();
    }

    public UserApp findById(UUID id) {
        return userAppRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public UserApp findByEmail(String email) {
        return userAppRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void createNewUser(String email, String password, String name, Long departmentId, String userType) {
        Department department = departmentService.findById(departmentId);

        UserApp user = new UserApp();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setType(UserType.valueOf(userType));
        user.setDepartment(department);
        user.setActive(true);

        userAppRepository.save(user);
    }

    public void updateUserDetails(UUID id, String email, String name, Long departmentId, String userType) {
        UserApp user = findById(id);
        Department department = departmentService.findById(departmentId);

        user.setEmail(email);
        user.setName(name);
        user.setType(UserType.valueOf(userType));
        user.setDepartment(department);

        userAppRepository.save(user);
    }

    public void updateUserPassword(UUID userId, String rawPassword) {
        UserApp user = findById(userId);
        user.setPassword(passwordEncoder.encode(rawPassword));
        userAppRepository.save(user);
    }

    public boolean toggleUserStatus(UUID userId) {
        UserApp user = findById(userId);
        user.setActive(!user.isActive());
        userAppRepository.save(user);
        return user.isActive();
    }





}