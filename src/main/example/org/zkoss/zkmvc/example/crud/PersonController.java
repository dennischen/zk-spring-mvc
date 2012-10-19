package org.zkoss.zkmvc.example.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zkoss.zkmvc.core.CommandViewResolver;
import org.zkoss.zkmvc.core.annotation.RequestAttribute;

@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonDAO dao;

	public PersonController(){
		System.out.println("PersonController>>>>"+this);
	}
	
	@RequestMapping
	public void index(ModelMap model) {
		model.put("peopleList", dao.query());
//		return MVVMViewResolver.VIEW_PREFIX+"person";
	}
	
	@RequestMapping("/save")
	public void save(@RequestAttribute Person selectedPerson, ModelMap model) {
		dao.save(selectedPerson);
		model.put("selectedPerson", selectedPerson);
		model.put("peopleList", dao.query());
	}

	@RequestMapping("/delete")
	public void delete(@RequestAttribute Person selectedPerson, ModelMap model) {
		dao.delete(selectedPerson);
		model.put("selectedPerson", null);// to clean selection
		model.put("peopleList", dao.query());
	}

	@RequestMapping("/new")
	public void newPerson(ModelMap model) {
		model.put("selectedPerson", new Person("No", "Name"));
	}


	//navigation.
	
	@RequestMapping("/redirect")
	public String redirect(@RequestAttribute String path, ModelMap model) {
		return "redirect:"+path;
	}
	
	@RequestMapping("/popup")
	public String pupup(@RequestAttribute String zul, ModelMap model) {
		model.put("popupinfo", "Personal information");
		return "popup:"+zul;
	}
}
