package net.javaguides.springboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

        //Junit test for
            @Test
            public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
                //given = precondition or setup
                Employee employee = Employee.builder()
                        .firstName("Shadab")
                        .lastName("Azhar")
                        .email("shadab10azhar@gmail.com")
                        .build();
                BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                        .willAnswer((invocation) -> invocation.getArgument(0));

                //when - action or behaviour that we are going to test
                ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)));




                //then - verify the output
                response.andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                                CoreMatchers.is(employee.getFirstName())))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                                CoreMatchers.is(employee.getLastName())))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                                CoreMatchers.is(employee.getEmail())));

            }

                //Junit test for All Get Employees REST API
                    @DisplayName("Junit test for All Get Employees REST API")
                    @Test
                    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
                        //given = precondition or setup
                        List<Employee> listOfEmployees = new ArrayList<>();
                        listOfEmployees.add(Employee.builder().firstName("Shadab").lastName("Azhar").email("shadab5azhar@gmail.com").build());
                        listOfEmployees.add(Employee.builder().firstName("Tony").lastName("Stark").email("tony@yahoo.com").build());

                        BDDMockito.given(employeeService.getAllEmployees()).willReturn(listOfEmployees);
                        //when - action or behaviour that we are going to test
                        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));

                        //then - verify the output

                        response.andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                                        CoreMatchers.is(listOfEmployees.size())));
                    }

             //Junit test for GET Employee by Id REST API (Positive Scenario)

            @DisplayName("Junit test for GET Employee by Id REST API (Positive Scenario)")
             @Test
             public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
                 //given = precondition or setup
                long employeeId = 1L;
                Employee employee = Employee.builder()
                        .firstName("Shadab")
                        .lastName("Azhar")
                        .email("shadab10azhar@gmail.com")
                        .build();

                BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

                 //when - action or behaviour that we are going to test
                ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}",employeeId));

                 //then - verify the output
                response.andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(employee.getFirstName())))
                                                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())))
                                                        .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));

             }


    //Junit test for GET Employee by Id REST API (Negative Scenario)

    @DisplayName("Junit test for GET Employee by Id REST API (Negative Scenario)")
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given = precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Shadab")
                .lastName("Azhar")
                .email("shadab10azhar@gmail.com")
                .build();

        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}",employeeId));

        //then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }
    //Junit test for update Employee REST API (Positive Scenario)
        @DisplayName("Junit test for update Employee REST API (Positive Scenario)")
        @Test
        public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {
            //given = precondition or setup
            long employeeId = 1L;
            Employee savedEmployee = Employee.builder()//Think as of DB object
                    .firstName("Shadab")
                    .lastName("Azhar")
                    .email("shadab10azhar@gmail.com")
                    .build();
            Employee updatedEmployee = Employee.builder() // Need to be updated
                    .firstName("Shad")
                    .lastName("Azhar")
                    .email("shad10azhar@gmail.com")
                    .build();

            BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
            BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation)->invocation.getArgument(0
            ));
            //when - action or behaviour that we are going to test


            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}",employeeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedEmployee)));

            //then - verify the output

            response.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(updatedEmployee.getFirstName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(updatedEmployee.getLastName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(updatedEmployee.getEmail())));
        }

    //Junit test for update Employee REST API (Negative Scenario)
    @DisplayName("Junit test for update Employee REST API (Negative Scenario)")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        //given = precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()//Think as of DB object
                .firstName("Shadab")
                .lastName("Azhar")
                .email("shadab10azhar@gmail.com")
                .build();
        Employee updatedEmployee = Employee.builder() // Need to be updated
                .firstName("Shad")
                .lastName("Azhar")
                .email("shad10azhar@gmail.com")
                .build();

        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation)->invocation.getArgument(0
        ));
        //when - action or behaviour that we are going to test


        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}",employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    //Junit test for delete employee REST API
        @DisplayName("Junit test for delete employee REST API")
        @Test
        public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
            //given = precondition or setup
            long employeeId = 1L;
            BDDMockito.willDoNothing().given(employeeService).deleteEmployee(employeeId);

            //when - action or behaviour that we are going to test
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}",employeeId));


            //then - verify the output
            response.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        }
}
