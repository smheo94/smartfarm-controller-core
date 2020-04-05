package com.kt.smartfarm.config;

import com.kt.smartfarm.supervisor.mapper.DBSchemaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableAsync(proxyTargetClass = true)
public class EnvironmentConfig {
	Logger LOG = LoggerFactory.getLogger("EnvironmentConfig");
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Resource(name="dbSchemaMapper")
	DBSchemaMapper mapper;

	@Autowired
	SmartfarmInterceptorConfig config;

	@Bean
	public boolean UpdateDBSchema() {
		Long version = 20181127L;
		if( config.isSmartfarmSystem() ) {
			final List<Method> methodList = Arrays.stream(mapper.getClass().getMethods()).sorted(Comparator.comparing(Method::getName)).collect(Collectors.toList());
			for (Method m : methodList) {
				try {
					final String[] mSplitNames = m.getName().split("_");
					if (mSplitNames.length >= 2) {
						final long schemaVersion = Long.parseLong(mSplitNames[1]);
						if (schemaVersion > version) {
							m.invoke(mapper);
							LOG.info("Update Schema : {}", m.getName());
						}
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage());
				}
			}
		}
		return true;
	}
}