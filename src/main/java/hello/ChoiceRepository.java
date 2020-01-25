package hello;

import hello.entity.Choice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "choices", path = "choices")
public interface ChoiceRepository extends CrudRepository<Choice, Long> {
}