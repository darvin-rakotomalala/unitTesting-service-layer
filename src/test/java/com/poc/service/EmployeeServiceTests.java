package com.poc.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.exception.FunctionalException;
import com.poc.model.domain.Employee;
import com.poc.repository.EmployeeRepository;
import com.poc.service.business.EmployeeCUDSM;
import com.poc.service.business.EmployeeCUDSMImpl;
import com.poc.service.business.EmployeeRSM;
import com.poc.service.business.EmployeeRSMImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeRSM employeeRService;

    @InjectMocks
    private EmployeeCUDSMImpl employeeCUDSM;

    @InjectMocks
    private EmployeeRSMImpl employeeRSM;

    private Employee employee;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Tojo")
                .lastName("Rakoto")
                .email("rakototojo@gmail.com")
                .build();
    }

    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() throws Exception {
        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeCUDSM.saveEmployee(employee);

        String json = mapper.writeValueAsString(savedEmployee);
        System.out.println(json);
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(FunctionalException.class, () -> {
            employeeCUDSM.saveEmployee(employee);
        });

        // then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Darvin")
                .lastName("Mampy")
                .email("mampydarvin@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // when -  action or the behaviour that we are going test
        List<Employee> employeeList = employeeRSM.getAllEmployees();

        String json = mapper.writeValueAsString(employeeList);
        System.out.println(json);

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Darvin")
                .lastName("Mampy")
                .email("mampydarvin@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        List<Employee> employeeList = employeeRSM.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when
        Employee savedEmployee = employeeRSM.getEmployeeById(employee.getId()).get();

        String json = mapper.writeValueAsString(savedEmployee);
        System.out.println(json);

        // then
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("JUnit test for getEmployeeById method which throws exception")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenThrowsException() throws Exception {
        // given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when
        Employee savedEmployee = employeeRSM.getEmployeeById(employee.getId()).get();
        String json = mapper.writeValueAsString(savedEmployee);
        System.out.println(json);
        System.out.println("------------------------------------------------------------------------------");

        org.junit.jupiter.api.Assertions.assertThrows(
                FunctionalException.class, () -> employeeRSM.getEmployeeById(5L).get());

        // then
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        given(employeeRService.getEmployeeById(employee.getId())).willReturn(Optional.of(employee));
        employee.setEmail("myrielle@gmail.com");
        employee.setFirstName("Myrielle");

        // when -  action or the behaviour that we are going test
        Employee updatedEmployee = employeeCUDSM.updateEmployee(employee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("myrielle@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Myrielle");
    }

    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        // given - precondition or setup
        Long employeeId = 1L;
        given(employeeRService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when -  action or the behaviour that we are going test
        employeeCUDSM.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

}
