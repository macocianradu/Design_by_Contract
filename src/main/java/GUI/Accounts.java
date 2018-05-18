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
    private JButton editPerson;
    private JButton deleteAccount;
    private GridBagConstraints c;
    private Dimension screenSize;
    private Dimension windowSize;
    private Account a;

    public Accounts(final Bank bank) {
        frame = new JFrame("Accounts window");
        panel = new JPanel(new GridBagLayout());
        exit = new JButton("Exit");
        addAccount = new JButton("Add Account");
        editPerson = new JButton("Edit Account");
        deleteAccount = new JButton("Delete Account");
        accounts = new JTable();
        c = new GridBagConstraints();
        screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        windowSize = new Dimension(640, 480);

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

        c.anchor = GridBagConstraints.PAGE_START;
        c.weighty = 0.5;
        c.weightx = 0.5;
        panel.add(addAccount, c);
        c.gridx = 1;
        panel.add(editPerson, c);
        c.gridx = 2;
        panel.add(deleteAccount, c);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 3;
        c.gridheight = 2;
        panel.add(accounts, c);
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy ++;
        panel.add(exit, c);
        frame.pack();

        addAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addAccount(bank, null);
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
        editPerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(a == null){
                    View.errorMsg("Select an account to edit");
                }
                else {
                    addAccount(bank, a);
                }
            }
        });
    }

    private void addAccount(final Bank bank, final Account account){
        final JFrame account_frame;
        JPanel person_panel = new JPanel(new GridBagLayout());
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
        account_frame.setContentPane(person_panel);
        account_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        account_frame.setVisible(true);
        account_frame.pack();
        account_frame.setLocationRelativeTo(null);

        con.anchor = GridBagConstraints.LAST_LINE_START;
        person_panel.add(new Label("Main Holder ID: "), con);
        con.gridy++;
        con.fill = GridBagConstraints.HORIZONTAL;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        person_panel.add(mhID, con);

        con.gridy++;
        con.anchor = GridBagConstraints.LAST_LINE_START;
        person_panel.add(new Label("End Date: "), con);
        con.gridy++;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        person_panel.add(endDate, con);

        con.gridy++;
        con.anchor = GridBagConstraints.LAST_LINE_START;
        person_panel.add(new Label("Type: "), con);
        con.gridy++;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        person_panel.add(type, con);

        con.gridwidth = 1;
        con.gridy++;
        con.fill = GridBagConstraints.NONE;
        con.anchor = GridBagConstraints.CENTER;
        person_panel.add(cancel, con);

        con.gridx++;
        person_panel.add(add, con);

        person_panel.validate();
        person_panel.repaint();
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

    private Calendar validDate(String s){
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        try {
            Date date = format.parse(s);
            Calendar result = Calendar.getInstance();
            result.setTime(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JTable createTable(ArrayList<Account> accounts){
        JTable table;
        String[] Header = {"id", "Main Holder", "Main Holder ID", "NrOfHolders", "StartDate", "EndDate", "Budget", "Type"};
        String[][] Data = new String[accounts.size()][8];
        for(int i = 0; i < accounts.size(); i++){
            Data[i][0] = Integer.toString(accounts.get(i).getId());
            Data[i][1] = accounts.get(i).getMainHolder().getName();
            Data[i][2] = Integer.toString(accounts.get(i).getMainHolder().getId());
            Data[i][3] = Integer.toString(accounts.get(i).getHolders());
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
