package parser.KufarParser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import parser.KufarParser.model.Link;

import java.util.Optional;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {
    Optional<Link> findByUrl(String url);
}
