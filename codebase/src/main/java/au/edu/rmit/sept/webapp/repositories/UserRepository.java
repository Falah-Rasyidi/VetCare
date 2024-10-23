package au.edu.rmit.sept.webapp.repositories;

import java.util.List;
import java.util.Optional;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.UserClass;

public interface UserRepository {
    public List<User> findAll();
//    public User findById(long id); // May be integrated later
    public Optional<User> findByEmail(String email);
    public Boolean insertNewUser(UserClass user);
    public Boolean editUser(int id, UserClass newUser);
    public Boolean deleteUser(int id);
    public Boolean verifyUser(String email, String password);
}
