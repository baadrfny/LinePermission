package ma.youcode.lineperm.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import ma.youcode.lineperm.dao.AbstractDao;
import ma.youcode.lineperm.model.User;


public class LogDao extends AbstractDao {

    public int compterTotal(){
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


    public int compterRefuse(){
        String sql = "SELECT COUNT(*) FROM logs where status = 'refuse'";

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


    public List<String> userDistincts(){
        List<String> users = new ArrayList<>();
        String sql = "SELECT DISTINCT u.login FROM logs l JOIN users u ON l.user_id = u.id";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();
            while (res.next()) {
                users.add(res.getString("username"));
                res.close();
                prpr.close();
                
            }
            res.close();
            prpr.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return users;
    }


    public List<String> actionUser(){
        String sql = "SELECT DISTINCT u.login FROM users u JOIN logs l ON u.id = l.user_id";
        List<String> logs = new ArrayList<>();

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();
            while (res.next()) {
                logs.add(res.getString("username"));
                res.close();
                prpr.close();
            }
            res.close();
            prpr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;

    }

    public List<String> topFichiers(){
        List<String> top = new ArrayList<>();
        String sql = "SELECT DISTINCT f.nom FROM fichiers f JOIN logs l ON l.fichier_id = f.id";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();
            while (res.next()) {
                top.add(res.getString("nom"));
                res.close();
                prpr.close();
    
            }

            res.close();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return top;
    }


    public String mostUser(){
        // List<User> users = new ArrayList<>();
        String sql = "SELECT u.login, COUNT(*) AS total FROM users u JOIN logs l ON l.user_id = u.id GROUP BY u.id, u.login ORDER BY total DESC LIMIT 1";
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
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<String> refuseByUser(){
        
        String sql = "SELECT DISTINCT u.login FROM users u JOIN logs l ON l.user_id = u.id where resultat = 'REFUSE' ";
        List<String> users = new ArrayList<>();
        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();
            while (res.next()) {
                
                users.add(res.getString("login"));
            }
            res.close();
            prpr.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public Map<String, Integer> repartitionByAction(){
        String sql = "SELECT action , COUNT(action) AS ttl FROM logs GROUP BY action ORDER BY ttl DESC";
        Map<String,Integer> actions = new HashMap<>();

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();
            while (res.next()) {
                actions.put(res.getString("action"), res.getInt("ttl"));
            }
            res.close();
            prpr.close();
            return actions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return actions;
    }



    
}