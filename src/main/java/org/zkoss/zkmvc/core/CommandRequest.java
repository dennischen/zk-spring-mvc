package org.zkoss.zkmvc.core;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CommandRequest extends HttpServletRequestWrapper{

	public static final String ATTR_CMD_REQUEST = CommandRequest.class.getName()+"$CommandRequest";
	
	public static final String PAYLOAD_MODEL_MAP = "$ModelMap$";
	
	
	Map<String,Object> payload = new HashMap<String,Object>();
	
	Map<String,Object> attr = new HashMap<String,Object>();
	
	public CommandRequest(HttpServletRequest request) {
		super(request);
	}
	
	public void setPayload(String name,Object value){
		payload.put(name, value);
	}
	
	public Object getPayload(String name){
		return payload.get(name);
	}

	@Override
	public Object getAttribute(String name) {
		if(attr.containsKey(name)) return attr.get(name);
		return super.getAttribute(name);
	}

	@Override
	public Enumeration getAttributeNames() {
		Set nms = new LinkedHashSet();
		nms.addAll(attr.keySet());
		nms.addAll(Collections.list(super.getAttributeNames()));
		return Collections.enumeration(nms);
	}

	@Override
	public void setAttribute(String name, Object o) {
		attr.put(name, o);
	}

	@Override
	public void removeAttribute(String name) {
		attr.remove(name);
	}

	
	
}
