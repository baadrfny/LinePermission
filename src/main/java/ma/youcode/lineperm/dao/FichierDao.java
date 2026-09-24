package ma.youcode.lineperm.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ma.youcode.lineperm.model.FichierProtege;

public class FichierDao extends AbstractDao{


    public void save(FichierProtege entity){ 
        String sql = "INSERT INTO fichiers (nom, proprietaire_id, permissions) VALUES (?,?,?)";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setString(1, entity.getNom());
            prpr.setString(2, entity.getProprietaire());
            prpr.setString(3, entity.getPermissions());
            prpr.executeUpdate();
            prpr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public FichierProtege findById(int id){
        String sql = "SELECT * FROM fichiers where id = ?";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setInt(1, id);
            ResultSet res = prpr.executeQuery();
            prpr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public void delete(int id){
        String sql = "DELETE fichiers where id = ?";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setInt(1, id);
            ResultSet res = prpr.executeQuery();
            prpr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public FichierProtege findByPropietaire(int proprietaire_id){
        String sql = "SELECT * FROM fichiers where proprietaire_id = ?";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setInt(1, proprietaire_id);
            ResultSet res = prpr.executeQuery();

            if (res.next()) {
                res.getInt(proprietaire_id);
            }
            prpr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public void updatePermission(int id , String permissions){
        String sql = " UPDATE fichiers SET permissions = ?  where id = ?";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setInt(1, id);
            prpr.setString(2, permissions);
            prpr.executeUpdate();
            prpr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
