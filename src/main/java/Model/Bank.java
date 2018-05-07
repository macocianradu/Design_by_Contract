package Model;

import Processes.BankProcesses;

import java.util.ArrayList;

public class Bank implements BankProcesses {
    private ArrayList<Person> personList;
    private ArrayList<Account> accounts;
    private ArrayList<SavingsAccount> savingsAccounts;
    private ArrayList<SpendingAccount> spendingAccounts;

    public void addPerson(Person p) {
        this.personList.add(p);
    }

    public void removePerson(Person p) {
        this.personList.remove(p);
    }

    public void addHolder(Person p, Account a) {
        if(this.personList.contains(p)) {
            if (this.accounts.contains(a)) {
                a.addHolder(p);
            } else {
                System.out.println("ERROR: account doesn't exist");
            }
        } else {
            System.out.println("ERROR: person doesn't exist");
        }
    }

    public void removeHolder(Person p, Account a) {
        a.removeHolder(p);
    }

    public Account getAccountData(int id) {
        return this.accounts.get(id);
    }

    public void setAccountData(Account a) {
        this.accounts.remove(a.getId());
        this.accounts.add(a);
    }

    public void generateReport() {

    }

    public void addAccount(Account a){
        if(a instanceof SavingsAccount){
            this.savingsAccounts.add((SavingsAccount)a);
            this.accounts.add(a);
        }
        else if(a instanceof SpendingAccount){
            this.spendingAccounts.add((SpendingAccount)a);
            this.accounts.add(a);
        }
        else {
            System.out.println("ERROR: Account has no type");
        }
    }

    public void removeAccount(Account a){
        if(a instanceof SavingsAccount){
            this.savingsAccounts.remove((SavingsAccount)a);
            this.accounts.remove(a);
        }
        else if(a instanceof SpendingAccount){
            this.spendingAccounts.remove((SpendingAccount)a);
            this.accounts.remove(a);
        }
        else {
            System.out.println("ERROR: Account has no type");
        }
    }

    public ArrayList<Account> showAccounts(Person p){
        ArrayList<Account> result = new ArrayList<Account>();
        for(Account a: accounts){
            if(a.getMainHolder() == p){
                result.add(a);
            }
        }
        return result;
    }

    public ArrayList<SavingsAccount> showSavingsAccounts(Person p){
        ArrayList<SavingsAccount> result = new ArrayList<SavingsAccount>();
        for(SavingsAccount a: savingsAccounts){
            if(a.getMainHolder() == p){
                result.add(a);
            }
        }
        return result;
    }

    public ArrayList<SpendingAccount> showSpendingAccounts(Person p){
        ArrayList<SpendingAccount> result = new ArrayList<SpendingAccount>();
        for(SpendingAccount a: spendingAccounts){
            if(a.getMainHolder() == p){
                result.add(a);
            }
        }
        return result;
    }
    public ArrayList<Account> showAccounts(){
        return this.accounts;
    }

    public ArrayList<SavingsAccount> showSavingsAccounts(){
        return this.savingsAccounts;
    }

    public ArrayList<SpendingAccount> showSpendingAccounts(){
        return this.spendingAccounts;
    }
}
