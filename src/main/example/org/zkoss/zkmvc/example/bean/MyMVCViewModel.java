package org.zkoss.zkmvc.example.bean;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.HandlerMapping;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.impl.BindContextImpl;
import org.zkoss.zkmvc.core.SpringMVCViewModel;

@Init(superclass=true)
public class MyMVCViewModel extends SpringMVCViewModel {

	
//	@Command({"set","set2"})
//	public void set( @ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) throws Exception {
//		super.invokeCommand(ctx);
//	}

}
