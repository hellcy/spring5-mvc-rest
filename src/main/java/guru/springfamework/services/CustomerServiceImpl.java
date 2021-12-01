package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  @Override
  public List<CustomerDTO> getAllCustomers() {
    return customerRepository.findAll()
            .stream()
            .map(customer -> {
              CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
              customerDTO.setCustomerUrl("/api/v1/customer/" + customer.getId());
              return customerDTO;
            })
            .collect(Collectors.toList());
  }

  @Override
  public CustomerDTO getCustomerById(Long id) {
    return customerRepository.findById(id)
            .map(customer -> {
              CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
              customerDTO.setCustomerUrl("/api/v1/customer/" + customer.getId());
              return customerDTO;
            })
            .orElseThrow(RuntimeException::new); // todo implement better exception handling
  }

  @Override
  public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
    Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

    return saveOrUpdateCustomer(customer);
  }

  @Override
  public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id) {
    Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
    customer.setId(id);

    return saveOrUpdateCustomer(customer);
  }

  @Override
  public CustomerDTO patchCustomer(CustomerDTO customerDTO, Long id) {
    return customerRepository.findById(id)
            .map(customer -> {
              if (customerDTO.getFirstname() != null) {
                customer.setFirstname(customerDTO.getFirstname());
              }
              if (customerDTO.getLastname() != null) {
                customer.setLastname(customerDTO.getLastname());
              }

              return saveOrUpdateCustomer(customer);
            })
            .orElseThrow(RuntimeException::new);
  }

  private CustomerDTO saveOrUpdateCustomer(Customer customer) {
    Customer savedCustomer = customerRepository.save(customer);
    CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);

    // set customer url before return
    savedCustomerDTO.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());
    return savedCustomerDTO;
  }


}
