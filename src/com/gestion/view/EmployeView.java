package com.gestion.view;

import com.gestion.dao.DepartementDAO;
import com.gestion.dao.EmployeDAO;
import com.gestion.model.departement;
import com.gestion.model.Employe;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeView extends JFrame {
    private JTextField txtNom, txtPrenom, txtEmail;
    private JComboBox<departement> comboDept;
    private JTable table;
    private DefaultTableModel model;
    private EmployeDAO empDAO = new EmployeDAO();
    private DepartementDAO deptDAO = new DepartementDAO();

    public EmployeView() {
        setTitle("Gestion des Employés");
        setSize(900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. Panel Formulaire (NORTH) ---
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(new Color(52, 73, 94)); // Bleu gris
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtEmail = new JTextField();
        comboDept = new JComboBox<>();

        // Charger les départements
        List<departement> depts = deptDAO.listerTout();
        for (departement d : depts) {
            comboDept.addItem(d);
        }

        JLabel lblNom = new JLabel("Nom :"); lblNom.setForeground(Color.WHITE);
        JLabel lblPre = new JLabel("Prénom :"); lblPre.setForeground(Color.WHITE);
        JLabel lblMail = new JLabel("Email :"); lblMail.setForeground(Color.WHITE);
        JLabel lblDep = new JLabel("Département :"); lblDep.setForeground(Color.WHITE);

        formPanel.add(lblNom); formPanel.add(txtNom);
        formPanel.add(lblPre); formPanel.add(txtPrenom);
        formPanel.add(lblMail); formPanel.add(txtEmail);
        formPanel.add(lblDep); formPanel.add(comboDept);

        // --- 2. Tableau (CENTER) ---
        model = new DefaultTableModel(new String[]{"ID", "Nom", "Prénom", "Email", "ID Dept"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // --- 3. Panel Boutons (SOUTH) ---
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBoutons.setBackground(new Color(236, 240, 241));

        JButton btnAdd = new JButton("Ajouter ➕");
        btnAdd.setBackground(new Color(52, 152, 219));
        btnAdd.setForeground(Color.WHITE);

        JButton btnModifier = new JButton("Modifier ✏️");
        btnModifier.setBackground(new Color(39, 174, 96)); // VERT
        btnModifier.setForeground(Color.WHITE);

        JButton btnSupprimer = new JButton("Supprimer 🗑️");
        btnSupprimer.setBackground(new Color(192, 57, 43)); // ROUGE
        btnSupprimer.setForeground(Color.WHITE);

        panelBoutons.add(btnAdd);
        panelBoutons.add(btnModifier);
        panelBoutons.add(btnSupprimer);

        // --- 4. Assemblage de la fenêtre ---
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        // --- 5. Logique des Actions ---

        // AJOUTER
        btnAdd.addActionListener(e -> {
            departement selectedDept = (departement) comboDept.getSelectedItem();
            if (selectedDept != null) {
                Employe emp = new Employe(0, txtNom.getText(), txtPrenom.getText(), txtEmail.getText(), selectedDept.getId());
                empDAO.ajouter(emp);
                chargerTableau();
                viderChamps();
            }
        });

        // MODIFIER
        btnModifier.addActionListener(e -> {
            int ligne = table.getSelectedRow();
            if (ligne != -1) {
                int id = (int) model.getValueAt(ligne, 0);
                departement d = (departement) comboDept.getSelectedItem();
                Employe emp = new Employe(id, txtNom.getText(), txtPrenom.getText(), txtEmail.getText(), d.getId());
                empDAO.modifier(emp);
                chargerTableau();
                JOptionPane.showMessageDialog(this, "Modifié avec succès !");
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez un employé dans la table !");
            }
        });

        // SUPPRIMER
        btnSupprimer.addActionListener(e -> {
            int ligne = table.getSelectedRow();
            if (ligne != -1) {
                int id = (int) model.getValueAt(ligne, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Supprimer cet employé ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    empDAO.supprimer(id);
                    chargerTableau();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez une ligne !");
            }
        });

        // Remplir les champs quand on clique sur une ligne
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtNom.setText(model.getValueAt(row, 1).toString());
                txtPrenom.setText(model.getValueAt(row, 2).toString());
                txtEmail.setText(model.getValueAt(row, 3).toString());
            }
        });

        chargerTableau();
    }

    private void chargerTableau() {
        model.setRowCount(0);
        for (Employe emp : empDAO.listerTout()) {
            model.addRow(new Object[]{emp.getId(), emp.getNom(), emp.getPrenom(), emp.getEmail(), emp.getIdDepartement()});
        }
    }

    private void viderChamps() {
        txtNom.setText("");
        txtPrenom.setText("");
        txtEmail.setText("");
    }
}
