package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static guru.springfamework.controllers.v1.CustomerController.BASE_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
public class CustomerController {

  public static final String BASE_URL = "/api/v1/customers";

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public CustomerListDTO getListOfCustomers() {
    return new CustomerListDTO(customerService.getAllCustomers());
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO getCustomerById(@PathVariable Long id) {
    return customerService.getCustomerById(id);
  }

  // @RequestBody will let Spring bind body object to Java object
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
    return customerService.createNewCustomer(customerDTO);
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
    return customerService.updateCustomer(customerDTO, id);
  }

  @PatchMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
    return customerService.patchCustomer(customerDTO, id);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomerById(id);
  }
}
