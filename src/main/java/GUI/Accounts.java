package GUI;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Accounts {
    private JFrame frame;
    private JPanel panel;
    private JButton exit;
    private JTable accounts;
    private JButton addAccount;
    private JButton editAccount;
    private JButton deleteAccount;
    private GridBagConstraints c;
    private Dimension screenSize;
    private Dimension windowSize;
    private JButton withdraw;
    private JButton deposit;
    private Account a;
    private Person holder;
    private JTable holders;
    private Bank bank;

    public Accounts(Bank b) {
        frame = new JFrame("Accounts window");
        panel = new JPanel(new GridBagLayout());
        exit = new JButton("Exit");
        addAccount = new JButton("Add Account");
        editAccount = new JButton("Edit Account");
        deleteAccount = new JButton("Delete Account");
        withdraw = new JButton("Withdraw");
        deposit = new JButton("Deposit");
        accounts = new JTable();
        c = new GridBagConstraints();
        screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        windowSize = new Dimension(640, 480);
        bank = b;

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setContentPane(panel);
        frame.setLocation((int) (screenSize.getWidth() / 2 - windowSize.getWidth() / 2) + 250, (int) (screenSize.getHeight() / 2 - windowSize.getHeight() / 2));
        frame.pack();
        if (!bank.showAccounts().isEmpty()) {
            accounts = createTable(bank.showAccounts());
        }
        accounts.setDefaultEditor(Object.class, null);
        JScrollPane pane = new JScrollPane(accounts);
        pane.setPreferredSize(new Dimension(120, 120));

        c.anchor = GridBagConstraints.PAGE_START;
        c.weighty = 1;
        c.weightx = 1;
        panel.add(addAccount, c);
        c.gridx = 1;
        panel.add(editAccount, c);
        c.gridx = 2;
        panel.add(deleteAccount, c);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 3;
        c.gridheight = 2;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(pane, c);
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy ++;
        panel.add(withdraw, c);
        c.gridx +=2;
        panel.add(deposit, c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 3;
        panel.add(exit, c);
        frame.pack();

        addAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addAccount(null);
            }
        });
        accounts.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                int id;
                int row = accounts.rowAtPoint(evt.getPoint());
                id = Integer.valueOf(accounts.getValueAt(row, 0).toString());
                a = bank.getAccount(id);
            }
        });
        deleteAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (a == null){
                    View.errorMsg("Select an account to delete");
                }
                else {
                    bank.removeAccount(a.getMainHolder(), a);
                    frame.dispose();
                    new Accounts(bank);
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        editAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(a == null){
                    View.errorMsg("Select an account to edit");
                }
                else {
                    editAccount();
                }
            }
        });
        withdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(a == null){
                    View.errorMsg("Select an account to withdraw from");
                }
                else {
                    withdraw();
                }
            }
        });
        deposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(a == null){
                    View.errorMsg("Select an account to deposit to");
                }
                else {
                    deposit();
                }
            }
        });
    }

    private void editAccount(){
        final JFrame account_frame = new JFrame("Edit Account");
        JPanel account_panel = new JPanel(new GridBagLayout());
        GridBagConstraints con = new GridBagConstraints();
        JButton add = new JButton("Edit Account");
        JButton cancel = new JButton("Cancel");
        JButton addHolder = new JButton("Add Holder");
        JButton removeHolder = new JButton("Remove Holder");
        final JTextField budget = new JTextField();
        editAccount.setPreferredSize(removeHolder.getPreferredSize());
        cancel.setPreferredSize(removeHolder.getPreferredSize());
        editAccount.setPreferredSize(removeHolder.getPreferredSize());
        holders = new JTable();
        con.gridwidth = 2;
        con.weightx = 0.5;
        con.weighty = 0.5;
        con.gridx = 0;
        con.gridy = 0;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        cancel.setPreferredSize(add.getPreferredSize());

        account_frame.setPreferredSize(new Dimension(320, 480));
        account_frame.setContentPane(account_panel);
        account_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        account_frame.setVisible(true);
        account_frame.pack();
        account_frame.setLocationRelativeTo(null);

        if(a.getHolders().size() > 0){
            holders = View.createTable(a.getHolders());
        }
        holders.setDefaultEditor(Object.class, null);
        JScrollPane pane =new JScrollPane(holders);
        pane.setPreferredSize(new Dimension(120, 120));

        con.anchor = GridBagConstraints.LAST_LINE_START;
        account_panel.add(new Label("Set Budget: "), con);
        con.gridy++;
        con.fill = GridBagConstraints.HORIZONTAL;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        account_panel.add(budget, con);

        con.gridy++;
        con.gridheight = 3;
        con.weightx = 0.5;
        con.weighty = 0.5;
        account_panel.add(pane, con);

        con.gridy++;
        con.gridheight = 1;
        con.gridwidth = 1;
        con.weightx = 1;
        con.weighty = 1;
        con.anchor = GridBagConstraints.PAGE_END;
        con.fill = GridBagConstraints.NONE;
        account_panel.add(removeHolder, con);
        con.gridx++;
        account_panel.add(addHolder, con);
        con.gridx = 0;
        con.gridy++;
        con.anchor = GridBagConstraints.CENTER;

        account_panel.add(cancel, con);
        con.gridx++;
        account_panel.add(editAccount, con);

        account_panel.validate();
        account_panel.repaint();
        account_frame.pack();

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                account_frame.dispose();
                frame.dispose();
                new Accounts(bank);
            }
        });
        editAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                a.setBudget(Integer.valueOf(budget.getText()));
                bank.saveBank();
                account_frame.dispose();
                frame.dispose();
                new Accounts(bank);
            }
        });
        removeHolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(holder == null){
                    View.errorMsg("Please select an account from the list");
                }
                else {
                    System.out.println(holder.getId());
                    a.removeHolder(holder);
                    bank.saveBank();
                    account_frame.dispose();
                    editAccount();
                }
            }
        });
        addHolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addHolder(account_frame);
            }
        });
        holders.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                int id;
                int row = holders.rowAtPoint(evt.getPoint());
                id = Integer.valueOf(holders.getValueAt(row, 0).toString());
                holder = bank.getPerson(id);
            }
        });
    }

    private void addAccount(final Account account){
        final JFrame account_frame;
        JPanel account_panel = new JPanel(new GridBagLayout());
        GridBagConstraints con = new GridBagConstraints();
        JButton add;
        if(a == null) {
            add = new JButton("Add Account");
            account_frame = new JFrame("Add Account");
        }
        else {
            add = new JButton("Edit Account");
            account_frame = new JFrame("Edit Account");
        }
        JButton cancel = new JButton("Cancel");
        final JTextField mhID = new JTextField();
        final JTextField type = new JTextField();
        final JTextField endDate = new JTextField();
        con.gridwidth = 2;
        con.weightx = 0.5;
        con.weighty = 0.5;
        con.gridx = 0;
        con.gridy = 0;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        cancel.setPreferredSize(add.getPreferredSize());

        account_frame.setPreferredSize(new Dimension(320, 480));
        account_frame.setContentPane(account_panel);
        account_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        account_frame.setVisible(true);
        account_frame.pack();
        account_frame.setLocationRelativeTo(null);

        con.anchor = GridBagConstraints.LAST_LINE_START;
        account_panel.add(new Label("Main Holder ID: "), con);
        con.gridy++;
        con.fill = GridBagConstraints.HORIZONTAL;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        account_panel.add(mhID, con);

        con.gridy++;
        con.anchor = GridBagConstraints.LAST_LINE_START;
        account_panel.add(new Label("End Date: "), con);
        con.gridy++;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        account_panel.add(endDate, con);

        con.gridy++;
        con.anchor = GridBagConstraints.LAST_LINE_START;
        account_panel.add(new Label("Type: "), con);
        con.gridy++;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        account_panel.add(type, con);

        con.gridwidth = 1;
        con.gridy++;
        con.fill = GridBagConstraints.NONE;
        con.anchor = GridBagConstraints.CENTER;
        account_panel.add(cancel, con);

        con.gridx++;
        account_panel.add(add, con);

        account_panel.validate();
        account_panel.repaint();
        account_frame.pack();

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                account_frame.dispose();
            }
        });

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Calendar date = validDate(endDate.getText());
                if (bank.getPerson(Integer.valueOf(mhID.getText())) == null) {
                    View.errorMsg("ID doesn't point to a person");
                }
                else if (!type.getText().equals("Savings") && !type.getText().equals("Spending")) {
                    View.errorMsg("Type must be 'Savings' or 'Spending'");
                }
                else if (date != null){
                    if (a != null) {
                        bank.removeAccount(account.getMainHolder(), account);
                    }
                    if(type.getText().equals("Savings")){
                        bank.addAccount(new SavingsAccount(bank.getId("Account"),
                                bank.getPerson(Integer.valueOf(mhID.getText())),
                                new ArrayList<Person>(),
                                Calendar.getInstance(),
                                date,
                                0));
                    }
                    else
                        bank.addAccount(new SpendingAccount(bank.getId("Account"),
                                bank.getPerson(Integer.valueOf(mhID.getText())),
                                new ArrayList<Person>(),
                                Calendar.getInstance(),
                                date,
                                0));
                    account_frame.dispose();
                    frame.dispose();
                    new Accounts(bank);
                }
            }
        });
    }

    private void addHolder(final JFrame aframe){
        final JFrame jFrame = new JFrame("Add holder");
        JPanel jPanel = new JPanel(new BorderLayout());
        final JTextField id = new JTextField();
        JButton addHolder = new JButton("Add Holder");

        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setPreferredSize(new Dimension(160, 120));
        jFrame.pack();
        jFrame.setContentPane(jPanel);
        jFrame.setLocationRelativeTo(null);

        jPanel.add(new Label("Insert Person id"), BorderLayout.PAGE_START);
        jPanel.add(id, BorderLayout.CENTER);
        jPanel.add(addHolder, BorderLayout.PAGE_END);

        addHolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Person p = bank.getPerson(Integer.valueOf(id.getText()));
                a.addHolder(p);
                bank.saveBank();
                jFrame.dispose();
                aframe.dispose();
                editAccount();
            }
        });
    }

    private Calendar validDate(String s){
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        try {
            Date date = format.parse(s);
            Calendar result = Calendar.getInstance();
            System.out.print(date.toString());
            result.setTime(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void withdraw(){
        final JFrame jFrame = new JFrame("Withdraw");
        JPanel jPanel = new JPanel(new BorderLayout());
        final JTextField sum = new JTextField();
        JButton ok = new JButton("Withdraw");

        jFrame.setContentPane(jPanel);
        jFrame.setPreferredSize(new Dimension(300, 200));
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.pack();

        jPanel.add(new JLabel("Enter Sum: "), BorderLayout.PAGE_START);
        jPanel.add(sum, BorderLayout.CENTER);
        jPanel.add(ok, BorderLayout.PAGE_END);
        jFrame.pack();

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Integer.valueOf(sum.getText()) < a.getBudget()) {
                    if(a instanceof SpendingAccount){
                        SpendingAccount aux = (SpendingAccount)a;
                        aux.withdraw(Double.valueOf(sum.getText()));
                        bank.saveBank();
                        jFrame.dispose();
                        frame.dispose();
                        new Accounts(bank);
                    }
                    else {
                        SavingsAccount aux = (SavingsAccount) a;
                        aux.withdraw(0);
                        bank.saveBank();
                        jFrame.dispose();
                        frame.dispose();
                        new Accounts(bank);
                    }
                }
                else{
                    View.errorMsg("Insert integer number smaller than the budget");
                }
            }
        });
    }

    private void deposit(){
        final JFrame jFrame = new JFrame("Deposit");
        JPanel jPanel = new JPanel(new BorderLayout());
        final JTextField sum = new JTextField();
        JButton ok = new JButton("Deposit");

        jFrame.setContentPane(jPanel);
        jFrame.setPreferredSize(new Dimension(300, 200));
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.pack();

        jPanel.add(new JLabel("Enter Sum: "), BorderLayout.PAGE_START);
        jPanel.add(sum, BorderLayout.CENTER);
        jPanel.add(ok, BorderLayout.PAGE_END);
        jFrame.pack();

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(a instanceof SpendingAccount){
                    SpendingAccount aux = (SpendingAccount)a;
                    aux.deposit(Double.valueOf(sum.getText()));
                    bank.saveBank();
                    jFrame.dispose();
                    frame.dispose();
                    new Accounts(bank);
                }
                else {
                    SavingsAccount aux = (SavingsAccount) a;
                    aux.deposit(Double.valueOf(sum.getText()));
                    bank.saveBank();
                    jFrame.dispose();
                    frame.dispose();
                    new Accounts(bank);
                }
            }
        });
    }

    private JTable createTable(ArrayList<Account> accounts){
        JTable table;
        String[] Header = {"id", "Main Holder", "Main Holder ID", "NrOfHolders", "StartDate", "EndDate", "Budget", "Type"};
        String[][] Data = new String[accounts.size()][8];
        for(int i = 0; i < accounts.size(); i++){
            Data[i][0] = Integer.toString(accounts.get(i).getId());
            Data[i][1] = accounts.get(i).getMainHolder().getName();
            Data[i][2] = Integer.toString(accounts.get(i).getMainHolder().getId());
            Data[i][3] = Integer.toString(accounts.get(i).getHolders().size());
            Calendar aux = accounts.get(i).getStartDate();
            Data[i][4] = aux.get(Calendar.YEAR) + "/" + aux.get(Calendar.MONTH) + "/" + aux.get(Calendar.DAY_OF_MONTH);
            aux = accounts.get(i).getEndDate();
            Data[i][5] = aux.get(Calendar.YEAR) + "/" + aux.get(Calendar.MONTH) + "/" + aux.get(Calendar.DAY_OF_MONTH);
            Data[i][6] = Double.toString(accounts.get(i).getBudget());
            if(accounts.get(i)instanceof SpendingAccount){
                Data[i][7] = "Spending";
            }
            else{
                Data[i][7] = "Savings";
            }
        }
        table = new JTable(Data, Header);
        return table;
    }
}
