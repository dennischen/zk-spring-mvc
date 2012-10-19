package org.zkoss.zkmvc.example.hello;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
@RequestMapping("/welcome")
public class HelloController {
	protected String view = "welcome";
	
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Spring 3 MVC Hello Wrold");
		return view; 
	}
	
	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public String printWelcome(@PathVariable String name, ModelMap model) {
		model.addAttribute("message", "Spring 3 MVC Hello "+name);
		return view; 
	}
	
	@RequestMapping(value="/{name}/{age}", method = RequestMethod.GET)
	public String printWelcome(@PathVariable String name, @PathVariable Integer age, ModelMap model) {
		model.addAttribute("message", "Spring 3 MVC Hello "+name+", you are " +age);
		return view; 
	}
 
}