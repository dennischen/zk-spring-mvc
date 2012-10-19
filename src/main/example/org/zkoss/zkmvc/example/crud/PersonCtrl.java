package org.zkoss.zkmvc.example.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zkoss.zkmvc.core.ViewModelMap;
import org.zkoss.zkmvc.core.annotation.ViewModelAttribute;

@Controller
@RequestMapping("/person")
public class PersonCtrl {

	@Autowired
	PersonDAO dao;
	
	@Autowired
	ViewModelMap viewModelMap;
	
	@RequestMapping
	public void index(ModelMap model) {
		model.put("peopleList", dao.query());
	}
	
	@RequestMapping("/save")
	public void save(@ViewModelAttribute Person selectedPerson, ModelMap model) {
		dao.save(selectedPerson);
		model.put("selectedPerson", selectedPerson);
		model.put("peopleList", dao.query());
	}

	@RequestMapping("/delete")
	public void delete(ModelMap model) {
		Person selectedPerson = (Person)viewModelMap.get("selectedPerson");
		if(selectedPerson==null){
			throw new RuntimeException("selectedPerson not found");
		}
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
	public String redirect(@ViewModelAttribute String path, ModelMap model) {
		return "redirect:"+path;
	}
	
	@RequestMapping("/popup")
	public String pupup(@ViewModelAttribute String zul, ModelMap model) {
		model.put("popupinfo", "Personal information");
		return "popup:"+zul;
	}
	
	@RequestMapping("/error")
	public void error(@ViewModelAttribute String error, ModelMap model) {
		System.out.println("Show never go to here");
	}
}
