package Model;

import Processes.BankProcesses;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Bank implements BankProcesses, Serializable {
    private HashMap<Person, ArrayList<Account>> accounts;

    public Bank(HashMap<Person, ArrayList<Account>> accounts){
        this.accounts = accounts;
    }

    public void addPerson(Person p) {
        assert p != null;
        this.accounts.put(p, new ArrayList<Account>());
        saveBank();
    }

    public void removePerson(Person p) {
        assert p != null;
        ArrayList<Person> toRemove = new ArrayList<Person>();
        for(Person p1: this.accounts.keySet()){
            if(p1.getId() == p.getId()){
                toRemove.add(p1);
            }
        }
        for(Person p1: toRemove){
            this.accounts.remove(p1);
        }
        saveBank();
    }

    public void addHolder(Person p, Account a) {
        assert p != null && a != null;
        if(this.accounts.keySet().contains(p)) {
            if (this.accounts.get(a.getMainHolder()).contains(a)) {
                a.addHolder(p);
            } else {
                System.out.println("ERROR: account doesn't exist");
            }
        } else {
            System.out.println("ERROR: person doesn't exist");
        }
        saveBank();
    }

    public void removeHolder(Person p, Account a) {
        assert p != null && a != null;
        a.removeHolder(p);
        saveBank();
    }

    public Account getAccountData(Account a) {
        assert a != null && this.accounts.get(a.getMainHolder()).contains(a);
        if(this.accounts.get(a.getMainHolder()).contains(a)){
            return a;
        }
        else{
            System.out.println("ERROR: account doesn't exist");
            return null;
        }
    }

    public void setAccountData(Person p, Account a) {
        assert p != null && a != null;
        this.accounts.get(p).remove(a.getId());
        this.accounts.get(p).add(a);
        saveBank();
    }

    public void addAccount(Account a){
        assert a != null;
        this.accounts.get(a.getMainHolder()).add(a);
        saveBank();
    }

    public void removeAccount(Person p, Account a){
        assert p != null && a != null;
        this.accounts.get(p).remove(a);
        saveBank();
    }

    public ArrayList<Account> showAccounts(Person p){
        assert p != null;
        ArrayList<Account> result = new ArrayList<Account>();
        for(Account a: this.accounts.get(p)){
            if(a.getMainHolder() == p){
                result.add(a);
            }
        }
        return result;
    }

    public ArrayList<SavingsAccount> showSavingsAccounts(Person p){
        ArrayList<SavingsAccount> result = new ArrayList<SavingsAccount>();
        for(Account a: this.accounts.get(p)){
            if(a.getMainHolder() == p && a instanceof SavingsAccount){
                result.add((SavingsAccount)a);
            }
        }
        return result;
    }

    public ArrayList<SpendingAccount> showSpendingAccounts(Person p){
        ArrayList<SpendingAccount> result = new ArrayList<SpendingAccount>();
        for(Account a: this.accounts.get(p)){
            if(a.getMainHolder() == p && a instanceof SpendingAccount){
                result.add((SpendingAccount)a);
            }
        }
        return result;
    }

    public ArrayList<Account> showAccounts(){
        ArrayList<Account> result = new ArrayList<Account>();
        for(Person p:this.accounts.keySet()){
            result.addAll(this.accounts.get(p));
        }
        assert !result.isEmpty();
        return result;
    }

    public ArrayList<SavingsAccount> showSavingsAccounts(){
        ArrayList<SavingsAccount> result = new ArrayList<SavingsAccount>();
        for(Person p:this.accounts.keySet()){
            for(Account a:this.accounts.get(p)){
                if(a instanceof SavingsAccount){
                    result.add((SavingsAccount)a);
                }
            }
        }
        return result;
    }

    public ArrayList<SpendingAccount> showSpendingAccounts(){
        ArrayList<SpendingAccount> result = new ArrayList<SpendingAccount>();
        for(Person p:this.accounts.keySet()){
            for(Account a:this.accounts.get(p)){
                if(a instanceof SpendingAccount){
                    result.add((SpendingAccount)a);
                }
            }
        }
        return result;
    }

    public ArrayList<Person> showPersons(){
        ArrayList<Person> result = new ArrayList<Person>();
        if(this.accounts.isEmpty())
            return result;
        else{
            result.addAll(this.accounts.keySet());
            return result;
        }
    }

    public void saveBank(){
        try {
            FileOutputStream fileOutput = new FileOutputStream("bank");
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(this);
            objectOutput.close();
            fileOutput.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static Bank getBank(){
        Bank b;
        try {
            FileInputStream fileInput = new FileInputStream("bank");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            b = (Bank)objectInput.readObject();
            objectInput.close();
            fileInput.close();
            return b;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getId(String s){
        int max = 0;
        if(s.equals("Person")) {
            for (Person p : this.accounts.keySet()) {
                if (p.getId() > max) {
                    max = p.getId();
                }
            }
            int min[] = new int[max + 1];
            Arrays.fill(min, 0);
            for (Person p : this.accounts.keySet()) {
                min[p.getId()] = 1;
            }
            for (int i = 0; i < max + 1; i++) {
                if (min[i] == 0) {
                    return i;
                }
            }
            return max + 1;
        }
        else {
            if (s.equals("Account")){
                for(Person p: this.accounts.keySet()){
                    for(Account a: this.accounts.get(p)){
                        if (a.getId() > max){
                            max = a.getId();
                        }
                    }
                }
                int min[] = new int[max + 1];
                Arrays.fill(min, 0);
                for(Person p: this.accounts.keySet()){
                    for(Account a: this.accounts.get(p)){
                        min[a.getId()] = 1;
                    }
                }
                for (int i = 0; i < max + 1; i++){
                    if (min[i] == 0){
                        return i;
                    }
                }
                return max + 1;
            }
            else {
                throw new IllegalArgumentException();
            }
        }
    }

    public Person getPerson(int id){
        for(Person p: this.accounts.keySet()){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public Account getAccount(int id){
        for(Person p: this.accounts.keySet()){
            for(Account a :this.accounts.get(p)){
                if (a.getId() == id){
                    return a;
                }
            }
        }
        return null;
    }
}
