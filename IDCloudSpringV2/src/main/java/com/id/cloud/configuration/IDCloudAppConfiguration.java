package com.id.cloud.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@PropertySource({"/WEB-INF/cfg-props/inspiration.properties"})
public class IDCloudAppConfiguration {
	
	/**
	 * MessageSource Configuration
	 * Resolves localized messages*.properties and application.properties files in the application to allow for internationalization. 
	 * The messages*.properties files translate Roo generated messages which are part of the admin interface, 
	 * the application.properties resource bundle localizes all application specific messages such as entity names and menu items.
	 * @return ReloadableResourceBundleMessageSource
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("/WEB-INF/i18n/messages","/WEB-INF/i18n/application");
		return messageSource;
	}
}
