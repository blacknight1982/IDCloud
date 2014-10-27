package com.id.cloud.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.id.cloud.inspiration.security.IDCloudUserDetailsService;

@Configuration
@EnableWebSecurity
public class IDCloudSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("idcloudUserDetailsService")
	private IDCloudUserDetailsService idcloudUserDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder registry) throws Exception {
		registry.userDetailsService(idcloudUserDetailsService);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login?pleaseLogin"));
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/**").hasRole("USER").anyRequest().authenticated();
		http.exceptionHandling().accessDeniedPage("/login?accessDenied");
		//http.formLogin().loginPage("/login").failureUrl("/login?error").successHandler(successHandler());
		//http.formLogin().loginProcessingUrl("/login").usernameParameter("idcloud_username").passwordParameter("idcloud_password");
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout");
	}
	
	@Bean
	AuthenticationSuccessHandler successHandler(){
		return new SavedRequestAwareAuthenticationSuccessHandler();
	}
	
	@Bean(name="idcloudAuthenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
       return super.authenticationManagerBean();
	}
	
	/*@Bean
	public FilterChainProxy springSecurityFilterChain() throws Exception {
	    
		*//**
		 *	Register the following 10 filters in filter chain by default
		 *	SecurityContextPersistenceFilter
		 *	WebAsyncManagerIntegrationFilter
		 *	-LogoutFilter
		 *	UsernamePasswordAuthenticationFilter
		 *	RequestCacheAwareFilter
		 *	SecurityContextHolderAwareRequestFilter
		 *	AnonymousAuthenticationFilter
		 *	SessionManagementFilter
		 *	ExceptionTranslationFilter
		 *	FilterSecurityInterceptor
		 *//*
		
		// AuthenticationEntryPoint
		LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint("/login");
	    
		// HttpSessionSecurityContextRepository
	    HttpSessionSecurityContextRepository httpSessionSecurityContextRepository = new HttpSessionSecurityContextRepository();
	    
	    // SecurityExpressionHandler
	    SecurityExpressionHandler<FilterInvocation> securityExpressionHandler = new DefaultWebSecurityExpressionHandler();
	    
	    // SecurityContextPersistenceFilter
	    SecurityContextPersistenceFilter securityContextPersistenceFilter = new SecurityContextPersistenceFilter(httpSessionSecurityContextRepository);
	    
	    // WebAsyncManagerIntegrationFilter
	    WebAsyncManagerIntegrationFilter webAsyncManagerIntegrationFilter = new WebAsyncManagerIntegrationFilter();
	    
	    // LogoutFilter
	    //LogoutFilter logoutFilter = new LogoutFilter("/login?logout");
	    
	    // UsernamePasswordAuthenticationFilter
	    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
	    
	    // RequestCacheAwareFilter
	    RequestCacheAwareFilter requestCacheAwareFilter = new RequestCacheAwareFilter();
	    
	    // SecurityContextHolderAwareRequestFilter
	    SecurityContextHolderAwareRequestFilter securityContextHolderAwareRequestFilter = new SecurityContextHolderAwareRequestFilter();
	    securityContextHolderAwareRequestFilter.afterPropertiesSet();
	    
	    // AnonymousAuthenticationFilter
	    AnonymousAuthenticationFilter anonymousAuthenticationFilter = new AnonymousAuthenticationFilter("anonymous");
	    
	    // SessionManagementFilter
	    SessionManagementFilter sessionManagementFilter = new SessionManagementFilter(httpSessionSecurityContextRepository);
	    
	    // ExceptionTranslationFilter
	    ExceptionTranslationFilter exceptionTranslationFilter = new ExceptionTranslationFilter(entryPoint);
	    exceptionTranslationFilter.setAccessDeniedHandler(new AccessDeniedHandlerImpl());
	    exceptionTranslationFilter.afterPropertiesSet();
	    
	    // FilterSecurityInterceptor
	    FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
	    filterSecurityInterceptor.setAuthenticationManager(super.authenticationManagerBean());
	    filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
	    LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> map = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
	    map.put(new AntPathRequestMatcher("/**"), Arrays.<ConfigAttribute>asList(new SecurityConfig("isAuthenticated()")));
	    ExpressionBasedFilterInvocationSecurityMetadataSource ms = new ExpressionBasedFilterInvocationSecurityMetadataSource(map, securityExpressionHandler);
	    filterSecurityInterceptor.setSecurityMetadataSource(ms);
	    filterSecurityInterceptor.afterPropertiesSet();
	    
	    // SecurityFilterChain
	    SecurityFilterChain chain = new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**"),
	            securityContextPersistenceFilter,
	            webAsyncManagerIntegrationFilter,
	            //logoutFilter,
	            usernamePasswordAuthenticationFilter,
	            requestCacheAwareFilter,
	            securityContextHolderAwareRequestFilter,
	            anonymousAuthenticationFilter,
	            sessionManagementFilter,
	            exceptionTranslationFilter,
	            filterSecurityInterceptor);
	    
		
	    return new FilterChainProxy(chain);
	}*/
}
