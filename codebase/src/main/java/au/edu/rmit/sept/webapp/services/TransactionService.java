package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.CartEntry;
import au.edu.rmit.sept.webapp.models.Transaction;
import au.edu.rmit.sept.webapp.models.TransactionEntry;

import java.util.Collection;
import java.util.List;

public interface TransactionService {
    public Collection<Transaction> getTransactions();
    public Collection<TransactionEntry> getTransactionEntries(Integer transactionId);
    public Boolean convertCartEntryToTransaction(Collection<CartEntry> cartEntries, Integer userId);
}
