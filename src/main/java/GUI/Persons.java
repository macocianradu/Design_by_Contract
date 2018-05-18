package GUI;

import Model.Bank;
import Model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Persons {
    private JFrame frame;
    private JPanel panel;
    private JButton exit;
    private JTable persons;
    private JButton addPerson;
    private JButton editPerson;
    private JButton deletePerson;
    private GridBagConstraints c;
    private Dimension screenSize;
    private Dimension windowSize;
    private Person p;

    public Persons(final Bank bank) {
        frame = new JFrame("Persons window");
        panel = new JPanel(new GridBagLayout());
        exit = new JButton("Exit");
        addPerson = new JButton("Add Person");
        editPerson = new JButton("Edit Person");
        deletePerson = new JButton("Delete Person");
        persons = new JTable();
        c = new GridBagConstraints();
        screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        windowSize = new Dimension(640, 480);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setContentPane(panel);
        frame.setLocation((int) (screenSize.getWidth() / 2 - windowSize.getWidth() / 2) - 250, (int) (screenSize.getHeight() / 2 - windowSize.getHeight() / 2));
        frame.pack();
        if (!bank.showPersons().isEmpty()) {
            persons = View.createTable(bank.showPersons());
        }
        persons.setDefaultEditor(Object.class, null);

        c.anchor = GridBagConstraints.PAGE_START;
        c.weighty = 0.5;
        c.weightx = 0.5;
        panel.add(addPerson, c);
        c.gridx = 1;
        panel.add(editPerson, c);
        c.gridx = 2;
        panel.add(deletePerson, c);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 3;
        c.gridheight = 2;
        panel.add(persons, c);
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy ++;
        panel.add(exit, c);
        frame.pack();

        addPerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPerson(bank, null);
            }
        });
        persons.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                int row = persons.rowAtPoint(evt.getPoint());
                int id;
                id = Integer.valueOf(persons.getValueAt(row, 0).toString());
                p = bank.getPerson(id);
            }
        });
        deletePerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(p == null){
                    View.errorMsg("Select a person to delete");
                }
                else {
                    bank.removePerson(p);
                    frame.dispose();
                    new Persons(bank);
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
                if(p == null){
                    View.errorMsg("Select a person to edit");
                }
                else {
                    addPerson(bank, p);
                }
            }
        });
    }

    private void addPerson(final Bank bank, final Person p){
        final JFrame person_frame;
        JPanel person_panel = new JPanel(new GridBagLayout());
        GridBagConstraints con = new GridBagConstraints();
        JButton add;
        if(p == null) {
            add = new JButton("Add person");
            person_frame = new JFrame("Add Person");
        }
        else {
            add = new JButton("Edit person");
            person_frame = new JFrame("Edit Person");
        }
        JButton cancel = new JButton("Cancel");
        final JTextField name = new JTextField();
        final JTextField age = new JTextField();
        final JTextField address = new JTextField();
        con.gridwidth = 2;
        con.weightx = 0.5;
        con.weighty = 0.5;
        con.gridx = 0;
        con.gridy = 0;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        cancel.setPreferredSize(add.getPreferredSize());

        person_frame.setPreferredSize(new Dimension(320, 480));
        person_frame.setContentPane(person_panel);
        person_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        person_frame.setVisible(true);
        person_frame.pack();
        person_frame.setLocationRelativeTo(null);

        con.anchor = GridBagConstraints.LAST_LINE_START;
        person_panel.add(new Label("Name: "), con);
        con.gridy++;
        con.fill = GridBagConstraints.HORIZONTAL;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        person_panel.add(name, con);

        con.gridy++;
        con.anchor = GridBagConstraints.LAST_LINE_START;
        person_panel.add(new Label("Address: "), con);
        con.gridy++;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        person_panel.add(address, con);

        con.gridy++;
        con.anchor = GridBagConstraints.LAST_LINE_START;
        person_panel.add(new Label("Age: "), con);
        con.gridy++;
        con.anchor = GridBagConstraints.FIRST_LINE_START;
        person_panel.add(age, con);

        con.gridwidth = 1;
        con.gridy++;
        con.fill = GridBagConstraints.NONE;
        con.anchor = GridBagConstraints.CENTER;
        person_panel.add(cancel, con);

        con.gridx++;
        person_panel.add(add, con);

        person_panel.validate();
        person_panel.repaint();
        person_frame.pack();

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                person_frame.dispose();
            }
        });

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Integer.valueOf(age.getText()) < 18) {
                    View.errorMsg("Age must be 18 or older");
                }
                else {
                    if (p != null) {
                        bank.removePerson(p);
                    }
                    bank.addPerson(new Person(bank.getId("Person"), name.getText(), address.getText(), Integer.valueOf(age.getText())));
                    person_frame.dispose();
                    frame.dispose();
                    new Persons(bank);
                }
            }
        });
    }
}
