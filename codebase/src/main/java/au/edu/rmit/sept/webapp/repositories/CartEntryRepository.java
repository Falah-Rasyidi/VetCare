package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.CartEntry;
import au.edu.rmit.sept.webapp.models.CartEntryClass;
import au.edu.rmit.sept.webapp.models.Prescription;

import java.util.Collection;
import java.util.List;

public interface CartEntryRepository {
    public List<CartEntry> getAll();
    public List<CartEntry> getByUserId(Integer userId);
    public Boolean add(CartEntryClass cartEntry);
    public Collection<CartEntryClass> convert(Integer userId);
}
