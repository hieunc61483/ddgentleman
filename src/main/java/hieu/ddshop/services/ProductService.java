package hieu.ddshop.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hieu.ddshop.daos.ProductDAO;
import hieu.ddshop.entities.ProductDTO;

@Service
@Transactional
public class ProductService {
	
	@Autowired
	private ProductDAO productDAO;

	public List<ProductDTO> findAll() {
		return productDAO.findAll();
	}

	public ProductDTO findById(final int id) {
		return productDAO.findById(id);
	}
	
	public List<ProductDTO> findByCategory(final int id) {
		return productDAO.findByCategory(id);
	}

	public void save(final ProductDTO product) {
		productDAO.save(product);
	}

	public void update(final ProductDTO product) {
		productDAO.update(product);
	}

	public void delete(final int id) {
		ProductDTO product = productDAO.findById(id);
		if (product != null) {
			productDAO.delete(product);
		}
	}
	public void changeActive(final int id) {
		ProductDTO product = productDAO.findById(id);
			if(product.getIsActive() == true) {
				product.setIsActive(false);
			} else {
				product.setIsActive(true);
			}
			productDAO.update(product);
	}
}
