package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.CartEntry;
import au.edu.rmit.sept.webapp.models.CartEntryClass;
import au.edu.rmit.sept.webapp.models.Transaction;
import au.edu.rmit.sept.webapp.models.TransactionEntry;
import au.edu.rmit.sept.webapp.repositories.CartEntryRepository;
import au.edu.rmit.sept.webapp.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository repository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }


    @Override
    public Collection<Transaction> getTransactions() {
        return repository.getTransactions();
    }

    @Override
    public Collection<TransactionEntry> getTransactionEntries(Integer transactionId) {
        return repository.getTransactionEntries(transactionId);
    }

    @Override
    public Boolean convertCartEntryToTransaction(Collection<CartEntry> cartEntries, Integer userId) {
        return repository.convertCartEntryToTransaction(cartEntries,userId);
    }
}
