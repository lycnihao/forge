package net.koodar.forge.security;

import net.koodar.forge.security.config.SecurityConfigurer;
import net.koodar.forge.security.dynamicAuthorization.DynamicSecurityService;
import net.koodar.forge.security.properties.SecurityProperties;
import net.koodar.forge.security.superAdmin.SuperAdminInitializer;
import net.koodar.forge.security.userdetails.DefaultUserDetailsServiceImpl;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * AutoConfiguration
 *
 * @author liyc
 */
@Configuration
@EnableMethodSecurity
public class AutoConfiguration {

	private final SecurityProperties.Initializer initializer;

	private final ObjectProvider<SecurityConfigurer> securityConfigurers;


	public AutoConfiguration(ObjectProvider<SecurityConfigurer> securityConfigurers) {
		this.initializer = new SecurityProperties.Initializer();
		this.securityConfigurers = securityConfigurers;
	}

	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
														 PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Bean
	@ConditionalOnMissingBean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		return new DefaultUserDetailsServiceImpl(passwordEncoder);
	}

	@Bean
	@ConditionalOnMissingBean
	public SuperAdminInitializer superAdminInitializer() {
		return new SuperAdminInitializer(initializer) {
			@Override
			protected boolean isExistsSuperAdministratorUser() {
				return false;
			}

			@Override
			protected void generateAdministratorUser() {

			}
		};
	}

	@Bean
	@ConditionalOnMissingBean
	public DynamicSecurityService dynamicSecurityService() {
		return new DynamicSecurityService() {
			@Override
			public Map<String, Pair<String, String>> loadDataSource() {
				return Collections.emptyMap();
			}
		};
	}

	@Bean
	@ConditionalOnMissingBean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 不开启session会话模式
		http.sessionManagement((authorize) -> authorize.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// 是否禁用csrf
		if(initializer.isDisableCsrf()) {
			http.csrf().disable();
		}

		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers( "/error").permitAll())
				.formLogin(withDefaults())
				.logout(withDefaults());

		// 与其他 Configurer 集成
		securityConfigurers.orderedStream()
				.forEach(securityConfigurer -> securityConfigurer.configure(http));

		return http.build();
	}



}
