package hieu.ddshop.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

import hieu.ddshop.entities.ProductDTO;
import hieu.ddshop.services.ProductService;

@RestController
public class CategoryController {

	@Autowired
	private ProductService productService;
	
	private static String UPLOADED_FOLDER = "/images//Photo//";
	
	@RequestMapping(value = "/danhmuc/vest")
	public String findListVest() throws JsonProcessingException {
		return new Gson().toJson(productService.findByCategory(1));
	}
	
	@RequestMapping(value = "/danhmuc/somi")
	public String findListSomi() throws JsonProcessingException {
		return new Gson().toJson(productService.findByCategory(2));
	}
	
	@RequestMapping(value = "/danhmuc/quantay")
	public String findListQuanTay() throws JsonProcessingException {
		return new Gson().toJson(productService.findByCategory(3));
	}
	
	@RequestMapping(value = "/danhmuc/giay")
	public String findListShoes() throws JsonProcessingException {
		return new Gson().toJson(productService.findByCategory(4));
	}
	
	@RequestMapping(value = "/danhmuc/phukien")
	public String findListOthers() throws JsonProcessingException {
		return new Gson().toJson(productService.findByCategory(5));
	}
	
	@RequestMapping(value = "/sanpham/changeActive/{id}")
	public void changeActive(@PathVariable int id) {
		productService.changeActive(id);
	}
	
	@RequestMapping(value = "/sanpham/themsanpham")
	public void doSaveProduct(@RequestBody ProductDTO product) {
		ProductDTO p = new ProductDTO();
		p.setProductName(product.getProductName());
		p.setProductCode(product.getProductCode());
		p.setPrice(product.getPrice());
		p.setListSize(product.getListSize());
		p.setDescription(product.getDescription());
		p.setCategoryID(product.getCategoryID());
		p.setPhoto(product.getPhoto());
		
		productService.save(p);
	}
	
	@RequestMapping(value ="/sanpham/suasanpham/{id}")
	public void updateProduct(@RequestBody ProductDTO product, @PathVariable int id) {
		ProductDTO p = productService.findById(id);
		p.setProductName(product.getProductName());
		p.setProductCode(product.getProductCode());
		p.setPrice(product.getPrice());
		p.setListSize(product.getListSize());
		p.setDescription(product.getDescription());
		p.setCategoryID(product.getCategoryID());
		p.setPhoto(product.getPhoto());
		productService.update(p);
	}
	
	@RequestMapping(value ="/sanpham/upload/{id}")
	public void upload(@RequestParam("file") MultipartFile file, @PathVariable int id) {
		ProductDTO p = productService.findById(id);
		String code = p.getProductCode();
		p.setPhoto(code);
		
		if (file.isEmpty()) {
            
        }else {
        	try {
    			String fileName = code + ".png";
                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.write(path, bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		productService.update(p);
	}

}
