package com.br.helpmenow.factory;

import com.br.helpmenow.model.Department;
import com.br.helpmenow.model.UserApp;
import com.br.helpmenow.model.UserType;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFactory {

    public static UserApp create(String email, String rawPassword, String name, Department department, String userType, PasswordEncoder encoder) {
        UserApp user = new UserApp();
        user.setEmail(email);
        user.setPassword(encoder.encode(rawPassword));
        user.setName(name);
        user.setType(UserType.valueOf(userType));
        user.setDepartment(department);
        user.setActive(true);
        return user;
    }
}
