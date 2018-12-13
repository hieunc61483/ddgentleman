package hieu.ddshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hieu.ddshop.entities.ProductDTO;
import hieu.ddshop.services.ProductService;

@Controller
@RequestMapping("/sanpham")
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = { "/", "/danhsach" })
	public String listProduct(Model model) {
		model.addAttribute("listProduct", productService.findAll());
		return "product-list";
	}

	@RequestMapping("/product-save")
	public String insertProduct(Model model) {
		model.addAttribute("product", new ProductDTO());
		return "product-save";
	}

	@RequestMapping("/product-update/{id}")
	public String updateProduct(@PathVariable int id, Model model) {
		ProductDTO product = productService.findById(id);
		model.addAttribute("product", product);
		return "product-update";
	}

	@RequestMapping("/saveProduct")
	public String doSaveProduct(@ModelAttribute("ProductDTO") ProductDTO product, Model model) {
		productService.save(product);
		model.addAttribute("listProduct", productService.findAll());
		return "redirect:product-list";
	}

	@RequestMapping("/updateProduct")
	public String doUpdateProduct(@ModelAttribute("ProductDTO") ProductDTO product, Model model) {
		productService.update(product);
		model.addAttribute("listProduct", productService.findAll());
		return "redirect:product-list";
	}

	@RequestMapping("/deleteProduct/{id}")
	public String doDeleteProduct(@PathVariable int id, Model model) {
		productService.delete(id);
		model.addAttribute("listProduct", productService.findAll());
		return "redirect:/product-list";
	}
	
}