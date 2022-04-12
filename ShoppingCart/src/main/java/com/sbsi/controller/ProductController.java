package com.sbsi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.sbsi.entity.Category;
import com.sbsi.entity.Product;
import com.sbsi.service.CategoryService;
import com.sbsi.service.ProductService;

@Component
public class ProductController implements Serializable {

	private static final long serialVersionUID = -1798429225324093636L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	private Product product = new Product();

	private Part uploadedFile;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public void upload() throws IOException {
		LOGGER.info("Going to upload file");
		String fileName = FilenameUtils.getName(uploadedFile.getName());
		String contentType = uploadedFile.getContentType();
		try (InputStream input = uploadedFile.getInputStream()) {
			product.setImage(IOUtils.toByteArray(input));
			FacesContext.getCurrentInstance().addMessage("fileUpload", new FacesMessage(FacesMessage.SEVERITY_INFO,
					String.format("File '%s' of type '%s' successfully uploaded!", fileName, contentType), null));
			LOGGER.info("File uploaded successfully...");

		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage("fileUpload", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					String.format("File '%s' of type '%s' upload failed!", fileName, contentType), null));
			LOGGER.error("File upload failed.." + e.getStackTrace());
		}

	}

	public String addProduct() {
		try {
			productService.addProduct(product);
			LOGGER.info("Product added successfully");
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			FacesContext.getCurrentInstance().addMessage("addProduct", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Product added successfully with Code: " + this.product.getPrdctCd(), null));
			externalContext.getFlash().setKeepMessages(true);
			clearProductForm();
			return "productList.xhtml?faces-redirect=true";
		} catch (DataIntegrityViolationException e) {
			FacesContext.getCurrentInstance().addMessage("addProduct", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Product already exists with the entered Product Code!!!", null));
			LOGGER.error("Product addition failed. " + e.getMessage());
		}
		return "addProduct";

	}

	public String getProductPage() {
		return "productList.xhtml?faces-redirect=true";
	}

	public String getProductAddPage() {
		return "addProduct.xhtml?faces-redirect=true";
	}

	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}

	public String convertImageByteToString(byte[] imageBytes) {
		return new String(Base64.getEncoder().encode(imageBytes));
	}

	public void clearProductForm() {
		product = new Product();
	}

}
