package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String>
{
    User findById(long Id);

    @Query(value = "select * from apprh.user where email = :email and password = :password",nativeQuery = true)
    public User login(String email,String password);
}
