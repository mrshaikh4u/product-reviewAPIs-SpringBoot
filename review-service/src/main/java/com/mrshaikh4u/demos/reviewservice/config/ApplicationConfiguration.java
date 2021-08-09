package com.mrshaikh4u.demos.reviewservice.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This is Application's configuration class serves centralized management of
 * confs
 * 
 * @author Rahil
 *
 */
@Configuration
@EnableSwagger2
public class ApplicationConfiguration {
	public static final String REVIEW_CONTROLLER_CLASS = "Reviews Controller";
	/**
	 * Used to enable swagger to document REST APIs
	 * 
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
		                                              .apis(RequestHandlerSelectors.basePackage(
		                                                      "com.mrshaikh4u.demos.reviewservice.controller"))
		                                              .paths(PathSelectors.regex("/.*"))
		                                              .build()
													  .tags(new Tag(REVIEW_CONTROLLER_CLASS,"Operations for reviews management"))
		                                              .apiInfo(apiEndPointsInfo());
	}

	/**
	 * Swagger REST APIs
	 * 
	 * @return
	 */
	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("REST APIs Documentation")
		                           .description(
		                                   "This page describes REST endpoints used in review application and short description for each APIs including input and outputs for more details feel free to reach @ below contact")
		                           .contact(new Contact("Rahil Shaikh", "https://www.test.com/en/",
		                                   "mohamedrshaikh@gmail.com"))
		                           .build();
	}

	/**
	 * Used to enable CORS in application
	 * 
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				        .allowedOrigins("http://localhost");
			}

		};
	}
}