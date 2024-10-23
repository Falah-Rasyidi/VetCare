package au.edu.rmit.sept.webapp.repositories;

import at.favre.lib.crypto.bcrypt.BCrypt;
import au.edu.rmit.sept.webapp.models.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookingRepositoryImpl implements BookingRepository {
    private final DataSource source;

    public BookingRepositoryImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public List<Booking> findAll() {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM booking;");
            List<Booking> userArrayList = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            // Loop to convert all query results to array list entries
            while (rs.next()) {
                Booking u = new Booking(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(6));
                userArrayList.add(u);
            }
            connection.close(); // Close connection to avoid errors
            return userArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean updateBooking(BookingClass booking) {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            connection.setAutoCommit(false);
            System.out.println(booking.toInsertSQL());
            PreparedStatement stm = connection.prepareStatement(booking.toInsertSQL());
            stm.executeUpdate(); // executeUpdate() used for UPDATE / INSERT calls
            connection.commit(); // Saves changes made in the connection
            connection.close(); // Close connection to avoid errors
            return true; // Successful Insertion
        } catch (SQLException e) {
            return false; // Failed Insertion
        }
    }

    @Override
    public Boolean removeBooking(int availabilityId) {
        try {
            Connection connection = this.source.getConnection();
            connection.setAutoCommit(false);
            System.out.println(String.format("DELETE FROM booking WHERE availabilityId = %s;", availabilityId));
            PreparedStatement statement = connection
                    .prepareStatement(String.format("DELETE FROM booking WHERE availabilityId = %s;", availabilityId));
            statement.execute();
            connection.commit(); // Saves changes made in the connection
            connection.close(); // Close connection to avoid errors
            return true; // Successful Insertion
        } catch (SQLException e) {
            return false; // Failed Insertion
        }
    }

    // Kinda scuffed:
    // SQL Join query for detailed booking information (across Booking, PetData, and
    // Availability tables)
    @Override
    public List<BookingClass> findDetailedBookingsByCustomerId(Integer customerId) {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT " +
                            "    b.bookingId, " +
                            "    c.clinicName, " +
                            "    c.clinicId, " +
                            "    b.availabilityId, " +
                            "    a.dateTimeStart, " +
                            "    a.dateTimeEnd, " +
                            "    p.petname AS petName, " +
                            "    p.species AS petSpecies " +
                            "FROM booking b " +
                            "JOIN availability a ON b.availabilityId = a.availabilityId " +
                            "JOIN clinic c ON b.clinicId = c.clinicId " +
                            "JOIN petData p ON b.petId = p.petId " +
                            "WHERE b.userid = ?");
            statement.setInt(1, customerId);
            List<BookingClass> bookingArrayList = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                BookingClass b = new BookingClass(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8));
                bookingArrayList.add(b);
            }
            connection.close(); // Close connection to avoid errors
            return bookingArrayList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean rescheduleBooking(int bookingId, int selectedAvailabilityId) {
        try {
            Connection connection = this.source.getConnection();
            connection.setAutoCommit(false);

            String sql = "UPDATE booking SET availabilityId = ? WHERE bookingId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, selectedAvailabilityId);
            statement.setInt(2, bookingId);

            int rowsAffected = statement.executeUpdate();

            connection.commit();
            connection.close();

            // if changes were made, return true (success)
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Handle the exception and return false if the update fails
            e.printStackTrace(); // Optionally log the exception
            return false;
        }
    }
}
