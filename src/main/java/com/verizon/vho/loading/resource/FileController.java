package com.verizon.vho.loading.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.verizon.vho.loading.model.Bookstore;
import com.verizon.vho.loading.service.FileHandler;

@RestController
public class FileController {

	@Autowired
	private FileHandler fileHandlerImpl;

	@PostMapping("/file-load")
	public Bookstore getBookData(@RequestParam("files") MultipartFile file) throws Exception {

		System.out.println("Inside Book Data Controller");

		Bookstore bookStore = fileHandlerImpl.readFIle(file);

		return bookStore;

	}

}
