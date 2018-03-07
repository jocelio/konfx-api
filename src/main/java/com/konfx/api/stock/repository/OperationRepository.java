package com.konfx.api.stock.repository;

import com.konfx.api.stock.model.Operation;
import com.konfx.api.stock.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer>{
}
