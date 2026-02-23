package com.gestion.view;
import com.gestion.dao.UserDao;

import javax.swing.*;
import java.awt.*;


public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {
        // Configuration de la fenêtre
        setTitle("Authentification - Gestion Entreprise");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel Principal avec fond bleu foncé
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(44, 62, 80)); // Bleu minuit

        // Titre
        JLabel lblTitle = new JLabel("CONNEXION", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(0, 50, 400, 30);
        mainPanel.add(lblTitle);

        // Champ Utilisateur
        JLabel lblUser = new JLabel("Utilisateur :");
        lblUser.setForeground(new Color(189, 195, 199));
        lblUser.setBounds(50, 130, 100, 20);
        mainPanel.add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(50, 160, 300, 35);
        txtUsername.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        mainPanel.add(txtUsername);

        // Champ Mot de passe
        JLabel lblPass = new JLabel("Mot de passe :");
        lblPass.setForeground(new Color(189, 195, 199));
        lblPass.setBounds(50, 220, 100, 20);
        mainPanel.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(50, 250, 300, 35);
        txtPassword.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        mainPanel.add(txtPassword);

        // Bouton Se Connecter
        btnLogin = new JButton("SE CONNECTER");
        btnLogin.setBounds(50, 330, 300, 45);
        btnLogin.setBackground(new Color(52, 152, 219)); // Bleu clair
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(btnLogin);
        btnLogin.addActionListener(e ->

        {
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());

            UserDao userDAO = new UserDao();
            if (userDAO.authentifier(user, pass)) {
                JOptionPane.showMessageDialog(this, "Connexion réussie !");
                new MainView().setVisible(true);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Identifiants incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }


}

