package hieu.ddshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hieu.ddshop.entities.ProductDTO;
import hieu.ddshop.services.ProductService;
 
@Controller
@RequestMapping("/")
public class WelcomeController {
	
	@Autowired
	private ProductService productService;
 
    @RequestMapping("/")
    String home(ModelMap modal) {
        return "index";
    }
    
    @RequestMapping("/menu/{name}")
    String categories(@PathVariable String name, Model model) {
    	int choose;
    	switch(name) {
    	case "vest":
    		choose = 1;
    		break;
    	case "somi":
    		choose = 2;
    		break;
    	case "quan":
    		choose = 3;
    		break;
    	case "giay":
    		choose = 4;
    		break;
    	case "phukien":
    		choose = 5;
    		break;
    	default:
    		choose = 1;
    		break;
    	}
    	
    	model.addAttribute("choose", choose);
    	model.addAttribute("listProduct", productService.findByCategory(choose));
        return "categories";
    }
    
	@RequestMapping("/sanpham/{id}")
	public String viewProduct(@PathVariable int id, Model model) {
		ProductDTO product = productService.findById(id);
		model.addAttribute("product", product);
		return "single";
	}
}