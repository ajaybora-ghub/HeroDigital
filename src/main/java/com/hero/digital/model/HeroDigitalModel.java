package com.hero.digital.model;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HeroDigitalModel {
	
	private List<Person> persons = new LinkedList<>();
	
	private List<String> errorsDetails = new LinkedList<>();
	

}
