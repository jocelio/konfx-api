package com.konfx.employee;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konfx.api.employee.model.Employee;
import com.konfx.api.employee.model.Frequency;
import com.konfx.api.employee.model.Payroll;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.vavr.collection.List.of;
import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toList;
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

	private final List<Employee> employees = of(
				Employee.builder().name("Jocelio").build()
//			, Employee.builder().name("Heloise").build()
	).toJavaList();

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
	}

	@Test
	public void testFrequenciesShouldBeRegistered() throws Exception {


		HttpEntity<Object> payroll = getHttpEntity(Try.of(() -> mapper.writeValueAsString(Payroll.builder().date(now()).isOpen(true).build())).get());
		ResponseEntity<Payroll> responsePayroll = template.postForEntity("/api/payroll", payroll, Payroll.class);
		Assert.assertEquals(200, responsePayroll.getStatusCode().value());

		Payroll payrollObj = responsePayroll.getBody();
		List<Employee> empl = new ArrayList<>();

		this.employees.forEach(e -> {
			HttpEntity<Object> employee = getHttpEntity(Try.of(() -> mapper.writeValueAsString(e)).get());
			ResponseEntity<Employee> response = template.postForEntity("/api/employee", employee, Employee.class);
			Assert.assertEquals(200,response.getStatusCode().value());
			empl.add(response.getBody());
		});


		IntStream.range(
				LocalDate.of(2018,7,1).getDayOfYear()
				, LocalDate.of(2018,7,2).getDayOfYear())
				.mapToObj(i->
						 empl.stream().map(e ->
							  of(
									 Frequency.builder()
											 .employee(e)
											 .payroll(payrollObj)
											 .dateTime(LocalDateTime.of(2018, 7, 1 ,7,randomMinutes(25, 35),0).withDayOfYear(i)).build()
									 , Frequency.builder()
											 .employee(e)
											 .payroll(payrollObj)
											 .dateTime(LocalDateTime.of(2018, 7, 1 ,11,randomMinutes(30, 45),0).withDayOfYear(i)).build()
									 , Frequency.builder()
											 .employee(e)
											 .payroll(payrollObj)
											 .dateTime(LocalDateTime.of(2018, 7, 1 ,13,randomMinutes(0, 15),0).withDayOfYear(i)).build()
									 , Frequency.builder()
											 .employee(e)
											 .payroll(payrollObj)
											 .dateTime(LocalDateTime.of(2018, 7, 1 ,18,randomMinutes(0, 20),0).withDayOfYear(i)).build()
							 )
						 ).flatMap(l -> l.toJavaStream()).collect(toList())
				).flatMap(f -> f.stream())
				.forEach(f -> {
					String value = "";
					try {
						value =	mapper.writeValueAsString(f);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
//					System.out.println(value);
					HttpEntity<Object> freq = getHttpEntity(value);
					ResponseEntity<Frequency> responseFreq = template.postForEntity("/api/frequency", freq, Frequency.class);
					Assert.assertEquals(200, responseFreq.getStatusCode().value());
				});
				//.forEach(f -> System.out.println(f.getEmployee().getName()+" "+f.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) ));



	}


	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}

	private int randomMinutes(int min, int max){
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}


}
