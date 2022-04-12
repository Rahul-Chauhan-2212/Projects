package com.sbsi.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.sbsi.entity.Category;
import com.sbsi.service.CategoryService;

@Component
public class CategoryController implements Serializable {

	private static final long serialVersionUID = 7613240864358486138L;

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

	private Category category = new Category();

	@Autowired
	private CategoryService categoryService;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}

	public String addCategory() {
		try {
			categoryService.addCategory(category);
			LOGGER.info("Category added successfully");
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			FacesContext.getCurrentInstance().addMessage("addCategory", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Category added successfully with Code: " + this.category.getCtgryCd(), null));
			externalContext.getFlash().setKeepMessages(true);
			clearCategoryForm();
			return "categoryList.xhtml?faces-redirect=true";
		} catch (DataIntegrityViolationException e) {
			FacesContext.getCurrentInstance().addMessage("addCategory", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Category already exists with the entered Category Code!!!", null));
			LOGGER.error("Category addition failed. " + e.getMessage());
		}
		return "addCategory";

	}

	public String getCategoryPage() {
		return "categoryList.xhtml?faces-redirect=true";
	}

	public String getCategoryAddPage() {
		return "addCategory.xhtml?faces-redirect=true";
	}

	public void clearCategoryForm() {
		category = new Category();
	}

}
