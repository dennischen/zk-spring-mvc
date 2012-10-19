package org.zkoss.zkmvc.example.crud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PersonDAO {

	static List<Person> all = Collections.synchronizedList(new ArrayList<Person>());
	static{
		all.add(new Person("Dennis","Chen"));
		all.add(new Person("Alex","Chung"));
		all.add(new Person("Pao","Wang"));
		all.add(new Person("Tim","A"));
		all.add(new Person("Lance","B"));
		all.add(new Person("Simon","C"));
		all.add(new Person("Ian","D"));
	}
	
	public PersonDAO(){
	}
	
	public void save(Person person){
//		all(selected);
		if(all.indexOf(person)==-1){
			all.add(person);
		}
	}
	
	public void delete(Person person){
		all.remove(person);
	}
	
	public List<Person> query(){
		return Collections.unmodifiableList(all);
	}
}
