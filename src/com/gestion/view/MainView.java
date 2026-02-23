package com.gestion.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainView extends JFrame {

    public MainView() {
        setTitle("Console de Supervision - Enterprise v1.0");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 242, 245));
        setLayout(new BorderLayout());

        // --- 1. SIDEBAR (MENU LATÉRAL) ---
        JPanel sideMenu = new JPanel();
        sideMenu.setPreferredSize(new Dimension(260, 0));
        sideMenu.setBackground(new Color(33, 37, 41));
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));

        JLabel logoLabel = new JLabel("ERP MANAGER");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(30, 25, 50, 0));

        JButton btnGoDept = createMenuButton("📁 Services & Dép.");
        JButton btnGoEmp = createMenuButton("👥 Collaborateurs");
        JButton btnLogout = createMenuButton("🔌 Déconnexion");
        btnLogout.setForeground(new Color(255, 107, 107));

        sideMenu.add(logoLabel);
        sideMenu.add(btnGoDept);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(btnGoEmp);
        sideMenu.add(Box.createVerticalGlue());
        sideMenu.add(btnLogout);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- 2. HEADER BAR (EN-TÊTE) ---
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(0, 70));
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));

        JLabel pageTitle = new JLabel("Tableau de bord principal");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pageTitle.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        topBar.add(pageTitle, BorderLayout.WEST);

        // --- 3. CONTENU CENTRAL (ACCUEIL) ---
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Appel des méthodes pour créer les cartes
        JPanel card1 = createStatCard("TOTAL EMPLOYÉS", "Consulter la liste", new Color(74, 144, 226));
        JPanel card2 = createStatCard("DÉPARTEMENTS", "Gérer les unités", new Color(80, 227, 194));

        contentPanel.add(card1, gbc);
        contentPanel.add(card2, gbc);

        // --- LOGIQUE DES BOUTONS ---
        btnGoDept.addActionListener(e -> new DepartementView().setVisible(true));
        btnGoEmp.addActionListener(e -> new EmployeView().setVisible(true));
        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginView().setVisible(true);
        });

        // Assemblage final
        add(sideMenu, BorderLayout.WEST);
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(topBar, BorderLayout.NORTH);
        rightPanel.add(contentPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.CENTER);
    }

    // --- MÉTHODE MANQUANTE : CRÉATION DES CARTES ---
    private JPanel createStatCard(String title, String sub, Color accent) {
        JPanel p = new JPanel(new BorderLayout());
        p.setPreferredSize(new Dimension(280, 160));
        p.setBackground(Color.WHITE);
        // Ajoute une petite bordure de couleur sur la gauche pour le style
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, accent),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 13));
        t.setForeground(Color.GRAY);

        JLabel s = new JLabel("<html><body style='width: 150px'>" + sub + "</body></html>");
        s.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        s.setForeground(new Color(50, 50, 50));

        p.add(t, BorderLayout.NORTH);
        p.add(s, BorderLayout.CENTER);

        return p;
    }

    // --- HELPER POUR LES BOUTONS DU MENU ---
    private JButton createMenuButton(String text) {
        JButton b = new JButton(text);
        b.setMaximumSize(new Dimension(240, 45));
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.setForeground(new Color(200, 200, 200));
        b.setBackground(new Color(33, 37, 41));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setMargin(new Insets(0, 25, 0, 0));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(45, 52, 54));
                b.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                b.setBackground(new Color(33, 37, 41));
                b.setForeground(new Color(200, 200, 200));
            }
        });
        return b;
    }
}
