package Processes;

import Model.Account;
import Model.Person;

public interface BankProcesses {
    void addPerson(Person p);
    void removePerson(Person p);
    void addHolder(Person p, Account a);
    void removeHolder(Person p, Account a);
    Account getAccountData(int id);
    void setAccountData(Account a);
    void generateReport();
}
