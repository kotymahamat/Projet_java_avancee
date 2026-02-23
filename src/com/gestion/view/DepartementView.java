package com.gestion.view;

import com.gestion.dao.DepartementDAO;
import com.gestion.model.departement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class DepartementView extends JFrame {
    private JTextField txtNom, txtDescription;
    private JTable table;
    private DefaultTableModel model;
    private DepartementDAO deptDAO = new DepartementDAO();

    public DepartementView() {
        setTitle("Espace Administration - Départements");
        setSize(950, 550);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 15));

        // --- SIDEBAR (PANEL DE GAUCHE POUR LE FORMULAIRE) ---
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(300, 0));
        sidePanel.setBackground(new Color(245, 246, 250)); // Gris très clair
        sidePanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JLabel titleLabel = new JLabel("Saisie des données");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtNom = createStyledField("Nom du département");
        txtDescription = createStyledField("Description");

        // --- PANEL BOUTONS (Vertical dans la sidebar) ---
        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        btnPanel.setOpaque(false);
        btnPanel.setMaximumSize(new Dimension(260, 150));

        JButton btnAdd = createModernButton("Enregistrer", new Color(108, 92, 231));
        JButton btnModifDept = createModernButton("Mettre à jour", new Color(0, 184, 148));
        JButton btnSuppDept = createModernButton("Supprimer", new Color(214, 48, 49));

        btnPanel.add(btnAdd);
        btnPanel.add(btnModifDept);
        btnPanel.add(btnSuppDept);

        sidePanel.add(titleLabel);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        sidePanel.add(new JLabel("Désignation :"));
        sidePanel.add(txtNom);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        sidePanel.add(new JLabel("Détails :"));
        sidePanel.add(txtDescription);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        sidePanel.add(btnPanel);

        // --- TABLEAU (CENTRE) ---
        String[] columns = {"ID IDENTIFIANT", "NOM DU SERVICE", "DESCRIPTION"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        // Personnalisation du header du tableau
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(45, 52, 54));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));

        table.setRowHeight(30);
        table.setSelectionBackground(new Color(162, 155, 254));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // --- LOGIQUE (Identique à ton code original) ---
        btnAdd.addActionListener(e -> {
            if(!txtNom.getText().isEmpty()) {
                deptDAO.ajouter(new departement(0, txtNom.getText(), txtDescription.getText()));
                chargerDonnees();
                clearFields();
            }
        });

        btnModifDept.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1){
                int id = (int) model.getValueAt(row, 0);
                deptDAO.modifier(new departement(id, txtNom.getText(), txtDescription.getText()));
                chargerDonnees();
                JOptionPane.showMessageDialog(this, "Modifications enregistrées");
            }
        });

        btnSuppDept.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1){
                int id = (int) model.getValueAt(row, 0);
                if(JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Validation", JOptionPane.YES_NO_OPTION) == 0) {
                    deptDAO.supprimer(id);
                    chargerDonnees();
                    clearFields();
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtNom.setText(model.getValueAt(row, 1).toString());
                txtDescription.setText(model.getValueAt(row, 2).toString());
            }
        });

        // Assemblage
        add(sidePanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        chargerDonnees();
    }

    // --- HELPER METHODS POUR LE DESIGN ---
    private JTextField createStyledField(String placeholder) {
        JTextField f = new JTextField();
        f.setMaximumSize(new Dimension(260, 35));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return f;
    }

    private JButton createModernButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void clearFields() {
        txtNom.setText("");
        txtDescription.setText("");
    }

    private void chargerDonnees() {
        model.setRowCount(0);
        List<departement> liste = deptDAO.listerTout();
        for (departement d : liste) {
            model.addRow(new Object[]{d.getId(), d.getNom(), d.getDescription()});
        }
    }
}
