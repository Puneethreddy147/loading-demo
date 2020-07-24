package com.verizon.vho.loading.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verizon.vho.loading.model.Bookstore;
import com.verizon.vho.loading.service.FileHandler;

@RestController
public class FileController {

	@Autowired
	private FileHandler fileHandlerImpl;

	@GetMapping("/file-load")
	public List<Bookstore> getBookData() throws Exception {

		System.out.println("Inside Book Data Controller");

		List<Bookstore> bookStore = fileHandlerImpl.readMultipleFIles();
		return bookStore;

	}

}
