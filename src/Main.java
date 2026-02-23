import com.gestion.dao.ConnexionBD;
import com.gestion.dao.EmployeDAO;
import com.gestion.model.Employe;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection maConnexion = ConnexionBD.getConnection();

        if (maConnexion != null) {
            System.out.println("Félicitations !  projet lance avec succe.");
        } else {
            System.out.println("Vérifie que XAMPP est lancé et que ta base 'gestion_entreprise' existe.");
        }
        EmployeDAO empDAO = new EmployeDAO();

        List<Employe> listeBase = empDAO.listerTout();

        if (listeBase.isEmpty()) {
            System.out.println("⚠ La base est vide. Ajoute un employé dans phpMyAdmin pour tester.");
        } else {
            Employe empTest = listeBase.get(0);
            int idCible = empTest.getId();
            System.out.println("🔍 Test sur l'employé ID : " + idCible + " (" + empTest.getNom() + ")");


            empTest.setNom(empTest.getNom() + " (Modifié)");
            empDAO.modifier(empTest);
            System.out.println("✅ Modification effectuée dans la base.");


             empDAO.supprimer(idCible);
            System.out.println("🗑️ Suppression effectuée dans la base.");
        }
    }

}
