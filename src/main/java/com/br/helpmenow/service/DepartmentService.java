package com.br.helpmenow.service;

import com.br.helpmenow.factory.DepartmentFactory;
import com.br.helpmenow.model.Department;
import com.br.helpmenow.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
    }

    public void createNewDepartment(String name, String extension, String location) {
        Department dep = DepartmentFactory.create(name, extension, location);
        departmentRepository.save(dep);
    }

    public void updateDepartment(Long id, String name, String extension, String location) {
        Department dep = findById(id);
        dep.setName(name);
        dep.setExtension(extension);
        dep.setLocation(location);
        departmentRepository.save(dep);
    }

    public boolean toggleStatus(Long id) {
        Department dep = findById(id);
        dep.setStatus(!dep.isStatus());
        departmentRepository.save(dep);
        return dep.isStatus();
    }

}
