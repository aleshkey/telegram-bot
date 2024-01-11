package parser.KufarParser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import parser.KufarParser.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> findByTitle(String title);
    boolean existsByLink(String link);
}
