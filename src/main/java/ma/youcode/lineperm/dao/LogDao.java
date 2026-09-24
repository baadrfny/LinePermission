package ma.youcode.lineperm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import ma.youcode.lineperm.dao.AbstractDao;
import ma.youcode.lineperm.model.User;


public class LogDao extends AbstractDao {

    public int compterTotal(){
        String sql = "SELECT COUNT(*) FROM logs ";

        try {
            PreparedStatement prpr = connection.prepareStatement(sql);
            ResultSet res = prpr.executeQuery();

            if (res.next()) {
                return res.getInt(1);
            }

            prpr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }



    
}