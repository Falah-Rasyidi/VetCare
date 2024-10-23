package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.CartEntry;
import au.edu.rmit.sept.webapp.models.CartEntryClass;
import au.edu.rmit.sept.webapp.models.Prescription;

import java.util.Collection;

public interface CartEntryService {
    public Collection<CartEntry> getCartEntries();
    public Collection<CartEntry> getCartEntriesByUser(Integer userId);
    public Boolean addCartEntry(CartEntryClass cartEntry);
    public Collection<CartEntryClass> convertEntriesToPrescriptions(Integer userId);
}
