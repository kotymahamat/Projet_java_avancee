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
    private JTextField fldNom, fldPrenom, fldMail;
    private JComboBox<departement> cbxServices;
    private JTable tblData;
    private DefaultTableModel tabModel;
    private EmployeDAO eService = new EmployeDAO();
    private DepartementDAO dService = new DepartementDAO();

    public EmployeView() {
        setTitle("Panel Collaborateurs - Système Entreprise");
        setSize(1000, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- CONTENEUR PRINCIPAL ---
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(new Color(30, 39, 46)); // Anthracite

        // --- ZONE DE DROITE : FORMULAIRE D'ÉDITION ---
        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.Y_AXIS));
        editorPanel.setPreferredSize(new Dimension(320, 0));
        editorPanel.setBackground(new Color(43, 58, 74));
        editorPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 211, 42)), "Fiche Information", 0, 0, null, Color.WHITE));

        fldNom = new JTextField();
        fldPrenom = new JTextField();
        fldMail = new JTextField();
        cbxServices = new JComboBox<>();

        // Style des champs
        Dimension fieldSize = new Dimension(280, 30);
        setupField(fldNom, fieldSize);
        setupField(fldPrenom, fieldSize);
        setupField(fldMail, fieldSize);

        // Peupler le combo
        dService.listerTout().forEach(cbxServices::addItem);

        // Boutons personnalisés
        JButton bSave = createCustomBtn("VALIDER", new Color(255, 211, 42), Color.BLACK);
        JButton bEdit = createCustomBtn("MODIFIER", new Color(11, 232, 129), Color.WHITE);
        JButton bTrash = createCustomBtn("EFFACER", new Color(255, 94, 87), Color.WHITE);

        // Ajout des composants à l'éditeur
        editorPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addInput(editorPanel, "NOM :", fldNom);
        addInput(editorPanel, "PRÉNOM :", fldPrenom);
        addInput(editorPanel, "COURRIEL :", fldMail);
        addInput(editorPanel, "UNITÉ :", cbxServices);
        editorPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        editorPanel.add(bSave); editorPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        editorPanel.add(bEdit); editorPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        editorPanel.add(bTrash);

        // --- ZONE DE GAUCHE : LISTE (TABLEAU) ---
        tabModel = new DefaultTableModel(new String[]{"REF", "NOM", "PRENOM", "EMAIL", "DEPT_ID"}, 0);
        tblData = new JTable(tabModel);
        tblData.setFillsViewportHeight(true);
        tblData.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(tblData);

        // --- SPLIT PANE POUR SÉPARER LES DEUX ---
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, editorPanel);
        split.setDividerLocation(650);
        split.setResizeWeight(0.7);

        // --- LOGIQUE DES BOUTONS ---
        bSave.addActionListener(e -> {
            departement d = (departement) cbxServices.getSelectedItem();
            if (d != null) {
                eService.ajouter(new Employe(0, fldNom.getText(), fldPrenom.getText(), fldMail.getText(), d.getId()));
                refresh();
                resetFields();
            }
        });

        bEdit.addActionListener(e -> {
            int row = tblData.getSelectedRow();
            if (row != -1) {
                int id = (int) tabModel.getValueAt(row, 0);
                departement d = (departement) cbxServices.getSelectedItem();
                eService.modifier(new Employe(id, fldNom.getText(), fldPrenom.getText(), fldMail.getText(), d.getId()));
                refresh();
                JOptionPane.showMessageDialog(this, "Mise à jour réussie");
            }
        });

        bTrash.addActionListener(e -> {
            int row = tblData.getSelectedRow();
            if (row != -1) {
                int id = (int) tabModel.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "Supprimer cet enregistrement ?", "Confirmation", 0) == 0) {
                    eService.supprimer(id);
                    refresh();
                }
            }
        });

        tblData.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblData.getSelectedRow() != -1) {
                int row = tblData.getSelectedRow();
                fldNom.setText(tabModel.getValueAt(row, 1).toString());
                fldPrenom.setText(tabModel.getValueAt(row, 2).toString());
                fldMail.setText(tabModel.getValueAt(row, 3).toString());
            }
        });

        mainContainer.add(split, BorderLayout.CENTER);
        add(mainContainer);
        refresh();
    }

    // --- MÉTHODES DE STYLE ---
    private void setupField(JTextField f, Dimension d) {
        f.setMaximumSize(d);
        f.setPreferredSize(d);
    }

    private void addInput(JPanel p, String label, JComponent comp) {
        JLabel l = new JLabel(label);
        l.setForeground(Color.WHITE);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        comp.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(l);
        p.add(comp);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private JButton createCustomBtn(String txt, Color bg, Color fg) {
        JButton b = new JButton(txt);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(200, 40));
        return b;
    }

    private void refresh() {
        tabModel.setRowCount(0);
        for (Employe emp : eService.listerTout()) {
            tabModel.addRow(new Object[]{emp.getId(), emp.getNom(), emp.getPrenom(), emp.getEmail(), emp.getIdDepartement()});
        }
    }

    private void resetFields() {
        fldNom.setText(""); fldPrenom.setText(""); fldMail.setText("");
    }
}
