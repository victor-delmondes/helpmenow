package com.br.helpmenow.service;

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
        return departmentRepository.findById(id).orElse(null);
    }

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public Department update(Department department) {
        return departmentRepository.save(department);
    }

    public void toggleStatus(Long id) {
        departmentRepository.findById(id).ifPresent(d -> {
            d.setStatus(!d.isStatus());
            departmentRepository.save(d);
        });
    }
}
