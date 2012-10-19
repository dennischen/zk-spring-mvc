package org.zkoss.zkmvc.core.backup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.UrlPathHelper;

@Deprecated
public class MVVMDispatcherServlet extends DispatcherServlet {
	private static final long serialVersionUID = 1L;

//	private UrlPathHelper urlPathHelper = new UrlPathHelper();
//	transient static private ThreadLocal<ZulDispatcherServlet> instance = new ThreadLocal<ZulDispatcherServlet>();
	
	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		instance.set(this);
//		String basePath = urlPathHelper.getLookupPathForRequest(request);
//		request.setAttribute(RequestKeys.CONTROLLER_BASE_PATH, basePath);
		try{
			super.doService(request, response);
		}finally{
//			instance.set(null);
		}
	}

//	private static ZulDispatcherServlet instance(){
//		if(instance.get()==null){
//			throw new RuntimeException("ZulDispatcherServlet instance not found");
//		}
//		return instance.get();
//	}
//	
//	public static HandlerExecutionChain getHandlerPublic(HttpServletRequest request) throws Exception {
//		return instance().getHandler(request);
//	}
//
//	public static HandlerAdapter getHandlerAdapterPublic(Object handler) throws Exception  {
//		return instance().getHandlerAdapter(handler);
//	}
//

	

	
	
}
