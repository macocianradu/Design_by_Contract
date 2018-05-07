package Model;

import java.util.ArrayList;
import java.util.Calendar;

public class SpendingAccount extends Account {
    public SpendingAccount(int id, Person mainHolder, ArrayList<Person> holders, Calendar startDate, Calendar endDate, int budget) {
        super(id, mainHolder, holders, startDate, endDate, budget);
    }

    public void deposit(int sum){
        this.setBudget(this.getBudget() + sum);
    }

    public void withdraw(int sum){
        this.setBudget(this.getBudget() - sum);
    }
}
