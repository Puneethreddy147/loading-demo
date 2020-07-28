package com.verizon.vho.loading.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.verizon.vho.loading.model.Bookstore;
import com.verizon.vho.loading.model.Status;
import com.verizon.vho.loading.repo.StatusRepo;

@Service
public class FileHandlerImpl implements FileHandler {
	
	@Autowired
	StatusRepo repo;

	Logger log = LoggerFactory.getLogger(FileHandlerImpl.class);

	@Override
	public Bookstore readFIle(MultipartFile file) throws Exception {

		Bookstore books = null;
		try {
			log.info(file.getResource().getFilename());
			JAXBContext jaxbContext = JAXBContext.newInstance(Bookstore.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			books = (Bookstore) jaxbUnmarshaller.unmarshal(file.getInputStream());
			insertStatus(file.getResource().getFilename(), "success");

		} catch (Exception e) {
			e.printStackTrace();
			insertStatus(file.getResource().getFilename(), "Failure");
		}

		return books;

	}

	private void insertStatus(String filename, String response) {
		// TODO Auto-generated method stub

		Status status = new Status();
		status.setFileName(filename);
		status.setStatus(response);
		status.setCreatedBy("userId");
		status.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		
		repo.save(status);

	}

	@Override
	public List<Bookstore> readMultipleFIles() throws Exception {

		List<Bookstore> bookstoreList = new ArrayList<Bookstore>();
		try {
			List<File> filesInFolder = Files.walk(Paths.get("/Users/puneethreddy/Documents/bookstore"))
					.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
			filesInFolder.stream().forEach(f -> {
				log.info(f.getName());
			});

			// bookstoreList = setBookStoreDate(filesInFolder);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return bookstoreList;
	}

	@Override
	public List<Bookstore> setBookStoreDate(List<MultipartFile> files) throws InterruptedException {
		List<Bookstore> bookstoreList = new ArrayList<Bookstore>();
		ExecutorService executor = Executors.newFixedThreadPool(files.size());

		// create a list to hold the Future object associated with Callable
		List<Callable<Void>> callables = new ArrayList<Callable<Void>>();

		// Create MyCallable instance
		files.stream().forEach(f -> {
			Callable<Void> callable = () -> {

				Bookstore bookStore = readFIle(f); // bookRepo.save(bookStore);
				bookstoreList.add(bookStore);

				return null;
			};

			callables.add(callable);

		});
		executor.invokeAll(callables);
		/*
		 * .stream().map(future -> { try { return future.get(); } catch (Exception e) {
		 * throw new IllegalStateException(e); } }).forEach(System.out::println);
		 */

		// shut down the executor service now
		executor.shutdown();

		return bookstoreList;
	}

}
