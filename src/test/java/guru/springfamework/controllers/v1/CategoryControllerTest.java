package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {
  private static final String NAME = "Jim";

  @Mock
  CategoryService categoryService;

  // if use @InjectMocks, we no longer need to instantiate the controller when set up
  //@InjectMocks
  CategoryController categoryController;

  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    categoryController = new CategoryController(categoryService);

    mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
  }

  @Test
  public void testListCategories() throws Exception{
    // given
    CategoryDTO categoryDTO1 = new CategoryDTO();
    categoryDTO1.setId(1L);
    categoryDTO1.setName(NAME);

    CategoryDTO categoryDTO2 = new CategoryDTO();
    categoryDTO2.setId(2L);
    categoryDTO2.setName("Bob");

    List<CategoryDTO> categoryDTOList = Arrays.asList(categoryDTO1, categoryDTO2);

    // when
    when(categoryService.getAllCategories()).thenReturn(categoryDTOList);

    // then
    mockMvc.perform(get("/api/v1/categories/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categories", hasSize(2)));

    // result example
    //    {
    //      "categories": [
    //        {
    //          "id": 1,
    //                "name": "Fruits"
    //        },
    //        {
    //          "id": 2,
    //                "name": "Dried"
    //        },
    //        {
    //          "id": 3,
    //                "name": "Fresh"
    //        },
    //        {
    //          "id": 4,
    //                "name": "Exotic"
    //        },
    //        {
    //          "id": 5,
    //                "name": "Nuts"
    //        }
    //      ]
    //    }
  }

  @Test
  public void testGetByNameCategories() throws Exception{
    // given
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setName(NAME);
    categoryDTO.setId(1L);

    // when
    when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

    // then
    mockMvc.perform(get("/api/v1/categories/Jim")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo(NAME)));

    // result example
    //    {
    //      "id": 4,
    //            "name": "Exotic"
    //    }
  }
}