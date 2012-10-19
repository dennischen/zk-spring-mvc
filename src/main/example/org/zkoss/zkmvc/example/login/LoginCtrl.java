package org.zkoss.zkmvc.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zkoss.zkmvc.core.ViewModelMap;

@Controller
@RequestMapping("/login")
public class LoginCtrl {

	@Autowired
	ViewModelMap viewModelMap;
	
	@RequestMapping
	public void index(ModelMap model) {
		model.put("loginForm", new LoginForm());
	}
	
	@RequestMapping("/submit")
	public String submit(ModelMap model){
		LoginForm form = (LoginForm)viewModelMap.get("loginForm");
		
		String nm = form.getName();
		String pd = form.getPassword();
		
		if("dennis".equals(nm) && "1234".equals(pd)){
			return "redirect:person";
		}
		
		model.put("loginMsg", "User or password is incorrect, hint:dennis/1234");
		return null;//same page
	}
	
	@RequestMapping("/clear")
	public void clear(ModelMap model){
		model.put("loginForm", new LoginForm());
	}
}
