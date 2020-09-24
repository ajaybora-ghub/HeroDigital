package com.hero.digital.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hero.digital.config.HeroDigitalProperties;
import com.hero.digital.util.HeroDigitalUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageService {

	@Autowired
	private HeroDigitalProperties properties;
	
	public void processImage(MultipartFile image, List<String> errorList) {
		String imageName = StringUtils.cleanPath(image.getOriginalFilename());
		try {
			Path imageLocation = Paths.get(properties.getImagesFolder()).toAbsolutePath()
					.normalize();
			if(!imageLocation.toFile().exists()) {
				Files.createDirectories(imageLocation); 
			}
			
			Path archiveLocation = Paths.get(properties.getArchiveFolder()).toAbsolutePath()
					.normalize();
			if(!archiveLocation.toFile().exists()) {
				Files.createDirectories(archiveLocation);
			}
			
			imageLocation = imageLocation.resolve(imageName);
			
			if (imageLocation != null && imageLocation.toFile().exists()) { 
				archiveLocation = archiveLocation.resolve(new StringBuilder(imageName.substring(0,imageName.lastIndexOf("."))).append("_")
						.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(HeroDigitalUtil.FORMATTER)))
						.append(imageName.substring(imageName.lastIndexOf(".")))
						.toString());
				Files.move(imageLocation, archiveLocation, StandardCopyOption.REPLACE_EXISTING);
				Files.copy(image.getInputStream(), imageLocation);
			} else { 
				Files.copy(image.getInputStream(), imageLocation);
			}
		} catch (Exception ex) {
			LOG.error("Exception in processing the image {}",ex);
			errorList.add("Not able to process the image : " + ex.getMessage());
		}
	}
	
}
