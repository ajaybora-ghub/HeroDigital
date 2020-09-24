package com.hero.digital.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hero.digital.config.HeroDigitalProperties;
import com.hero.digital.model.HeroDigitalModel;
import com.hero.digital.model.Person;
import com.hero.digital.util.HeroDigitalUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
	
	@Autowired
	private HeroDigitalProperties properties;
	

	public void generateFile(HeroDigitalModel resp, List<String> errorList) throws IOException {
		Path outputFileLocation = Paths.get(properties.getOutputFolder()).toAbsolutePath()
				.normalize();
		if(!outputFileLocation.toFile().exists()) {
			Files.createDirectories(outputFileLocation);
		}
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter(outputFileLocation + HeroDigitalUtil.FORWARD_SLASH + properties.getOutputFile()))) {
			for (Person person : resp.getPersons()) {		
				writer.write(new StringBuilder(person.getFirstName()).append(HeroDigitalUtil.PIPE).append(person.getLastName())
						.append(HeroDigitalUtil.PIPE).append(person.getDob()).append("\n").toString());
			}
			
		} catch (Exception e) {
			LOG.error("Exception in generate the File {}",e);
			errorList.add("Unable to generating the File : " +e.getMessage());
		}
	}
	
}
