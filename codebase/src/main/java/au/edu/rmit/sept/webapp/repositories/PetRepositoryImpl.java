package au.edu.rmit.sept.webapp.repositories;

import at.favre.lib.crypto.bcrypt.BCrypt;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.PetClass;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.UserClass;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PetRepositoryImpl implements PetRepository {
    private final DataSource source;

    public PetRepositoryImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public List<Pet> findAll() {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM petData;");
            List<Pet> petArrayList = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            // Loop to convert all query results to array list entries
            while (rs.next()) {
                Pet p = new Pet(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7));
                petArrayList.add(p);
            }
            connection.close(); // Close connection to avoid errors
            return petArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pet> findByOwnerId(String ownerID) {
        try {
            Connection connection = this.source.getConnection();
            System.out.println(String.format("SELECT * FROM petData WHERE userId = %s;", ownerID));
            PreparedStatement statement = connection
                    .prepareStatement(String.format("SELECT * FROM petData WHERE userId = %s;", ownerID));
            List<Pet> petArrayList = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // Otherwise, return user
                Pet p = new Pet(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7));
                petArrayList.add(p);
                // return Optional.of(user);
            }
            connection.close(); // Close connection to avoid errors
            return petArrayList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Pet findById(Integer id) {
        try {
            Connection connection = this.source.getConnection();
            // System.out.println(String.format("SELECT * FROM petData WHERE petId = %s;",
            // id));
            PreparedStatement statement = connection
                    .prepareStatement(String.format("SELECT * FROM petData  WHERE petId = %s;", id));
            List<Pet> petArrayList = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            rs.next();
            Pet p = new Pet(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7));
            connection.close(); // Close connection to avoid errors
            return p;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean insertNewPet(PetClass pet) {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            connection.setAutoCommit(false);
            System.out.println(pet.toInsertSQL());
            PreparedStatement stm = connection.prepareStatement(pet.toInsertSQL());
            stm.executeUpdate(); // executeUpdate() used for UPDATE / INSERT calls
            connection.commit(); // Saves changes made in the connection
            connection.close(); // Close connection to avoid errors
            return true; // Successful Insertion
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false; // Failed Insertion
        }
    }

    @Override
    public Boolean deletePet(Integer petId) {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            connection.setAutoCommit(false);
            PreparedStatement stm = connection
                    .prepareStatement("DELETE FROM petData WHERE petId = " + petId.toString() + ";");
            stm.executeUpdate(); // executeUpdate() used for UPDATE / INSERT calls
            connection.commit(); // Saves changes made in the connection
            connection.close(); // Close connection to avoid errors
            return true; // Successful Delete
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false; // Failed Delete
        }
    }

    @Override
    public Boolean editPet(int id, PetClass pet) {
        try {
            // Update pet details given their ID
            Connection connection = this.source.getConnection();
            connection.setAutoCommit(false);
//            System.out.printf(
//                    "UPDATE PETDATA SET PETNAME = '%s', SPECIES = '%s', BREED = '%s', DATEOFBIRTH = '%s' WHERE PETID = %d;%n",
//                    pet.getName(), pet.getSpecies(), pet.getBreed(), pet.getDOB(), id);
            PreparedStatement statement = connection.prepareStatement(String.format(
                    "UPDATE petdata SET petName = '%s', species = '%s', breed = '%s', dateOfBirth = '%s' WHERE petId = %d;",
                    pet.getName(), pet.getSpecies(), pet.getBreed(), pet.getDOB(), id));
            statement.executeUpdate();
            connection.commit();
            connection.close();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
