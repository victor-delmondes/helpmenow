package com.br.helpmenow.factory;

import com.br.helpmenow.model.Department;

public class DepartmentFactory {

    public static Department create(String name, String extension, String location) {
        return new Department(name, extension, location);
    }
}