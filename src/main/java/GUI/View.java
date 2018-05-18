package GUI;

import Model.Account;
import Model.Bank;
import Model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class View {
    private JFrame frame;
    private JPanel panel;
    private JButton exit;
    private JButton accounts;
    private JButton persons;
    private GridBagConstraints c;

    public View(final Bank bank){
        frame = new JFrame("Bank simulator 2k18");
        panel = new JPanel(new GridBagLayout());
        exit = new JButton("Exit");
        accounts = new JButton("Accounts");
        persons = new JButton("Persons");
        c = new GridBagConstraints();
        persons.setPreferredSize(accounts.getPreferredSize());
        exit.setPreferredSize(accounts.getPreferredSize());
        frame.setContentPane(panel);
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        c.weightx=0.5;
        c.weighty=0.5;
        c.anchor = GridBagConstraints.PAGE_END;
        panel.add(persons, c);
        c.gridx = 1;
        panel.add(accounts, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(exit, c);
        frame.pack();

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        persons.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Persons(bank);
            }
        });
        accounts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Accounts(bank);
            }
        });
    }

    static JTable createTable(ArrayList<?> objects){
        if(objects.isEmpty())
            return new JTable();
        String[] header = new String[objects.get(0).getClass().getDeclaredFields().length];
        String[][] data = new String[objects.size()][objects.get(0).getClass().getDeclaredFields().length];
        Object obj = objects.get(0);
        int i = 0;
        int j;
        for(Field f:obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            header[i] = f.getName();
            j = 0;
            for(Object o:objects){
                try {
                    data[j][i] = f.get(o).toString();
                    j++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        return new JTable(data, header);
    }

    public static void errorMsg(String s){
        final JFrame msgWindow = new JFrame("ERROR");
        JPanel msgPanel = new JPanel(new BorderLayout());
        JLabel msg = new JLabel("ERROR: " + s);
        msg.setForeground(Color.red);
        JButton ok = new JButton("OK");

        msgWindow.setVisible(true);
        msgWindow.setPreferredSize(new Dimension(300, 120));
        msgWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        msgWindow.setLocationRelativeTo(null);
        msgWindow.setContentPane(msgPanel);
        msgWindow.pack();

        msgPanel.add(msg, BorderLayout.CENTER);
        msgPanel.add(ok, BorderLayout.PAGE_END);
        msgWindow.pack();

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                msgWindow.dispose();
            }
        });
    }

    public static void main(String[] args) {
        Bank b = Bank.getBank();
        //Bank b = new Bank(new HashMap<Person, ArrayList<Account>>());
        new View(b);
    }
}
