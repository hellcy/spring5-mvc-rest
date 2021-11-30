package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

  private CategoryRepository categoryRepository;
  private CustomerRepository customerRepository;

  public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
    this.categoryRepository = categoryRepository;
    this.customerRepository = customerRepository;
  }

  // CommandLineRunner run method is Spring Boot specific
  // it will run on startup, any arguments passed in to this method will be picked up
  @Override
  public void run(String... args) throws Exception {
    loadCategories();
    loadCustomers();
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
}
