package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api/v1/customers/")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  public ResponseEntity<CustomerListDTO> getListOfCustomers() {
    return new ResponseEntity<CustomerListDTO>(
            new CustomerListDTO(customerService.getAllCustomers()), HttpStatus.OK
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
    return new ResponseEntity<CustomerDTO>(
            customerService.getCustomerById(id), HttpStatus.OK
    );
  }

  // @RequestBody will let Spring bind body object to Java object
  @PostMapping()
  public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
    return new ResponseEntity<>(
            customerService.createNewCustomer(customerDTO), HttpStatus.CREATED
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
    return new ResponseEntity<>(
            customerService.updateCustomer(customerDTO, id), HttpStatus.OK
    );
  }

  @PatchMapping("{id}")
  public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
    return new ResponseEntity<>(
            customerService.patchCustomer(customerDTO, id), HttpStatus.OK
    );
  }
}
