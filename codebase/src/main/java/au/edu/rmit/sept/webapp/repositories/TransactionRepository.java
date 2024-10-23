package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.CartEntry;
import au.edu.rmit.sept.webapp.models.Transaction;
import au.edu.rmit.sept.webapp.models.TransactionEntry;

import java.util.Collection;
import java.util.List;

public interface TransactionRepository {
    public List<Transaction> getTransactions();
    public List<TransactionEntry> getTransactionEntries(Integer transactionId);
    public Boolean convertCartEntryToTransaction(Collection<CartEntry> cartEntries, Integer userId);

}
