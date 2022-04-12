package com.sbsi.validator;

import java.util.Arrays;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesValidator(value = "fileUploadValidator")
public class FileUploadValidator implements Validator {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadValidator.class);

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Part file = (Part) value;

		FacesMessage message = null;

		try {

			if (file == null || file.getSize() <= 0 || file.getContentType().isEmpty())
				message = new FacesMessage("Select a valid file");
			else if (!Arrays.asList("image/jpeg", "image/jpg", "image/png").contains(file.getContentType()))
				message = new FacesMessage("Select JPEG/JPG/PNG file");
			else if (file.getSize() > 2000000)
				message = new FacesMessage("File size too big. File size allowed  is less than or equal to 2 MB.");

			if (message != null && !message.getDetail().isEmpty()) {
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				LOGGER.error("File upload is not done due to error-->" + message.getSummary());
				throw new ValidatorException(message);
			}

		} catch (Exception ex) {
			throw new ValidatorException(new FacesMessage(ex.getMessage()));
		}

	}

}