package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String>
{
    User findById(long Id);
}
