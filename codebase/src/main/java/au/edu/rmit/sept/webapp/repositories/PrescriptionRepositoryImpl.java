package au.edu.rmit.sept.webapp.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import au.edu.rmit.sept.webapp.models.Prescription;
import au.edu.rmit.sept.webapp.models.Pet;
import org.springframework.stereotype.Repository;

@Repository
public class PrescriptionRepositoryImpl implements PrescriptionRepository {
    private DataSource source;

    public PrescriptionRepositoryImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public List<Prescription> getAll() {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM prescription;");
            List<Prescription> prescriptions = new ArrayList<>();
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Prescription prescription = new Prescription(
                        results.getInt("PRESCRIPTIONID"),
                        results.getString("MEDICINENAME"),
                        results.getString("DESCRIPTION"),
                        results.getString("DOSAGE"),
                        results.getString("INSTRUCTIONS"),
                        results.getDouble("PRICE")

                );
                prescriptions.add(prescription);
            }
            connection.close();

            return prescriptions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Prescription getById(Integer prescriptionId) {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM prescription WHERE prescriptionId = ?;");
            statement.setInt(1, prescriptionId);
            ResultSet rs = statement.executeQuery();
            Prescription prescription = null;
            if (rs.next()) {
                prescription = new Prescription(
                        rs.getInt("PRESCRIPTIONID"),
                        rs.getString("MEDICINENAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getString("DOSAGE"),
                        rs.getString("INSTRUCTIONS"),
                        rs.getDouble("PRICE")
                );
            }
            connection.close();
            return prescription;
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
}
