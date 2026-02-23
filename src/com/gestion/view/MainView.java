package com.gestion.view;
import javax.swing.*;
import java.awt.*;


public class MainView  extends JFrame {
    public MainView() {
        // Configuration de base
        setTitle("Menu Principal - Gestion Entreprise");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80));
        JLabel lblTitle = new JLabel("TABLEAU DE BORD");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 30, 30));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton btnDepts = new JButton("GESTION DÉPARTEMENTS");
        JButton btnEmps = new JButton("GESTION EMPLOYÉS");

        // Style des boutons
        btnDepts.setBackground(new Color(52, 152, 219));
        btnDepts.setForeground(Color.WHITE);
        btnEmps.setBackground(new Color(46, 204, 113));
        btnEmps.setForeground(Color.WHITE);

        // Actions des boutons
        btnDepts.addActionListener(e -> new DepartementView().setVisible(true));
        btnEmps.addActionListener(e -> new EmployeView().setVisible(true));

        centerPanel.add(btnDepts);
        centerPanel.add(btnEmps);

        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

}
