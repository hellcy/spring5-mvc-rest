package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {
  private CustomerMapper customerMapper = CustomerMapper.INSTANCE;
  private static final String FIRST_NAME = "yuan";

  @Test
  public void testCustomerMapper() {
    // given
    Customer customer = new Customer();
    customer.setFirstname(FIRST_NAME);

    // when
    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

    // then
    assertEquals(FIRST_NAME, customerDTO.getFirstname());
  }
}