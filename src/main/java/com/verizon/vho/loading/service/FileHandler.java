package com.verizon.vho.loading.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.verizon.vho.loading.model.Bookstore;


@Service
public interface FileHandler {
	
	
	
	public List<Bookstore> readMultipleFIles() throws Exception;

	Bookstore readFIle(File f) throws Exception;

}
