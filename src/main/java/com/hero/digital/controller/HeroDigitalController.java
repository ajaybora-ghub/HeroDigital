package com.hero.digital.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hero.digital.model.HeroDigitalModel;
import com.hero.digital.service.RequestProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HeroDigitalController {
	
	@Autowired
	private RequestProcessor heroDigitalService;
	
	@PostMapping(value = { "/parse" })
	public ResponseEntity<HeroDigitalModel> parseRequest(@RequestParam(value = "image") MultipartFile image,
			@RequestParam(value = "jsonStr") String jsonStr) {
		LOG.debug("Starting parsing ...");
		HeroDigitalModel resp = heroDigitalService.processRequest(image, jsonStr);
		if (CollectionUtils.isEmpty(resp.getErrorsDetails())) {
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}

	}
	
}
