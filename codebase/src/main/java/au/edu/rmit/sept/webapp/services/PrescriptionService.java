package au.edu.rmit.sept.webapp.services;

import java.util.Collection;
import au.edu.rmit.sept.webapp.models.Prescription;
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

public interface PrescriptionService {
    Collection<Prescription> getAllPrescriptions();
    Prescription getPrescriptionById(Integer prescriptionId);
    Collection<Pet> findByOwnerId(String ownerId);
}
