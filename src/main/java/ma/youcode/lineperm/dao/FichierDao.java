package ma.youcode.lineperm.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ma.youcode.lineperm.model.FichierProtege;

public class FichierDao extends AbstractDao<FichierProtege> {


    public void save(FichierProtege entity){ 
        String sql = "INSERT INTO fichiers (nom, proprietaire_id, permissions) VALUES (?,?,?)";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setString(1, entity.getNom());
            prpr.setInt(2, entity.getProprietaireId());
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

            if (res.next()) {
                FichierProtege fichier = new FichierProtege(
                    res.getInt("id"),
                    res.getString("nom"),
                    res.getInt("proprietaire_id"),
                    res.getString("permissions")
                );

                res.close();
                prpr.close();
                return fichier;
            }

            res.close();
            prpr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public void delete(int id){
        String sql = "DELETE FROM fichiers where id = ?";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setInt(1, id);
            prpr.executeUpdate();
            prpr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<FichierProtege> findByPropietaire(int proprietaire_id){
        List<FichierProtege> fichiers = new ArrayList<>();

        String sql = "SELECT * FROM fichiers where proprietaire_id = ?";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setInt(1, proprietaire_id);
            ResultSet res = prpr.executeQuery();

            while (res.next()) {
                FichierProtege fichier = new FichierProtege(
                    res.getInt("id"),
                    res.getString("nom"),
                    res.getInt("proprietaire_id"),
                    res.getString("permissions")
                );
                fichiers.add(fichier);
            }

            res.close();
            prpr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fichiers;
    }


    public void updatePermission(int id , String permissions){
        String sql = "UPDATE fichiers SET permissions = ? where id = ?";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setString(1, permissions);
            prpr.setInt(2, id);
            prpr.executeUpdate();
            prpr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}