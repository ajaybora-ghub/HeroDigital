package com.hero.digital.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "hero.digital")
@Data
@Configuration
public class HeroDigitalProperties {

	private static final String HERO_DIGITAL = "hero.digital";

    @Value("${" + HERO_DIGITAL + ".images.folder}")
    private String imagesFolder;
    
    @Value("${" + HERO_DIGITAL + ".archive.folder}")
    private String archiveFolder;
    
    @Value("${" + HERO_DIGITAL + ".xls.folder}")
    private String excelFolder;
    
    @Value("${" + HERO_DIGITAL + ".xls.name}")
    private String excelName;
    
    @Value("${" + HERO_DIGITAL + ".output.folder}")
    private String outputFolder;
    
    @Value("${" + HERO_DIGITAL + ".output.file}")
    private String outputFile;
    
	
}
