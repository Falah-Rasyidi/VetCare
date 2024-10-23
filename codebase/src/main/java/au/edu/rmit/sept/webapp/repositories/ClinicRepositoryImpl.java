package au.edu.rmit.sept.webapp.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

//import org.springframework.jdbc.datasource.init.UncategorizedScriptException;
import au.edu.rmit.sept.webapp.models.Pet;
import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.Clinic;

@Repository
public class ClinicRepositoryImpl implements ClinicRepository {
    private DataSource source;

    public ClinicRepositoryImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public List<Clinic> getAll() {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM clinic;");
            List<Clinic> clinics = new ArrayList<>();
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Clinic clinic = new Clinic(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5));
                clinics.add(clinic);
            }
            connection.close();

            return clinics;
        } catch (SQLException e) {
            /**
             * idk why, but intellij can't resolve the jdbc package ^^^. the code still
             * compiles but it's annoying
             * to see the red squiggly lines so i changed the exception to something else.
             */
            throw new RuntimeException(e);
        }
    }

    @Override
    public Clinic getByAvailability(Integer availabilityId){
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clinic LEFT JOIN availability WHERE clinic.clinicId=availability.clinicId AND availability.availabilityId="+availabilityId+";");
            ResultSet rs = statement.executeQuery();
            Clinic c = null;
            while (rs.next()) {
                c = new Clinic(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));}

            System.out.println(c);
            connection.close();
            return c;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
