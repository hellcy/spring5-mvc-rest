package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

  private CategoryRepository categoryRepository;
  private CustomerRepository customerRepository;
  private VendorRepository vendorRepository;

  public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
    this.categoryRepository = categoryRepository;
    this.customerRepository = customerRepository;
    this.vendorRepository = vendorRepository;
  }

  // CommandLineRunner run method is Spring Boot specific
  // it will run on startup, any arguments passed in to this method will be picked up
  @Override
  public void run(String... args) throws Exception {
    loadCategories();
    loadCustomers();
    loadVendors();
  }

  private void loadCategories() {
    Category fruits = new Category();
    fruits.setName("Fruits");

    Category dried = new Category();
    dried.setName("Dried");

    Category fresh = new Category();
    fresh.setName("Fresh");

    Category exotic = new Category();
    exotic.setName("Exotic");

    Category nuts = new Category();
    nuts.setName("Nuts");

    categoryRepository.save(fruits);
    categoryRepository.save(dried);
    categoryRepository.save(fresh);
    categoryRepository.save(exotic);
    categoryRepository.save(nuts);

    System.out.println("Data loaded: " + categoryRepository.count());
  }

  private void loadCustomers() {
    // given
    Customer customer1 = new Customer();
    customer1.setId(1L);
    customer1.setFirstname("Yuan");
    customer1.setLastname("Cheng");
    customerRepository.save(customer1);

    Customer customer2 = new Customer();
    customer2.setId(2L);
    customer2.setFirstname("Nan");
    customer2.setLastname("Yang");
    customerRepository.save(customer2);

    System.out.println("Customers loaded: " + customerRepository.count());
  }

  private void loadVendors() {
    Vendor vendor1 = new Vendor();
    vendor1.setName("Yuan");
    vendorRepository.save(vendor1);

    Vendor vendor2 = new Vendor();
    vendor2.setName("Nan");
    vendorRepository.save(vendor2);

    System.out.println("Vendors loaded: " + vendorRepository.count());
  }
}
