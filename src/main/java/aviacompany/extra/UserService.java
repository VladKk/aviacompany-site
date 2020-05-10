package aviacompany.extra;

import aviacompany.entity.Role;
import aviacompany.entity.User;
import aviacompany.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// Бізнес-логіка для користувача
@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //    Отримати користувача за логіном
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);

        if (user == null)
            throw new UsernameNotFoundException("User not found");

        return user;
    }

    //    Отримати користувача за id
    public User findUserById(long id) {
        Optional<User> userFromDB = userRepository.findById(id);
        return userFromDB.orElse(new User());
    }

    //    Отримати поточного користувача
    public String getUser() {
        String name = "";

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        name = user.getUsername();

        return name;
    }

    //    Отримати список всіх користувачів
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    //    Зберегти користувача
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByLogin(user.getUsername());

        if (userFromDB == null)
            return false;

        user.setRoles(Collections.singleton(new Role(4L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return true;
    }

    //    Видалити користувача
    public boolean deleteUser(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }

        return false;
    }

    //    Отримати певний список користувачів, починаючи з заданого id
    public List<User> getUserList(long fromId) {
        return entityManager.createQuery("select usr from User usr where usr.id > :paramId", User.class)
                .setParameter("paramId", fromId).getResultList();
    }
}
