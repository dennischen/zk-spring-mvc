package org.zkoss.zkmvc.core;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.zkoss.zkplus.spring.SpringUtil;

@Component(ViewModelMap.NAME)
@Scope(value="request", proxyMode=ScopedProxyMode.TARGET_CLASS) 
public class ViewModelMap {

	static final public String NAME = "viewModelMap";
	
	Map<String,Object> internalMap;
	
	void setInternalMap(Map<String,Object> map){
		internalMap = map;
	}
	
	public Object get(String name){
		return internalMap==null?null:internalMap.get(name);
	}
	
	public static ViewModelMap instance(){
		ViewModelMap bean = (ViewModelMap)SpringApplicationContext.getBean(NAME);
		if(bean==null){
			throw new RuntimeException("ViewModelMap instance not found");
		}
		return bean;
	}
}
