package ma.youcode.lineperm.service;
import java.util.HashMap;
import ma.youcode.lineperm.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    
    private HashMap<String, User> comptes = new HashMap<>();

    public boolean exists(String username){

        return comptes.containsKey(username);
    }


    public void createAccount(String username , String passHach){
        if(exists(username)){
            System.out.println("Account already exists");
            
        }else{
            String passHash = BCrypt.hashpw(passHach , BCrypt.gensalt());
            User user = new User(username , passHash);
            comptes.put(username , user);
            System.out.println("Account created successfully");
            
        }
    }

}