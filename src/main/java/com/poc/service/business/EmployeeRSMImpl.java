package com.poc.service.business;

import com.poc.exception.ErrorsEnum;
import com.poc.exception.FunctionalException;
import com.poc.model.domain.Employee;
import com.poc.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeRSMImpl implements EmployeeRSM {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        try {
            log.info("----- getAllEmployees");
            return employeeRepository.findAll();
        } catch (Exception e) {
            log.error("Error getAllEmployees : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        try {
            log.info("----- getEmployeeById : {}", id);
            Optional<Employee> employeeFound = employeeRepository.findById(id);
            if (employeeFound.isEmpty()) {
                throw new FunctionalException(ErrorsEnum.ERR_MCS_EMPLOYEE_NOT_FOUND.getErrorMessage());
            }
            return employeeFound;
        } catch (Exception e) {
            log.error("Error getEmployeeById : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

}
