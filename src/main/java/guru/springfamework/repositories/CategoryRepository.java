package guru.springfamework.repositories;

import guru.springfamework.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 9/24/17.
 */
// Category repository extends JPA repository (h2 in memory database)
public interface CategoryRepository extends JpaRepository<Category, Long> {
  // JPA Query method will help us do the implementation
  Category findByName(String name);
}
