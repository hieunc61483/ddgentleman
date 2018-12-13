package hieu.ddshop.daos;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hieu.ddshop.entities.ProductDTO;

@Repository(value = "productDAO")
@Transactional(rollbackFor = Exception.class)
public class ProductDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public void save(final ProductDTO product) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(product);
	}

	public void update(final ProductDTO product) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(product);
	}

	public ProductDTO findById(final int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(ProductDTO.class, id);
	}

	public void delete(final ProductDTO product) {
		Session session = this.sessionFactory.getCurrentSession();
		session.remove(product);
	}

	public List<ProductDTO> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("FROM ProductDTO", ProductDTO.class).getResultList();
	}
	
	public List<ProductDTO> findAllUnActive() {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("FROM ProductDTO p WHERE p.isActive = 0", ProductDTO.class).getResultList();
	}
	
	public List<ProductDTO> findByCategory(int categoryID) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM ProductDTO p WHERE p.categoryID = ?1 AND p.isActive = 1 ORDER BY p.CreateDate DESC";
		return session.createQuery(hql, ProductDTO.class)
				.setParameter(1, categoryID)
				.getResultList();
	}

}