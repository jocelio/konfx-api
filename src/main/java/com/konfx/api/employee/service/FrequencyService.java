package com.konfx.api.employee.service;

import com.konfx.api.employee.model.Frequency;
import com.konfx.api.employee.model.FrequencyGroup;

import java.util.List;

public interface FrequencyService {

	List<Frequency> findAll();

	List<FrequencyGroup> findGroup();

	Frequency findOne(Integer id);

	Frequency save(Frequency frequency);
}
