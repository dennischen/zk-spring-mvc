package org.zkoss.zkmvc.core;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.UrlPathHelper;

public class MVVMDispatcherFilter implements Filter {

	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		
		String basePath = urlPathHelper.getLookupPathForRequest((HttpServletRequest)request);
		request.setAttribute(RequestKeys.BASE_PATH, basePath);
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
