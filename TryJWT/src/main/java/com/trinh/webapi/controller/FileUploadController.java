package com.trinh.webapi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trinh.webapi.model.User;
import com.trinh.webapi.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/image")
public class FileUploadController {
	@Autowired
	UserRepository userRepository;
	
	public String saveFile(String fileName, MultipartFile file, String folder) {
		Path uploadDirectory = Paths.get("src\\main\\resources\\images\\" + folder);
		fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss")) + StringUtils.delete(fileName, " ");
		
		try(InputStream inputStream = file.getInputStream()) {
			Path filePath = uploadDirectory.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println(fileName);
		return fileName;
	}

	public String saveFile2(String fileName, MultipartFile file, String folder) {
		Path uploadDirectory = Paths.get("src\\main\\resources\\images\\" + folder);
		fileName = StringUtils.delete(fileName, " ");
		
		try(InputStream inputStream = file.getInputStream()) {
			Path filePath = uploadDirectory.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println(fileName);
		return fileName;
}
	
	@PostMapping("/poster")
	public ResponseEntity<?> postPosterImage(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String newFileName = saveFile(fileName, file, "posters");
		
		return ResponseEntity.ok(newFileName);
	}

	@PostMapping("/product")
	public ResponseEntity<?> postProductImage(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String newFileName = saveFile(fileName, file, "products");
		
		return ResponseEntity.ok(newFileName);
	}
	
	@PostMapping("/category")
	public ResponseEntity<?> postCategoryImage(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String newFileName = saveFile(fileName, file, "categories");
		
		return ResponseEntity.ok(newFileName);
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> postUserImage(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String newFileName = saveFile(fileName, file, "users");
		
		return ResponseEntity.ok(newFileName);
	}
	
	@PostMapping("/user2")
	public ResponseEntity<?> postUserImage2(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String newFileName = saveFile2(fileName, file, "users");
		
		User user =userRepository.findById(5).get();
		user.setImage(newFileName);
		userRepository.save(user);
		return ResponseEntity.ok(newFileName);
	}
	
}
