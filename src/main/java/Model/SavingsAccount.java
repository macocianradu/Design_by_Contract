package Model;

import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class SavingsAccount extends Account {

    private Boolean deposited;

    public SavingsAccount(int id, Person mainHolder, ArrayList<Person> holders, Calendar startDate, Calendar endDate, int budget) {
        super(id, mainHolder, holders, startDate, endDate, budget);
        this.deposited = false;
    }

    public void setBudget(){
        super.setBudget(this.getBudget() + this.getBudget()*getRate());
    }

    public double getRate(){
        double rate;
        if(Calendar.getInstance().after(this.getEndDate())){
            rate = 0.1*(this.getEndDate().get(YEAR) - this.getStartDate().get(YEAR));
            rate += (0.1/12)*(this.getEndDate().get(MONTH) - this.getStartDate().get(MONTH));
        } else {
            rate = 0.1*(Calendar.getInstance().get(YEAR) - this.getStartDate().get(YEAR));
            rate += (0.1/12)*(Calendar.getInstance().get(MONTH) - this.getStartDate().get(MONTH));
        }
        return rate;
    }

    public double withdraw(){
        this.setBudget();
        return this.getBudget();
    }

    public void deposit(double sum){
        if(this.deposited){
            System.out.println("ERROR: cannot deposit multiple amounts to savings account");
        }
        else{
            this.deposited = true;
            this.setBudget(sum);
        }
    }
}
