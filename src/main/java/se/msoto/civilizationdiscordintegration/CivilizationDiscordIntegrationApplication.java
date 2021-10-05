package se.msoto.civilizationdiscordintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import se.msoto.civilizationdiscordintegration.logging.RequestResponseLoggingFilter;

import java.util.Collections;

@SpringBootApplication
@EnableScheduling
public class CivilizationDiscordIntegrationApplication {

	private static final String LOGGING_BASE_PATH = "/*";

	public static void main(String[] args) {
		SpringApplication.run(CivilizationDiscordIntegrationApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<RequestResponseLoggingFilter> requestResponseLoggingFilter() {
		var filterRegistrationBean = new FilterRegistrationBean<RequestResponseLoggingFilter>();
		filterRegistrationBean.setFilter(new RequestResponseLoggingFilter());
		filterRegistrationBean.setUrlPatterns(Collections.singleton(LOGGING_BASE_PATH));
		return filterRegistrationBean;
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		return restTemplate;
	}

}
