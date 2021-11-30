package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {
  @Mock
  CustomerRepository customerRepository;

  CustomerService customerService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
  }

  @Test
  public void testGetAllCustomers() {
    // given
    List<Customer> customers = Arrays.asList(new Customer(), new Customer());

    // when
    when(customerRepository.findAll()).thenReturn(customers);

    List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

    // then
    assertEquals(2, customerDTOS.size());
  }

  @Test
  public void testGetCustomerById() {
    // given
    Customer customer = new Customer();
    customer.setFirstname("Yuan");
    customer.setLastname("Cheng");

    // when
    when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

    CustomerDTO customerDTO = customerService.getCustomerById(1L);

    // then
    assertEquals("Yuan", customerDTO.getFirstname());
  }
}