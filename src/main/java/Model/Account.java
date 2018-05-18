package Model;

import ObserverDP.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class Account implements Observable, Serializable {
    private int id;
    private Person mainHolder;
    private ArrayList<Person> holders;
    private Calendar startDate;
    private Calendar endDate;
    private double budget;
    private Observer observer;

    public Account(int id, Person mainHolder, ArrayList<Person> holders, Calendar startDate, Calendar endDate, int budget) {
        this.id = id;
        this.mainHolder = mainHolder;
        this.holders = holders;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        addObserver(mainHolder);
    }

    abstract double withdraw(double sum);

    abstract void deposit(double sum);

    public void addHolder(Person p){
        this.holders.add(p);
        notifyObserver("Account: " + this.id + " - Added holder id: " + p.getId() + "name: " + p.getName());
    }

    public void removeHolder(Person p){
        this.holders.remove(p);
        notifyObserver("Account: " + this.id + " - Removed holder id: " + p.getId() + "name: " + p.getName());
    }

    public int getId(){
        return this.id;
    }

    public void setBudget(double budget) {
        this.budget = budget;
        notifyObserver("Account: " + this.id + " - Budget changed to: " + budget);
    }

    public double getBudget() {
        return budget;
    }

    public Calendar getEndDate(){
        return this.endDate;
    }

    public Calendar getStartDate(){
        return this.startDate;
    }

    public Person getMainHolder(){
        return this.mainHolder;
    }

    public int getHolders(){
        return this.holders.size();
    }

    public void addObserver(Observer o) {
        this.observer = o;
    }

    public void notifyObserver(String s) {
        observer.update(s);
    }
}
