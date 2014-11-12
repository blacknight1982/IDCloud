package com.id.cloud.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

@Configuration
@ComponentScan("com.id.cloud")
@PropertySource({"/WEB-INF/cfg-props/persistence-mysql.properties"})
@EnableWebMvc
@EnableTransactionManagement
public class IDCloudMVCConfiguration extends WebMvcConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	static Class<?> VIEW_CLASS = org.springframework.web.servlet.view.tiles3.TilesView.class;
	
	/**
	 * Resolves views selected for rendering by @Controllers to .jsp resources in the /WEB-INF/views directory
	 * @return UrlBasedViewResolver
	 */
	@Bean
	public ViewResolver tilesViewResolver() {
		  UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
		  urlBasedViewResolver.setViewClass(VIEW_CLASS);
		  return urlBasedViewResolver;
	}
	
	/**
	 * TilesConfigurator Configuration
	 * @return TilesConfigurer
	 */
	@Bean
	public TilesConfigurer tilesConfigurer(){
		String[] definitions = new String[]{"/WEB-INF/layouts/layouts.xml","/WEB-INF/views/**/views.xml"};
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(definitions);
		return tilesConfigurer;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
		webContentInterceptor.setCacheSeconds(0);
		webContentInterceptor.setUseExpiresHeader(true);
		webContentInterceptor.setUseCacheControlHeader(true);
		webContentInterceptor.setUseCacheControlNoStore(true);
	    registry.addInterceptor(webContentInterceptor);
	 }
}
