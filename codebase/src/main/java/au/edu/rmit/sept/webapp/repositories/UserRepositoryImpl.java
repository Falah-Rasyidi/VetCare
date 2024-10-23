package au.edu.rmit.sept.webapp.repositories;

import java.sql.SQLException;
import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.UserClass;
import org.flywaydb.core.internal.jdbc.Result;
import org.flywaydb.core.internal.jdbc.Results;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.sql.SQLException;
import java.util.Optional;

/**
 * <ul>
 * <li><u>findAll()</u> -> Gets all users</li>
 * <li><u>insertNewUser()</u> -> Inserts new UserClass into DB</li>
 * </ul>
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final DataSource source;

    public UserRepositoryImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public List<User> findAll() {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM userAccount;");
            List<User> userArrayList = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            // Loop to convert all query results to array list entries
            while (rs.next()) {
                User u = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7));
                userArrayList.add(u);
            }
            connection.close(); // Close connection to avoid errors
            return userArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean insertNewUser(UserClass user) {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement(user.toInsertSQL());
            stm.executeUpdate(); // executeUpdate() used for UPDATE / INSERT calls
            connection.commit(); // Saves changes made in the connection
            connection.close(); // Close connection to avoid errors
            return true; // Successful Insertion
        } catch (SQLException e) {
            return false; // Failed Insertion
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement(String.format("SELECT * FROM userAccount WHERE emailAddress = '%s';", email));
            ResultSet rs = statement.executeQuery();

            // Return null if no user found (email hasn't been used before)
            if (!rs.isBeforeFirst()) {
                connection.close();
                return Optional.empty();
            } else {
                // Otherwise, return user
                rs.next();
                User user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7));

                connection.close();
                return Optional.of(user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean editUser(int id, UserClass newUser) {
        try {
            // Update the user's details via their user ID
            Connection connection = this.source.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(String.format(
                    "UPDATE userAccount SET firstName = '%s', lastName = '%s', phoneNumber = '%s', emailAddress = '%s' WHERE userId = %d;",
                    newUser.getFirstName(), newUser.getLastName(), newUser.getPhoneNumber(), newUser.getEmail(), id));
            statement.executeUpdate();
            connection.commit();
            connection.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteUser(int id) {
        try {
            // Delete user from db via their user ID
            // Should also delete all reliant items such as pets, appointments, cart items,
            // etc.
            Connection connection = this.source.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection
                    .prepareStatement(String.format("DELETE FROM userAccount WHERE userId = %d;", id));
            statement.executeUpdate();
            connection.commit();
            connection.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean verifyUser(String email, String password) {
        // Assumes that user associated with email already exists
        User user = this.findByEmail(email).get();

        // Verify inputted password against password hash stored in database
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.password());

        // Return outcome of verification
        return result.verified;
    }
}
