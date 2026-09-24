package ma.youcode.lineperm.dao;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import ma.youcode.lineperm.dao.AbstractDao;

import ma.youcode.lineperm.model.User;


public class UserDao extends AbstractDao<User> {


    @Override 
    public void save(User entity){
        String sql = "INSERT INTO users (login,password) VALUES (? , ?)";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);

            prpr.setString(1, entity.getUsername());
            prpr.setString(2, entity.getPass());

            prpr.executeUpdate();
            prpr.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

     
    public User findByUsername(String username){
        String sql = "SELECT * FROM users where login = ?";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setString(1, username);
            ResultSet result = prpr.executeQuery();

            if (result.next()) {
                User user = new User(
                    result.getInt("id"),
                    result.getString("login"),
                    result.getString("password")
                );
                
                result.close();
                prpr.close();
                return user;
            }
            result.close();
            prpr.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override 
    public User findById(int id){
        
        String sql = "SELECT * FROM users where id = ?";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setInt(1, id);
            ResultSet result = prpr.executeQuery();

            if (result.next()) {
                User user = new User(
                    result.getInt("id"),
                    result.getString("login"),
                    result.getString("password")
                );

                result.close();
                prpr.close();
                return user;

            }
            result.close();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    public List<User> findAll(){

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM USERS";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet result = prpr.executeQuery();

            while (result.next()) {
                User user = new User(
                    result.getInt("id"),
                    result.getString("username"),
                    result.getString("password")
                );

                users.add(user);
            }
            result.close();
            prpr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }



    @Override 
    public void delete(int id){
        try {
            String sql = "DELETE FROM users where id = ?";
            PreparedStatement prpr = connection.prepareStatement(sql);
            prpr.setInt(1, id);
            prpr.executeUpdate();
            prpr.close();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
