package com.sbsi.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbsi.entity.Category;
import com.sbsi.service.CategoryService;

@Component
public class CategoryConverter implements Converter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryConverter.class);

	@Autowired
	private CategoryService categoryService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		try {
			return categoryService.findCatgeoryByCode(value);
		} catch (Exception e) {
			LOGGER.error(
					new ConverterException(new FacesMessage(value + " is not a valid Category Code"), e).getMessage());
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		if (modelValue == null) {
			return ""; // Never return null here!
		}

		if (modelValue instanceof Category) {
			return String.valueOf(((Category) modelValue).getCtgryCd());
		} else {
			LOGGER.error(
					new ConverterException(new FacesMessage(modelValue + " is not a valid Category")).getMessage());
			return "";
		}
	}

}