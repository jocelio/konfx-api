package com.konfx.employee;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konfx.api.employee.rest.EmployeeController;
import com.konfx.api.employee.model.Employee;
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "test")
public class EmployeeInTests {

	MockMvc mockMvc;

	@Mock
	private EmployeeController employeeController;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private ObjectMapper mapper;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}

	@Test
	public void testEmployeeShouldBeRegistered() throws Exception {
		HttpEntity<Object> employee = getHttpEntity("{\"name\": \"JOCELIO\"}");
		ResponseEntity<Employee> response = template.postForEntity("/api/employee", employee, Employee.class);
		Assert.assertEquals(201,response.getStatusCode().value());
	}

	@Test
	public void testEmployeesShouldBeRetrieved() throws Exception {

		HttpEntity<Object> employee1 = getHttpEntity("{\"name\": \"Jocelio\"}");
		ResponseEntity<Employee> response1 = template.postForEntity("/api/employee", employee1, Employee.class);
		Assert.assertEquals(201,response1.getStatusCode().value());

		HttpEntity<Object> employee2 = getHttpEntity("{\"name\": \"Heloise\"}");
		ResponseEntity<Employee> response2 = template.postForEntity("/api/employee", employee2, Employee.class);
		Assert.assertEquals(201,response2.getStatusCode().value());


		ResponseEntity<String> response3 = template.getForEntity("/api/employee", String.class);


		List<Employee> o = mapper.readValue(response3.getBody(), new TypeReference<List<Employee>>() {});
		Assert.assertEquals(2, o.size());

	}

	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}



}
