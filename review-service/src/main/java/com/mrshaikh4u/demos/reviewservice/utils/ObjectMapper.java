package com.mrshaikh4u.demos.reviewservice.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * This is Util class for mapping objects , it uses ModelMapper
 * 
 * @author Rahil
 *
 */
@Component
@Data
public class ObjectMapper{

	private ModelMapper mapperInstance;

	public ObjectMapper() {
		mapperInstance = new ModelMapper();
		mapperInstance.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
}
