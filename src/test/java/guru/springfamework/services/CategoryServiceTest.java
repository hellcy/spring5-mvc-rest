package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

  private static final Long ID = 2L;
  private static final String NAME = "Jimmy";

  CategoryService categoryService;

  @Mock
  CategoryRepository categoryRepository;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
  }

  @Test
  public void testGetAllCategories() {
    // given
    List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

    // when
    when(categoryRepository.findAll()).thenReturn(categories);

    List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

    // then
    assertEquals(3, categoryDTOS.size());
  }

  @Test
  public void testGetCategoryByName() {
    // given
    Category category = new Category();
    category.setId(ID);
    category.setName(NAME);

    // when
    when(categoryRepository.findByName(anyString())).thenReturn(category);

    CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

    // then
    assertEquals(ID, categoryDTO.getId());
    assertEquals(NAME, categoryDTO.getName());

  }
}