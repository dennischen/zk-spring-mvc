package org.zkoss.zkmvc.example.bean;
 
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.zkoss.zkmvc.core.CommandViewResolver;
import org.zkoss.zkmvc.core.annotation.RequestAttribute;
 
@Controller
@RequestMapping("/bean")
public class BeanController{
	
	
	@RequestMapping(method = RequestMethod.GET)
	public void index(@RequestParam(required=false) String fn, ModelMap model) {
		model.addAttribute("message", "Spring 3 MVC Hello Wrold "+fn);
	}
	
	@RequestMapping(value="/set")
	public void set(ModelMap model) {
		model.addAttribute("message", "Act ["+new Date()+"]");
	}
	
	@RequestMapping(value="/set2")
	public void set(@RequestAttribute(required=false) String message, ModelMap model) {
		model.addAttribute("message", "Act ["+message+"]:["+new Date()+"]");
	}
	
	
}