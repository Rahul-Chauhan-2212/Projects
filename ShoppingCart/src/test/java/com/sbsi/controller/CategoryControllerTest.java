package com.sbsi.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.sbsi.entity.Category;
import com.sbsi.service.CategoryService;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

	@InjectMocks
	private CategoryController categoryController;

	@Mock
	private CategoryService categoryService;

	private List<Category> categories;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		categories = new ArrayList<>();
		Category category1 = new Category();
		Category category2 = new Category();
		category1.setCtgryCd("MBL");
		category1.setCtgry("MOBILE");
		category2.setCtgryCd("TBLT");
		category2.setCtgry("TABLET");
		categories.add(category1);
		categories.add(category2);
	}

	@Test
	public void testShouldGetAllCategories() {
		// given
		when(categoryService.getAllCategories()).thenReturn(categories);
		// when
		List<Category> actualCategories = categoryController.getAllCategories();
		// then
		assertEquals(actualCategories, categories);
	}

}
