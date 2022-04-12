package com.sbsi.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.sbsi.dao.CategoryDao;
import com.sbsi.entity.Category;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Mock
	private CategoryDao categoryDao;

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
	public void testShouldAddCategory() {
		// given
		String expectedCode = "TV";
		Category category = new Category();
		category.setCtgry("TV");
		category.setCtgryCd("TV");
		doAnswer(c -> {
			Category ctgry = (Category) c.getArgument(0);
			categories.add(ctgry);
			return ctgry.getCtgryCd();
		}).when(categoryDao).addCategory(category);
		// when
		String actualCode = categoryService.addCategory(category);
		// then
		assertEquals(actualCode, expectedCode);
		assertEquals(category, categories.get(2));

	}

	@Test
	public void testShouldGetAllCategories() {
		// given
		when(categoryDao.getAllCategories()).thenReturn(categories);
		// when
		List<Category> actualCategories = categoryService.getAllCategories();
		// then
		assertEquals(actualCategories, categories);
	}

	@Test
	public void testShouldFindCatgeoryByCode() {
		// given
		String code = "MBL";
		when(categoryDao.findCatgeoryByCode(code))
				.thenReturn(categories.stream().filter(u -> u.getCtgryCd().compareTo(code) == 0).findFirst().get());
		// when
		Category actualCategory = categoryService.findCatgeoryByCode(code);
		// then
		assertEquals(actualCategory.getCtgryCd(), code);
	}

	@After
	public void destroy() {
		categories.clear();
	}

}
