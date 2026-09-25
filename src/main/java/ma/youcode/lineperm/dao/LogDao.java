package ma.youcode.lineperm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.youcode.lineperm.model.AccessLog;
import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.User;

public class LogDao extends AbstractDao {

    public void save(AccessLog log) {

        String sql = "INSERT INTO logs (user_id, fichier_id, action, resultat) VALUES (?, ?, ?, ?)";

        try {
            UserDao userDao = new UserDao();
            FichierDao fichierDao = new FichierDao();

            User user = userDao.findByUsername(log.getUtilisateur());
            FichierProtege fichier = fichierDao.findByName(log.getFichier());

            if (user == null || fichier == null) {
                return;
            }

            PreparedStatement prpr = connection.prepareStatement(sql);

            prpr.setInt(1, user.getId());
            prpr.setInt(2, fichier.getId());
            prpr.setString(3, log.getAction());
            prpr.setString(4, log.getResultat());

            prpr.executeUpdate();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int compterTotal() {

        String sql = "SELECT COUNT(*) FROM logs";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();

            if (res.next()) {
                int total = res.getInt(1);

                res.close();
                prpr.close();

                return total;
            }

            res.close();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int compterRefuse() {

        String sql = "SELECT COUNT(*) FROM logs WHERE resultat = 'REFUSE'";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();

            if (res.next()) {
                int total = res.getInt(1);

                res.close();
                prpr.close();

                return total;
            }

            res.close();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<String> userDistincts() {

        List<String> users = new ArrayList<>();

        String sql = "SELECT DISTINCT u.login FROM logs l " +
                "JOIN users u ON l.user_id = u.id";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();

            while (res.next()) {
                users.add(res.getString("login"));
            }

            res.close();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public Map<String, Long> actionUser() {

        Map<String, Long> users = new HashMap<>();

        String sql = "SELECT u.login, COUNT(*) AS total FROM logs l JOIN users u ON l.user_id = u.id GROUP BY u.login";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);

            ResultSet res = prpr.executeQuery();

            while (res.next()) {
                users.put(
                        res.getString("login"),
                        res.getLong("total"));
            }

            res.close();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<String> topFichiers() {

        List<String> top = new ArrayList<>();

        String sql = """
                SELECT f.nom, COUNT(*) AS total
                FROM fichiers f
                JOIN logs l ON l.fichier_id = f.id
                GROUP BY f.id, f.nom
                ORDER BY total DESC
                LIMIT 3
                """;

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();

            while (res.next()) {
                top.add(res.getString("nom"));
            }

            res.close();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return top;
    }

    public List<String> refuseByUser() {

        List<String> users = new ArrayList<>();

        String sql = "SELECT DISTINCT u.login " +
                "FROM users u " +
                "JOIN logs l ON l.user_id = u.id " +
                "WHERE l.resultat = 'REFUSE'";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();

            while (res.next()) {
                users.add(res.getString("login"));
            }

            res.close();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public String mostUser() {

        String sql = """
                SELECT u.login, COUNT(*) AS total
                FROM users u
                JOIN logs l ON l.user_id = u.id
                GROUP BY u.id, u.login
                ORDER BY total DESC
                LIMIT 1
                """;

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();

            if (res.next()) {
                String user = res.getString("login");

                res.close();
                prpr.close();

                return user;
            }

            res.close();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Map<String, Integer> repartitionByAction() {

        Map<String, Integer> actions = new HashMap<>();

        String sql = "SELECT action, COUNT(action) AS ttl " +
                "FROM logs " +
                "GROUP BY action " +
                "ORDER BY ttl DESC";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();

            while (res.next()) {
                actions.put(
                        res.getString("action"),
                        res.getInt("ttl"));
            }

            res.close();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return actions;
    }
}