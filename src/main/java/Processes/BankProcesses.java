package Processes;

import Model.Account;
import Model.Person;

import java.util.ArrayList;

public interface BankProcesses {
    /**
     * @pre p != null
     */
    void addPerson(Person p);
    /**
     * @pre p != null && bank.accounts.keySet().contains(p);
     */
    void removePerson(Person p);
    /**
     * @pre p != null && a != null
     */
    void addHolder(Person p, Account a);
    /**
     * @pre p != null && a != null
     */
    void removeHolder(Person p, Account a);
    /**
     * @pre a != null && accounts.get(a.getMainHolder()).contains(a)
     * @pos return != null
     */
    Account getAccountData(Account a);
    /**
     * @pre p != null && a != null
     */
    void setAccountData(Person p, Account a);
    /**
     * @pre a != null
     */
    void addAccount(Account a);
    /**
     * @pre p != null && a != null
     */
    void removeAccount(Person p, Account a);
    /**
     * @pre p != null
     */
    public ArrayList<Account> showAccounts(Person p);
    /**
     * @pos return != null
     */
    public ArrayList<Account> showAccounts();
}
