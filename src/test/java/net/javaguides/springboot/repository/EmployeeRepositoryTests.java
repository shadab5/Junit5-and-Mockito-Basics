package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Junit test for save employee Operation
    @DisplayName("Junit test for save employee Operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        //given = precondition or setup
        Employee employee = Employee.builder()
                .firstName("Shadab")
                .lastName("Azhar")
                .email("shadab5azhar@gmail.com")
                .build();

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    //Junit test for get all employee operation
    @DisplayName("Junit test for get all employee operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList(){
        //given = precondition or setup
        Employee employee = Employee.builder()
                .firstName("Shadab")
                .lastName("Azhar")
                .email("shadab5azhar@gmail.com")
                .build();
        Employee employee1 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .email("john.cena@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        //when - action or behaviour that we are going to test

        List<Employee> employeeList = employeeRepository.findAll();


        //then - verify the output

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }


        //Junit test for get employee by id operation
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
    //given = precondition or setup
    Employee employee = Employee.builder()
            .firstName("Shadab")
            .lastName("Azhar")
            .email("shadab5azhar@gmail.com")
            .build();

    employeeRepository.save(employee);

    //when - action or behaviour that we are going to test
    Employee employeeDB = employeeRepository.findById(employee.getId()).get();

    //then - verify the output
    assertThat(employeeDB).isNotNull();
    }

    //Junit test for get employee by email operation
    @DisplayName("Junit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){

        //given = precondition or setup
        Employee employee = Employee.builder()
                .firstName("Shadab")
                .lastName("Azhar")
                .email("shadab5azhar@gmail.com")
                .build();

        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }


}
