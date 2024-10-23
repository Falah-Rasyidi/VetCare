package au.edu.rmit.sept.webapp.services;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.UserClass;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    public Collection<User> getUsers();
    public boolean addUser(UserClass newUser);
    public Optional<User> findUser(String email);
    public boolean editUser(int id, UserClass newUser);
    public boolean deleteUser(int id);
    public boolean verifyUser(String email, String password);
}
