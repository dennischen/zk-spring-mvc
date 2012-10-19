package org.zkoss.zkmvc.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SpringApplicationContext implements ApplicationContextAware {

	private static ApplicationContext CONTEXT;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		CONTEXT = context;
	}
	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}
}