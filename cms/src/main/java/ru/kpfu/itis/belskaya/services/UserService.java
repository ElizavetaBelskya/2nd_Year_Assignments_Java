
package ru.kpfu.itis.belskaya.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.repositories.UserRepository;


/**
 * @author Elizaveta Belskaya
 */

@Component("UserService")
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public void registerUser(User user) throws DuplicateKeyException {
    if (userRepo.findByEmail(user.getUsername()) != null){
      throw new DuplicateKeyException("Duplicate key - username field.");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(User.Role.USER);
    userRepo.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    return user;
  }

  



}
