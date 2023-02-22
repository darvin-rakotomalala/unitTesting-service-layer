package com.poc.service.business;

import com.poc.model.domain.Employee;

public interface EmployeeCUDSM {
    Employee saveEmployee(Employee employee);
    Employee updateEmployee(Employee updatedEmployee);
    void deleteEmployee(Long id);
}
