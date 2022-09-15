package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Shadab")
                .lastName("Azhar")
                .email("shadab5azhar@gmail.com")
                .build();
    }

        //Junit test for save Employee method
    @DisplayName("Junit test for save Employee method")
        @Test
        public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() throws ResourceNotFoundException {
            //given = precondition or setup


            BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

            BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
            //when - action or behaviour that we are going to test
            Employee savedEmployee = employeeService.saveEmployee(employee);

            //then - verify the output

            Assertions.assertThat(savedEmployee).isNotNull();
        }

    //Junit test for save Employee method
    @DisplayName("Junit test for save Employee method which throw Exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() throws ResourceNotFoundException {
        //given = precondition or setup


        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        //BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
        //when - action or behaviour that we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });


        //then - verify the output
        verify(employeeRepository,never()).save(any(Employee.class));

    }

        //Junit test for getAllEmployees method
        @DisplayName("Junit test for getAllEmployees method")
        @Test
        public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList(){
        //given = precondition or setup
          Employee  employee1 = Employee.builder()
                    .id(2L)
                    .firstName("Sonu")
                    .lastName("monu")
                    .email("sonu.monu@gmail.com")
                    .build();
        BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));
            //when - action or behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();
            //then - verify the output

            Assertions.assertThat(employeeList).isNotNull();
            Assertions.assertThat(employeeList.size()).isEqualTo(2);
        }

    //Junit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method (negative Scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){
        //given = precondition or setup
        Employee  employee1 = Employee.builder()
                .id(2L)
                .firstName("Sonu")
                .lastName("monu")
                .email("sonu.monu@gmail.com")
                .build();
        BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        //when - action or behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();
        //then - verify the output

        Assertions.assertThat(employeeList).isEmpty();
        Assertions.assertThat(employeeList.size()).isEqualTo(0);
    }

        //Junit test for gteEmployeeById method
            @DisplayName("Junit test for gteEmployeeById method")
            @Test
            public void givenEmployeeId_whenEmployeeId_thenReturnEmployeeObject(){
                //given = precondition or setup
                BDDMockito.given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

                //when - action or behaviour that we are going to test
                Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

                //then - verify the output
                Assertions.assertThat(savedEmployee).isNotNull();
            }

           //Junit test for updateEmployee
                @DisplayName("Junit test for updateEmployee")
               @Test
               public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
                   //given = precondition or setup
                   BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

                   employee.setEmail("shadab10azhar@gmail.com");
                   employee.setFirstName("Shaddu");


                   //when - action or behaviour that we are going to test
                   Employee updatedEmployee = employeeService.updateEmployee(employee);

                   //then - verify the output
                   Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("shadab10azhar@gmail.com");
                   Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("Shaddu");

               }

               //Junit test for delete Employee method
               @Test
               public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
                    //given = precondition or setup
                   long employeeId = 1L;
                   BDDMockito.willDoNothing().given(employeeRepository).deleteById(employeeId);

                   //when - action or behaviour that we are going to test
                   employeeService.deleteEmployee(employeeId);
                   //then - verify the output
                   BDDMockito.verify(employeeRepository,times(1)).deleteById(employeeId);
    }
}
