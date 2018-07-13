package com.konfx.api.employee.service;

import com.konfx.api.employee.model.Employee;
import com.konfx.api.employee.model.Frequency;
import com.konfx.api.employee.model.FrequencyGroup;
import com.konfx.api.employee.repository.FrequencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.vavr.collection.List.of;
import static java.math.BigDecimal.ROUND_FLOOR;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class FrequencyServiceImp implements FrequencyService {

	@Autowired
	private FrequencyRepository frequencyRepository;

	public List<Frequency> findAll(){
		return frequencyRepository.findAll();
	}

	public List<FrequencyGroup> findGroup(){

		List<Frequency> all = frequencyRepository.findAll();
		Set<Map.Entry<LocalDate, Map<Employee, List<Frequency>>>> entries = all.stream()
					.collect(groupingBy(f -> f.getDateTime().toLocalDate(), groupingBy(Frequency::getEmployee))).entrySet();

		return entries.stream().map(d ->

				d.getValue().entrySet().stream().map(entry -> {

					List<LocalDateTime> dateTimes = getSorted(entry);


					Long minutesWorked = of(ChronoUnit.MINUTES.between(dateTimes.get(0), dateTimes.get(1))
							, ChronoUnit.MINUTES.between(dateTimes.get(2), dateTimes.get(3))).toJavaStream().reduce((a, b) -> a + b).get();

					return FrequencyGroup.builder()
							.date(dateTimes.get(0).toLocalDate())
							.employee(entry.getKey())
							.sortedFrequencyPoint(dateTimes)
							.payroll(entry.getValue().stream().findAny().get().getPayroll())
							.workedHours(new BigDecimal(minutesWorked).divide(new BigDecimal(60), 2,ROUND_FLOOR))
							.build();

				}).collect(toList())

		).flatMap(List::stream).collect(toList());

	}

	private List<LocalDateTime> getSorted(Map.Entry<Employee, List<Frequency>> entry) {
		return entry.getValue().stream().sorted(comparing(Frequency::getDateTime)).map(Frequency::getDateTime).collect(toList());
	}

	public Frequency findOne(Integer id){
		return frequencyRepository.findOne(id);
	}


	public Frequency save(Frequency frequency){
		return frequencyRepository.save(frequency);
	}

}
