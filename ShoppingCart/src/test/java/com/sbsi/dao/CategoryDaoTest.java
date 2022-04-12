package com.sbsi.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.sbsi.entity.Category;

@RunWith(MockitoJUnitRunner.class)
public class CategoryDaoTest {

	@InjectMocks
	private CategoryDaoImpl categoryDAO;

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	private List<Category> categories;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
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
		}).when(session).save(category);
		// when
		String actualCode = categoryDAO.addCategory(category);
		// then
		assertEquals(actualCode, expectedCode);
		assertEquals(category, categories.get(2));

	}

	@Test
	public void testShouldGetAllCategories() {
		// given
		@SuppressWarnings("unchecked")
		Query<Category> query = mock(Query.class);
		when(session.createQuery("SELECT c FROM Category c", Category.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(categories);
		// when
		List<Category> actualCategories = categoryDAO.getAllCategories();
		// then
		assertEquals(actualCategories, categories);
	}

	@Test
	public void testShouldFindCatgeoryByCode() {
		// given
		String code = "MBL";
		when(session.get(Category.class, code))
				.thenReturn(categories.stream().filter(u -> u.getCtgryCd().compareTo(code) == 0).findFirst().get());
		// when
		Category actualCategory = categoryDAO.findCatgeoryByCode(code);
		// then
		assertEquals(actualCategory.getCtgryCd(), code);
	}

	@After
	public void destroy() {
		categories.clear();
	}

}
