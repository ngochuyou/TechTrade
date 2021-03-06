package com.green.finale.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SecurityWebConfig.class };
	}

//	@Override
//	protected Filter[] getServletFilters() {
//		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
//
//		encodingFilter.setForceEncoding(true);
//		encodingFilter.setEncoding("UTF-8");
//
//		return new Filter[] { encodingFilter, new DelegatingFilterProxy("springSecurityFilterChain"),
//				new OpenEntityManagerInViewFilter() };
//	}
	
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
