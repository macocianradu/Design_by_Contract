package Model;

import java.util.ArrayList;
import java.util.Calendar;

public class Account  {
    private int id;
    private Person mainHolder;
    private ArrayList<Person> holders;
    private Calendar startDate;
    private Calendar endDate;
    private double budget;

    public Account(int id, Person mainHolder, ArrayList<Person> holders, Calendar startDate, Calendar endDate, int budget) {
        this.id = id;
        this.mainHolder = mainHolder;
        this.holders = holders;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
    }

    public void addHolder(Person p){
        this.holders.add(p);
    }

    public void removeHolder(Person p){
        this.holders.remove(p);
    }

    public int getId(){
        return this.id;
    }

    public void setBudget(double budget) {
        this.budget = budget;
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
}
