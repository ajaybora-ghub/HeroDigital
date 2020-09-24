package com.hero.digital.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;

import com.hero.digital.model.HeroDigitalModel;

public class HeroDigitalUtil {

	public static final String FORWARD_SLASH = "//";
	
	public static final String PIPE = "|";
	
	public static final String PERSONS_SHEET_NAME = "PERSONS";

	public static final String FORMATTER = "yyyyMMddHHmmss";
	
	public static List<String> validate(HeroDigitalModel resp, List<String> errorMessages) {
		resp.getPersons().stream().forEach(p->{
			if (!StringUtils.isAlpha(p.getFirstName())) {
				errorMessages.add(p.getFirstName() + " First Name Not Valid Contains AlphaNumeric");
			}
			if (!StringUtils.isAlpha(p.getLastName())) {
				errorMessages.add(p.getLastName()+ " Last Name Not Valid Contains AlphaNumeric");
			}
			if (!isValidDate(p.getDob())) {
				errorMessages.add(p.getDob()  + " Not Valid Date , Date should be in format MM/DD/YYYY");
			}
		});
		return errorMessages;
	}

	public static boolean isValidDate(String date) {
		return GenericValidator.isDate(date, "MM/dd/yyyy", true);
	}

}
