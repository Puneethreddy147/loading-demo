package com.verizon.vho.loading.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.verizon.vho.loading.model.Status;


@Repository
public interface StatusRepo extends JpaRepository<Status, Long>{

	
}
