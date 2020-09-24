package com.hero.digital.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hero.digital.model.HeroDigitalModel;
import com.hero.digital.util.HeroDigitalUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RequestProcessor {

	@Autowired
	private ImageService imgService;
	
	@Autowired
	private ExcelService excelService;
	
	@Autowired
	private FileService fileService;

	public HeroDigitalModel processRequest(MultipartFile image, String jsonStr) {
		List<String> errorList = new ArrayList<>();
		HeroDigitalModel resp = processJson(jsonStr, errorList); 
		imgService.processImage(image, errorList); 
		if (resp != null && !CollectionUtils.isEmpty(errorList)) {
			resp.setErrorsDetails(errorList);
		}
		return resp;
	}

	
	private HeroDigitalModel processJson(String jsonStr, List<String> errorList) {
		ObjectMapper mapper = new ObjectMapper();
		HeroDigitalModel resp = null;
		try {
			JsonNode node = mapper.readTree(jsonStr);
			resp = mapper.treeToValue(node, HeroDigitalModel.class);
			if (resp != null) {
				HeroDigitalUtil.validate(resp, errorList);
				if (!CollectionUtils.isEmpty(errorList)) {
					resp.setErrorsDetails(errorList);
				} else {
					excelService.generateExcel(resp, errorList);
					fileService.generateFile(resp, errorList);
				}
			}
		} catch (IOException e) {
			LOG.error("Error in processing the JSON {}",e);
			errorList.add("Error in processing JSON : " + e.getMessage());
		}
		return resp;
	}

	

	

	

	

}
