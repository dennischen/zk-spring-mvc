package org.zkoss.zkmvc.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.impl.BindContextImpl;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

public class SpringMVCViewModel implements Map<String, Object>{
	protected Map<String,Object> internalModel = new HashMap<String, Object>();
	protected String basePath;
	
	private boolean applyRequestAttr = false; 
	
	@Init
	public void init(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx){
		basePath = (String)Executions.getCurrent().getAttribute(RequestKeys.BASE_PATH);
		if(basePath==null){
			throw new RuntimeException("request base path  not found");
		}
	}
	
	@Override
	public int size() {
		return internalModel.size();
	}

	@Override
	public boolean isEmpty() {
		return internalModel.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return internalModel.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return internalModel.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		
		//for spring ModelMap(stored in request)
		Execution ex = Executions.getCurrent();
		Object val = null;
		if(ex!=null && (val = ex.getAttributes().get(key)) !=null){
			internalModel.put(key.toString(), val);
			return val;
		}
		
		return internalModel.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		BindUtils.postNotifyChange(null, null, this, key);
		return internalModel.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		BindUtils.postNotifyChange(null, null, this, key.toString());
		return internalModel.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		for(String nm:m.keySet()){
			BindUtils.postNotifyChange(null, null, this, nm);
		}
		internalModel.putAll(m);
	}

	@Override
	public void clear() {
		internalModel.clear();
	}

	@Override
	public Set<String> keySet() {
		return internalModel.keySet();
	}

	@Override
	public Collection<Object> values() {
		return internalModel.values();
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		throw new UnsupportedOperationException();
	}
	
	/** utility method */
	@Command
	public void detachComponent(@BindingParam("component")Component comp) throws Exception {
		comp.detach();
	}
	
	@Command("*")
	public void invokeCommand(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) throws Exception {
		final String command = ctx.getCommandName();
		
		final Map<String,Object> innerViewModelMap = new HashMap<String,Object>();
		
		final Map<String,Object> bindArgs = (Map<String,Object>)ctx.getAttribute(BindContextImpl.COMMAND_ARGS);
		
		//copy implement in DispatcherServlet
		final Execution exe = Executions.getCurrent();
		final HttpServletRequest request = (HttpServletRequest)exe.getNativeRequest();
		final HttpServletResponse response = (HttpServletResponse)exe.getNativeResponse();
		
		final String commandPath = basePath+"/"+command;
		final RequestDispatcher reqd = request.getRequestDispatcher(commandPath);
		final CommandRequest req = new CommandRequest(request);
		final CommandResponse res = new CommandResponse(response);
		
		req.setAttribute(CommandRequest.ATTR_CMD_REQUEST, req);		
		
		
		for(Map.Entry<String, Object> en:internalModel.entrySet()){
			if(applyRequestAttr){
				req.setAttribute(en.getKey(), en.getValue());
			}
			innerViewModelMap.put(en.getKey(), en.getValue());
		}
		
		if(bindArgs!=null){
			for(Map.Entry<String, Object> en:bindArgs.entrySet()){
				if(applyRequestAttr){
					req.setAttribute(en.getKey(), en.getValue());
				}
				innerViewModelMap.put(en.getKey(), en.getValue());
			}
		}
		
		ViewModelMap.instance().setInternalMap(innerViewModelMap);
		
		
		try{
			CommandContext mvcctx = CommandContext.init();
			mvcctx.setCommandName(command);
			
			reqd.include(req, res);
			
//			if(!mvcctx.isInvocked()){
//				
//			}
			
			String redirect = mvcctx.getRedirect();
			if(redirect!=null){
				exe.sendRedirect(redirect);
				return;
			}
			
			String popup = mvcctx.getPopup();
			if(popup!=null){
				exe.createComponents(popup, ctx.getBinder().getView(), null, null);
			}
			
		}finally{
			CommandContext.clean();
		}

		if(res.getError()!=-1){
			throw new RuntimeException("command request "+commandPath+" error, error code="+res.getError()+",message="+res.getErrorMessage());
		}
		
		
		Map<String, ?> model = (Map<String, ?>)req.getPayload(CommandRequest.PAYLOAD_MODEL_MAP);
		if(model==null){
			throw new RuntimeException("model map not found for command path "+commandPath+", error ");
		}
		
		for(String nm:model.keySet()){
			Object val = model.get(nm);
			if(val!=null){
				internalModel.put(nm, val);
			}else{
				internalModel.remove(nm);
			}
		}
		
		for(String nm:model.keySet()){
			BindUtils.postNotifyChange(null, null, this, nm);
		}
	}

}
