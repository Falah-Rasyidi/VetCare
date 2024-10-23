package au.edu.rmit.sept.webapp.services;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.UserClass;
import au.edu.rmit.sept.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public boolean addUser(UserClass userClass) {
        boolean returnValue = repository.insertNewUser(userClass);
        //Return value held in-case there is handling for failed insertion
        return returnValue;
    }

    @Override
    public Optional<User> findUser(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean editUser(int id, UserClass newUser) {
        return repository.editUser(id, newUser);
    }

    @Override
    public boolean deleteUser(int id) {
        return repository.deleteUser(id);
    }

    @Override
    public boolean verifyUser(String email, String password) {
        return repository.verifyUser(email, password);
    }
}
