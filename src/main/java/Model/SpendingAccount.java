package Model;

import java.util.ArrayList;
import java.util.Calendar;

public class SpendingAccount extends Account {
    public SpendingAccount(int id, Person mainHolder, ArrayList<Person> holders, Calendar startDate, Calendar endDate, int budget) {
        super(id, mainHolder, holders, startDate, endDate, budget);
    }

    public void deposit(double sum){
        this.setBudget(this.getBudget() + sum);
        this.notifyObserver("Account: " + this.getId() + " - Money Deposited sum: " + sum);
    }

    public double withdraw(double sum){
        if(sum > this.getBudget()){
            System.out.println("Error not enough funds!");
            return 0;
        }
        else {
            this.setBudget(this.getBudget() - sum);
            this.notifyObserver("Account: " + this.getId() + " - Money Withdrawn sum: " + sum);
            return sum;
        }
    }
}
