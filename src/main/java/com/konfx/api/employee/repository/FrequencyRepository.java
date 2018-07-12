package com.konfx.api.employee.repository;

import com.konfx.api.employee.model.Frequency;
import com.konfx.api.employee.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequencyRepository extends JpaRepository<Frequency, Integer> {
}
