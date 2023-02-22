package com.poc.service.business;

import com.poc.exception.ErrorsEnum;
import com.poc.exception.FunctionalException;
import com.poc.model.domain.Employee;
import com.poc.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeCUDSMImpl implements EmployeeCUDSM {

    private final EmployeeRepository employeeRepository;
    private final EmployeeRSM employeeRSM;

    @Override
    public Employee saveEmployee(Employee employee) {
        try {
            log.info("----- saveEmployee");
            Optional<Employee> employeeExist = employeeRepository.findByEmail(employee.getEmail());
            if (employeeExist.isPresent()) {
                throw new FunctionalException(ErrorsEnum.ERR_MCS_EMPLOYEE_ALREADY_EXIST.getErrorMessage());
            }
            return employeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Error saveEmployee : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        try {
            log.info("----- updateEmployee");
            var employeeFound = employeeRSM.getEmployeeById(updatedEmployee.getId()).get();
            employeeFound.setFirstName(updatedEmployee.getFirstName());
            employeeFound.setLastName(updatedEmployee.getLastName());
            employeeFound.setEmail(updatedEmployee.getEmail());
            return employeeRepository.save(employeeFound);
        } catch (Exception e) {
            log.error("Error updateEmployee : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        try {
            log.info("----- deleteEmployee : {}", id);
            var employeeFound = employeeRSM.getEmployeeById(id).get();
            employeeRepository.deleteById(employeeFound.getId());
        } catch (Exception e) {
            log.error("Error deleteEmployee : {} : {}", e.getMessage(), e);
            throw e;
        }
    }
}
