package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Booking;
import au.edu.rmit.sept.webapp.models.CartEntry;
import au.edu.rmit.sept.webapp.models.CartEntryClass;
import au.edu.rmit.sept.webapp.models.Prescription;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Repository
public class CartEntryRepositoryImpl implements CartEntryRepository{

    private final DataSource source;

    public CartEntryRepositoryImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public List<CartEntry> getAll() {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM cartEntry;");
            List<CartEntry> cartEntryArrayList = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            // Loop to convert all query results to array list entries
            while (rs.next()) {
                CartEntry u = new CartEntry(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3));
                cartEntryArrayList.add(u);
            }
            connection.close(); // Close connection to avoid errors
            return cartEntryArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<CartEntry> getByUserId(Integer userId){
        try {
            Connection connection = this.source.getConnection(); // DB Init
            PreparedStatement stm = connection.prepareStatement(String.format("SELECT * FROM cartEntry where userId = %s;",userId));
            List<CartEntry> cartEntryArrayList = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            // Loop to convert all query results to array list entries
            while (rs.next()) {
                CartEntry u = new CartEntry(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3));
                cartEntryArrayList.add(u);
            }
            connection.close(); // Close connection to avoid errors
            return cartEntryArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean add(CartEntryClass cartEntry) {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            connection.setAutoCommit(false);
            System.out.println(cartEntry.toInsertSQL());
            PreparedStatement stm = connection.prepareStatement(cartEntry.toInsertSQL());
            stm.executeUpdate(); // executeUpdate() used for UPDATE / INSERT calls
            connection.commit(); // Saves changes made in the connection
            connection.close(); // Close connection to avoid errors
            return true; // Successful Insertion
        } catch (SQLException e) {
            return false; // Failed Insertion
        }
    }

    @Override
    public Collection<CartEntryClass> convert(Integer userId) {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement(String.format("SELECT * FROM PRESCRIPTION INNER JOIN CARTENTRY ON CARTENTRY.PRESCRIPTIONID=PRESCRIPTION.PRESCRIPTIONID WHERE USERID = %d;", userId));
            ResultSet rs = statement.executeQuery();
            Collection<CartEntryClass> prescriptionList = new ArrayList<>();

            if (!rs.isBeforeFirst()) {
                return new ArrayList<>();
            }
            else {
                while (rs.next()) {
                    CartEntryClass c = new CartEntryClass(
                            rs.getInt(7),
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getDouble(6),
                            rs.getInt(9)
                    );

                    prescriptionList.add(c);
                }

                return prescriptionList;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
