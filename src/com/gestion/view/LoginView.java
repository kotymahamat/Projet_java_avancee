package com.gestion.view;

import com.gestion.dao.UserDao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame {
    private JTextField fldUser;
    private JPasswordField fldPass;
    private JButton btnEnter;

    public LoginView() {
        setTitle("Portail Collaborateurs");
        setSize(450, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- PANEL AVEC DÉGRADÉ ---
        JPanel bgPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Dégradé du Bleu Canard au Noir (plus sérieux)
                GradientPaint gp = new GradientPaint(0, 0, new Color(44, 62, 80), 0, getHeight(), new Color(20, 20, 20));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 50, 10, 50);
        g.fill = GridBagConstraints.HORIZONTAL;

        // --- TITRE DESIGN ---
        JLabel title = new JLabel("<html><center>APPLICATION CRUD<br/><span style='font-size:10px; font-weight:normal;'>GESTION DES RESSOURCES</span></center></html>", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(255, 211, 42)); // Couleur Or pour le titre
        g.gridy = 0; g.insets = new Insets(0, 0, 50, 0);
        bgPanel.add(title, g);

        // --- CHAMPS UTILISATEUR ---
        fldUser = new JTextField("Nom d'utilisateur");
        setupField(fldUser, "Nom d'utilisateur");
        g.gridy = 1; g.insets = new Insets(5, 50, 5, 50);
        bgPanel.add(fldUser, g);

        // --- CHAMPS MOT DE PASSE ---
        fldPass = new JPasswordField("");
        setupField(fldPass, "");
        g.gridy = 2;
        bgPanel.add(fldPass, g);

        // Petit texte au dessus du pass (pour aider l'ami)
        JLabel lPass = new JLabel("Code d'accès :");
        lPass.setForeground(Color.GRAY);
        lPass.setFont(new Font("Arial", Font.PLAIN, 10));
        g.gridy = 3; g.insets = new Insets(-5, 55, 15, 50);
        bgPanel.add(lPass, g);

        // --- BOUTON ---
        btnEnter = new JButton("AUTHENTIFICATION");
        btnEnter.setPreferredSize(new Dimension(0, 45));
        btnEnter.setBackground(new Color(255, 211, 42));
        btnEnter.setForeground(Color.BLACK);
        btnEnter.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEnter.setFocusPainted(false);
        btnEnter.setBorder(BorderFactory.createLineBorder(new Color(255, 211, 42), 1));
        btnEnter.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEnter.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnEnter.setBackground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { btnEnter.setBackground(new Color(255, 211, 42)); }
        });

        g.gridy = 4; g.insets = new Insets(20, 50, 0, 50);
        bgPanel.add(btnEnter, g);

        // --- LOGIQUE ---
        btnEnter.addActionListener(e -> {
            String u = fldUser.getText();
            String p = new String(fldPass.getPassword());
            if (new UserDao().authentifier(u, p)) {
                new MainView().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Accès refusé !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(bgPanel);
    }

    // Méthode pour styliser et gérer le focus (le texte qui s'efface)
    private void setupField(JTextField f, String hint) {
        f.setPreferredSize(new Dimension(300, 40));
        f.setBackground(Color.WHITE);
        f.setForeground(Color.DARK_GRAY);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        if(!hint.isEmpty()) {
            f.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (f.getText().equals(hint)) {
                        f.setText("");
                        f.setForeground(Color.BLACK);
                    }
                }
                public void focusLost(FocusEvent e) {
                    if (f.getText().isEmpty()) {
                        f.setForeground(Color.GRAY);
                        f.setText(hint);
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
