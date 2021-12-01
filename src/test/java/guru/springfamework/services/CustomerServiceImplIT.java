package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

// @DataJpaTest only create Repositories and database (data layer)
// controllers and services will not be created in Spring Application Context for tests
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  CategoryRepository categoryRepository;

  CustomerService customerService;

  @Before
  public void setUp() throws Exception {
    System.out.println("Loading Customer Data");
    System.out.println(customerRepository.findAll().size());

    // setup data for testing
    Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
    bootstrap.run(); // load data

    customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
  }

  @Test
  public void testPatchCustomerUpdateLastname() {
    String updatedLastname = "updated lastname";
    Long id = getCustomerIdValue();

    Customer originalCustomer = customerRepository.getOne(id);
    String originalFirstname = originalCustomer.getFirstname();
    String originalLastname = originalCustomer.getLastname();

    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setLastname(updatedLastname);

    // when
    customerService.patchCustomer(customerDTO, id);

    Customer updatedCustomer = customerRepository.findById(id).get();

    // then
    assertNotNull(updatedCustomer);
    assertEquals(updatedLastname, updatedCustomer.getLastname());
    assertThat(originalFirstname, equalTo(updatedCustomer.getFirstname()));
    assertThat(originalLastname, not(equalTo(updatedCustomer.getFirstname())));
  }

  @Test
  public void testPatchCustomerUpdateFirstname() {
    String updatedFirstname = "updated firstname";
    Long id = getCustomerIdValue();

    Customer originalCustomer = customerRepository.getOne(id);
    String originalFirstname = originalCustomer.getFirstname();
    String originalLastname = originalCustomer.getLastname();

    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setFirstname(updatedFirstname);

    // when
    customerService.patchCustomer(customerDTO, id);

    Customer updatedCustomer = customerRepository.findById(id).get();

    // then
    assertNotNull(updatedCustomer);
    assertEquals(updatedFirstname, updatedCustomer.getFirstname());
    assertThat(originalFirstname, not(equalTo(updatedCustomer.getFirstname())));
    assertThat(originalLastname, equalTo(updatedCustomer.getLastname()));
  }

  private Long getCustomerIdValue() {
    List<Customer> customers = customerRepository.findAll();

    System.out.println("Customers found: " + customers.size());

    // return first id
    return customers.get(0).getId();
  }
}