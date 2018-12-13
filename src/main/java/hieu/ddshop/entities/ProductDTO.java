package hieu.ddshop.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductDTO {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productID;

	@Column(name = "Code")
	private String productCode;

	@Column(name = "Name")
	private String productName;

	@Column(name = "Price")
	private Double price;

	@Column(name = "Description")
	private String description;

	@Column(name = "Status")
	private Boolean status;

	@Column(name = "CategoryID")
	private Integer categoryID;

	@Column(name = "IsSaleOff")
	private Boolean isSaleOff;

	@Column(name = "PercentSaleOff")
	private Double percentSaleOff;

	@Column(name = "Size")
	private String listSize;

	@Column(name = "Photo")
	private String photo;

	@Column(name = "IsActive")
	private Boolean isActive;

	@Column(name = "CreateDate")
	private Timestamp CreateDate;

	public ProductDTO() {
		super();
		this.isSaleOff = false;
		this.percentSaleOff = 0.0;
		this.isActive = true;
		this.CreateDate = new java.sql.Timestamp(new java.util.Date().getTime());
		this.status = true;
	}

	public ProductDTO(Integer productID, String productCode, String productName, Double price, String description,
			Boolean status, Integer categoryID, Boolean isSaleOff, Double percentSaleOff, String listSize, String photo,
			Boolean isActive, Timestamp createDate) {
		super();
		this.productID = productID;
		this.productCode = productCode;
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.status = status;
		this.categoryID = categoryID;
		this.isSaleOff = isSaleOff;
		this.percentSaleOff = percentSaleOff;
		this.listSize = listSize;
		this.photo = photo;
		this.isActive = isActive;
		this.CreateDate = createDate;
	}

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getIsSaleOff() {
		return isSaleOff;
	}

	public void setIsSaleOff(Boolean isSaleOff) {
		this.isSaleOff = isSaleOff;
	}

	public Double getPercentSaleOff() {
		return percentSaleOff;
	}

	public void setPercentSaleOff(Double percentSaleOff) {
		this.percentSaleOff = percentSaleOff;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getListSize() {
		return listSize;
	}

	public void setListSize(String listSize) {
		this.listSize = listSize;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public Timestamp getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(Timestamp createDate) {
		CreateDate = createDate;
	}
}
