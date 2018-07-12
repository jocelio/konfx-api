package com.konfx.employee;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konfx.api.employee.model.Employee;
import com.konfx.api.employee.rest.EmployeeController;
import io.vavr.control.Try;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "test")
public class FrequencyInTests {

	MockMvc mockMvc;

	@Mock
	private EmployeeController employeeController;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private ObjectMapper mapper;

	private final List<Employee> employees = io.vavr.collection.List.of(
				Employee.builder().name("Jocelio").build()
			, Employee.builder().name("Heloise").build()
	).toJavaList();

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
	}

	@Test
	public void testEmployeeShouldBeRegistered() throws Exception {

		employees.forEach(e -> {
			HttpEntity<Object> employee = getHttpEntity(Try.of(() -> mapper.writeValueAsString(e)).get());
			ResponseEntity<Employee> response = template.postForEntity("/api/employee", employee, Employee.class);
			Assert.assertEquals(201,response.getStatusCode().value());
		});


	}


	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}


}
