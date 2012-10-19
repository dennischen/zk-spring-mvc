package org.zkoss.zkmvc.example.hello;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zkoss.zkmvc.core.CommandViewResolver;
 
@Controller
@RequestMapping("/welcomezk")
public class ZulHelloController extends HelloController{
	public ZulHelloController(){
		view = "welcomezk";
	}
}