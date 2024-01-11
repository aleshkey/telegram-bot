package parser.KufarParser.repository;

import org.springframework.data.repository.CrudRepository;
import parser.KufarParser.model.State;

public interface StateRepository extends CrudRepository<State, Long> {
}
