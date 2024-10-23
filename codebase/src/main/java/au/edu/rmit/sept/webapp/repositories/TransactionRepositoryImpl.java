package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.CartEntry;
import au.edu.rmit.sept.webapp.models.CartEntryClass;
import au.edu.rmit.sept.webapp.models.Transaction;
import au.edu.rmit.sept.webapp.models.TransactionEntry;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final DataSource source;

    public TransactionRepositoryImpl(DataSource source) {
        this.source = source;
    }


    @Override
    public List<Transaction> getTransactions() {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM transactions;");
            List<Transaction> transactionArrayList = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            // Loop to convert all query results to array list entries
            while (rs.next()) {
                Transaction u = new Transaction(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
                transactionArrayList.add(u);
            }
            connection.close(); // Close connection to avoid errors
            return transactionArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TransactionEntry> getTransactionEntries(Integer transactionId) {
        try {
            Connection connection = this.source.getConnection(); // DB Init
            PreparedStatement stm = connection.prepareStatement(String.format("SELECT * FROM transactionEntry WHERE transactionId = %s;", transactionId));
            List<TransactionEntry> transactionArrayList = new ArrayList<>();
            ResultSet rs = stm.executeQuery();
            // Loop to convert all query results to array list entries
            while (rs.next()) {
                TransactionEntry u = new TransactionEntry(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getFloat(4));
                transactionArrayList.add(u);
            }
            connection.close(); // Close connection to avoid errors
            return transactionArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean convertCartEntryToTransaction(Collection<CartEntry> cartEntries,Integer userId) {
        try{
            Connection connection = this.source.getConnection(); // DB Init
            connection.setAutoCommit(false);

            // Create new transaction
            PreparedStatement stm = connection
                    .prepareStatement("INSERT INTO transactions" +
                            "(userId,status, dateSubmitted, timeSubmitted)" +
                            "values(" +
                            userId+
                            ", 'Processing' , CURRENT_DATE(), CURRENT_TIME());");
            stm.executeUpdate(); // executeUpdate() used for UPDATE / INSERT calls
            connection.commit(); // Saves changes made in the connection
            stm = connection.prepareStatement(String.format("SELECT * FROM transactions WHERE userId = %s ORDER BY transactionId DESC;",userId));
            ResultSet rs = stm.executeQuery();
            Integer transactionId = 0;
            if(rs.next()) {
                transactionId = rs.getInt(1);
            }

            // Insert transaction entries for each cart entry
            for (CartEntry entry : cartEntries) {
                stm = connection.prepareStatement(String.format("SELECT * FROM prescription where prescriptionId = %s;",entry.prescriptionId()));
                rs = stm.executeQuery();
                Float price = 0.00F;
                if(rs.next()) {
                    price = rs.getFloat(6);
                }
                stm = connection.prepareStatement(String.format("INSERT INTO transactionEntry VALUES (%s,%s,%s,%f);", transactionId, entry.prescriptionId(), entry.quantity(), price));
                stm.executeUpdate();
                connection.commit();
            }

            // Delete old transaction entries
            stm = connection.prepareStatement(String.format("DELETE FROM cartEntry where userId = %s;", userId));
            stm.executeUpdate();
            connection.commit();

            connection.close(); // Close connection to avoid errors
            return true; // Successful Delete
        } catch (SQLException e) {
            return false;
//            throw new RuntimeException(e);

        }
    }
}
