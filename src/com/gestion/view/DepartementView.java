package com.gestion.view;

import com.gestion.dao.DepartementDAO;
import com.gestion.model.departement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartementView extends JFrame {
    private JTextField txtNom, txtDescription;
    private JTable table;
    private DefaultTableModel model;
    private DepartementDAO deptDAO = new DepartementDAO();

    public DepartementView() {
        setTitle("Gestion des Départements");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(new Color(44, 62, 80)); // Bleu Foncé
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNom = new JLabel("Nom du département :");
        lblNom.setForeground(Color.WHITE);
        txtNom = new JTextField();

        JLabel lblDesc = new JLabel("Description :");
        lblDesc.setForeground(Color.WHITE);
        txtDescription = new JTextField();

        formPanel.add(lblNom); formPanel.add(txtNom);
        formPanel.add(lblDesc); formPanel.add(txtDescription);

        // --- Table  ---
        String[] columns = {"ID", "Nom", "Description"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        // --- Panel Boutons (Bas) ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(new Color(236, 240, 241));

        // Bouton AJOUTER (Bleu)
        JButton btnAdd = new JButton("Ajouter ➕");
        btnAdd.setBackground(new Color(52, 152, 219));
        btnAdd.setForeground(Color.WHITE);

        // Bouton MODIFIER (Vert)
        JButton btnModifDept = new JButton("Modifier ✏️");
        btnModifDept.setBackground(new Color(39, 174, 96));
        btnModifDept.setForeground(Color.WHITE);

        // Bouton SUPPRIMER (Rouge)
        JButton btnSuppDept = new JButton("Supprimer 🗑️");
        btnSuppDept.setBackground(new Color(192, 57, 43));
        btnSuppDept.setForeground(Color.WHITE);

        btnPanel.add(btnAdd);
        btnPanel.add(btnModifDept);
        btnPanel.add(btnSuppDept);

        // --- ACTIONS ---

        // Ajouter
        btnAdd.addActionListener(e -> {
            if(!txtNom.getText().isEmpty()) {
                departement d = new departement(0, txtNom.getText(), txtDescription.getText());
                deptDAO.ajouter(d);
                chargerDonnees();
                txtNom.setText(""); txtDescription.setText("");
            }
        });

        // Modifier
        btnModifDept.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1){
                int id = (int) model.getValueAt(row, 0);
                // Correction des noms des variables ici : txtNom et txtDescription
                deptDAO.modifier(new departement(id, txtNom.getText(), txtDescription.getText()));
                chargerDonnees();
                JOptionPane.showMessageDialog(this, "Département modifié !");
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez un département !");
            }
        });

        // Supprimer
        btnSuppDept.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1){
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ce département ?", "Attention", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                    deptDAO.supprimer(id);
                    chargerDonnees();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez une ligne !");
            }
        });

        // les champs au clic sur le tableau
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtNom.setText(model.getValueAt(row, 1).toString());
                txtDescription.setText(model.getValueAt(row, 2).toString());
            }
        });

        // Mise en page finale
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        chargerDonnees();
    }

    private void chargerDonnees() {
        model.setRowCount(0);
        List<departement> liste = deptDAO.listerTout();
        for (departement d : liste) {
            model.addRow(new Object[]{d.getId(), d.getNom(), d.getDescription()});
        }
    }
}
