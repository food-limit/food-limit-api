package fr.foodlimit.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public User lookup(String username) {
    return userRepository.findById(username).get();
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public boolean usernameExists(String username) {
    return userRepository.existsById(username);
  }
}
