package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.CartEntry;
import au.edu.rmit.sept.webapp.models.CartEntryClass;
import au.edu.rmit.sept.webapp.models.Prescription;
import au.edu.rmit.sept.webapp.repositories.BookingRepository;
import au.edu.rmit.sept.webapp.repositories.CartEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CartEntryServiceImpl implements CartEntryService{

    private CartEntryRepository repository;

    @Autowired
    public CartEntryServiceImpl(CartEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<CartEntry> getCartEntries() {
        return repository.getAll();
    }

    @Override
    public Collection<CartEntry> getCartEntriesByUser(Integer userId){
        return repository.getByUserId(userId);
    }

    @Override
    public Boolean addCartEntry(CartEntryClass cartEntry) {
        return repository.add(cartEntry);
    }

    @Override
    public Collection<CartEntryClass> convertEntriesToPrescriptions(Integer userId) {
        return repository.convert(userId);
    }
}
