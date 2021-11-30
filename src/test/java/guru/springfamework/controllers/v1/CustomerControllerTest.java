package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {
  @Mock
  CustomerService customerService;

  CustomerController customerController;

  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    customerController = new CustomerController(customerService);

    mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
  }

  @Test
  public void testListCustomers() throws Exception{
    // given
    CustomerDTO customerDTO1 = new CustomerDTO();
    customerDTO1.setFirstname("Yuan");
    customerDTO1.setLastname("Cheng");
    customerDTO1.setCustomerUrl("/api/v1/customer/1");

    CustomerDTO customerDTO2 = new CustomerDTO();
    customerDTO2.setFirstname("Nan");
    customerDTO2.setLastname("Yang");
    customerDTO2.setCustomerUrl("/api/v1/customer/2");

    List<CustomerDTO> customerDTOList = Arrays.asList(customerDTO1, customerDTO2);

    // when
    when(customerService.getAllCustomers()).thenReturn(customerDTOList);

    // then
    mockMvc.perform(get("/api/v1/customers/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.customers", hasSize(2)));
  }

  @Test
  public void testGetCustomerById() throws Exception{
    // given
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstname("Yuan");
    customerDTO.setLastname("Cheng");
    customerDTO.setCustomerUrl("/api/v1/customer/1");

    // when
    when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

    // then
    mockMvc.perform(get("/api/v1/customers/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstname", equalTo("Yuan")));
  }

  @Test
  public void testCreateNewCustomer() throws Exception{
    // given
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstname("Yuan");
    customerDTO.setLastname("Cheng");

    CustomerDTO returnDTO = new CustomerDTO();
    returnDTO.setFirstname(customerDTO.getFirstname());
    returnDTO.setLastname(customerDTO.getLastname());
    returnDTO.setCustomerUrl("/api/v1/customers/1");

    // when
    when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(returnDTO);

    // then
    mockMvc.perform(post(("/api/v1/customers/"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(customerDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstname", equalTo("Yuan")))
            .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
  }
}