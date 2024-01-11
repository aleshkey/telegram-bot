package parser.KufarParser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import parser.KufarParser.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
