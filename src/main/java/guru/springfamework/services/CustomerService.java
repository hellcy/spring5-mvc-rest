package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
  List<CustomerDTO> getAllCustomers();
  CustomerDTO getCustomerById(Long id);
  CustomerDTO createNewCustomer(CustomerDTO customerDTO);
  CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id);
  CustomerDTO patchCustomer(CustomerDTO customerDTO, Long id);
  void deleteCustomerById(Long id);
}
