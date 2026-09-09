package ma.youcode.lineperm.service;

import java.util.HashMap;
import ma.youcode.lineperm.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;



public class UserService {

    
    private HashMap<String, User> comptes = new HashMap<>();


    public UserService() {
        loadUsers();
    }

    private void loadUsers() {
        File file = new File("src/main/resources/users.txt");
        if (!file.exists()) {
            return;
        }

        //FileReader to open the file and BufferedReader to read it line by line
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            //readline to read it line by line
            while ((line = br.readLine()) != null) {
                //split the line by ":" to get the username and password
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0];
                    String passHash = parts[1];
                    User user = new User(username, passHash);
                    comptes.put(username, user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }



    public boolean exists(String username){

        return comptes.containsKey(username);
    }


    public boolean createAccount(String username , String passHach){
        if(exists(username)){
            return false;
            
        }else{
            
            String passHash = BCrypt.hashpw(passHach , BCrypt.gensalt());
            User user = new User(username , passHash);
            comptes.put(username , user);

            saveUser(username, passHash);
            return true;
            
        }
    }


    public boolean authenticate(String username , String passHach){
        if(!exists(username)){
            return false;
        }else{
            User user = comptes.get(username);
            if(BCrypt.checkpw(passHach , user.getPass())){
                return true;
            }else{
                return false;
            }
        }
    }


    public boolean saveUser(String username , String passHach){
        try (FileWriter fw = new FileWriter("src/main/resources/users.txt", true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.println(username + ":" + passHach);
            out.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }



}