package ru.kpfu.itis.belskaya.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.belskaya.models.User;

/**
 * @author Elizaveta Belskaya
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
}
