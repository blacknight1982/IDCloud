package com.id.cloud.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
//@PropertySource({"/WEB-INF/cfg-props/inspiration.properties"})
@Import({IDCloudDBConfiguration.class, IDCloudMVCConfiguration.class, IDCloudSecurityConfiguration.class})

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
		messageSource.setBasenames("/WEB-INF/i18n/messages","/WEB-INF/i18n/application","/WEB-INF/cfg-props/inspiration");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	@Bean
	public SessionLocaleResolver localeResolver(){
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}
	
	@Bean
    public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(268435456);
		return multipartResolver;
    }
}
