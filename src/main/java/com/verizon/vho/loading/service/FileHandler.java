package com.verizon.vho.loading.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.verizon.vho.loading.model.Bookstore;


@Service
public interface FileHandler {
	
	
	
	public List<Bookstore> readMultipleFIles() throws Exception;

	Bookstore readFIle(MultipartFile file) throws Exception;

	List<Bookstore> setBookStoreDate(List<MultipartFile> files) throws InterruptedException;

}
