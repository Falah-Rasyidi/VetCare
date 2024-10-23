package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Availability;

import au.edu.rmit.sept.webapp.models.AvailabilityClass;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.User;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AvailabilityRepositoryImpl implements AvailabilityRepository {
    private final DataSource source;

    public AvailabilityRepositoryImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public List<Availability> findAll() {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM availability;");
            List<Availability> availabilityArrayList = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            // Loop to convert all query results to array list entries
            while (rs.next()) {
                Availability u = new Availability(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5));
                availabilityArrayList.add(u);
            }
            connection.close(); // Close connection to avoid errors
            return availabilityArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean bookAvailability(Integer availabilityId) {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement(
                    "UPDATE availability " +
                            "SET bookedStatus = true " +
                            "WHERE availabilityId=" + availabilityId + ";");
            // List<Availability> availabilityArrayList = new ArrayList<>();
            stm.executeUpdate(); // executeUpdate() used for UPDATE / INSERT calls
            connection.commit(); // Saves changes made in the connection
            connection.close(); // Close connection to avoid errors
            return true; // Successful Insertion
        } catch (SQLException e) {
            return false; // Failed Insertion
        }
    }

    @Override
    public Boolean putAvailability(Integer availabilityId) {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement(
                    "UPDATE availability " +
                            "SET bookedStatus = false " +
                            "WHERE availabilityId=" + availabilityId + ";");
            stm.executeUpdate(); // executeUpdate() used for UPDATE / INSERT calls
            connection.commit(); // Saves changes made in the connection
            connection.close(); // Close connection to avoid errors
            return true; // Successful Insertion
        } catch (SQLException e) {
            return false; // Failed Insertion
        }
    }

    @Override
    public List<Availability> findAvailabilityByClinicId(Integer clinicId) {
        try {
            Connection connection = this.source.getConnection();

            // Prepare the SQL query to fetch availability based on clinicId
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT " +
                            "    a.availabilityId, " +
                            "    a.clinicId, " +
                            "    a.dateTimeStart, " +
                            "    a.dateTimeEnd, " +
                            "    a.bookedStatus " +
                            "FROM availability a " +
                            "WHERE a.clinicId = ? AND a.bookedStatus = FALSE");

            // Set the clinicId in the query
            statement.setInt(1, clinicId);

            // Execute the query
            ResultSet rs = statement.executeQuery();

            // Create a list to store the availability entries
            List<Availability> availabilityList = new ArrayList<>();

            // Loop through the result set and add each availability to the list
            while (rs.next()) {
                Availability availability = new Availability(
                        rs.getInt("availabilityId"),
                        rs.getInt("clinicId"),
                        rs.getString("dateTimeStart"),
                        rs.getString("dateTimeEnd"),
                        rs.getBoolean("bookedStatus")

                );
                availabilityList.add(availability);
            }

            connection.close(); // Close the connection
            return availabilityList; // Return the list of availabilities

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Availability> findValidAvailability() {

        try {
            Connection connection = this.source.getConnection(); // DB Init
            PreparedStatement stm = connection
                    .prepareStatement("SELECT * FROM availability where bookedStatus = false;");
            List<Availability> availabilityArrayList = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            // Loop to convert all query results to array list entries
            while (rs.next()) {
                Availability u = new Availability(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5));
                availabilityArrayList.add(u);
            }
            connection.close(); // Close connection to avoid errors
            return availabilityArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Boolean addAvailability(AvailabilityClass availability) {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement(availability.toInsertSQL());
            stm.executeUpdate(); // executeUpdate() used for UPDATE / INSERT calls
            connection.commit(); // Saves changes made in the connection
            connection.close(); // Close connection to avoid errors
            return true; // Successful Insertion
        } catch (SQLException e) {
            return false; // Failed Insertion
        }
    }

    @Override
    public Boolean removeAvailability(Integer availabilityId) {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement(
                    "DELETE FROM availability WHERE availabilityId = " + availabilityId.toString() + ";");
            stm.executeUpdate(); // executeUpdate() used for UPDATE / INSERT calls
            connection.commit(); // Saves changes made in the connection
            connection.close(); // Close connection to avoid errors
            return true; // Successful Delete
        } catch (SQLException e) {
            return false; // Failed Delete
        }
    }

    @Override
    public Availability findAvailabilityById(Integer availabilityId) {
        try {
            Connection connection = this.source.getConnection();
            // System.out.println(String.format("SELECT * FROM petData WHERE petId = %s;",
            // id));
            PreparedStatement statement = connection
                    .prepareStatement(
                            String.format("SELECT * FROM availability  WHERE availabilityId = %s;", availabilityId));
            ResultSet rs = statement.executeQuery();
            rs.next();
            Availability u = new Availability(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getBoolean(5));
            connection.close(); // Close connection to avoid errors
            return u;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}