package ma.youcode.lineperm.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.mindrot.jbcrypt.BCrypt;

import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.access.ControleAcces;
import ma.youcode.lineperm.model.FichierProtege;

public class UserService {

    private HashMap<String, User> comptes = new HashMap<>();

    public UserService() {
        loadUsers();
    }

    private void loadUsers() {
        File file = new File("users.txt");
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
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

    public boolean exists(String username) {
        return comptes.containsKey(username);
    }

    public boolean createAccount(String username, String passHach) {
        if (exists(username)) {
            return false;
        } else {
            String passHash = BCrypt.hashpw(passHach, BCrypt.gensalt());
            User user = new User(username, passHash);
            comptes.put(username, user);
            saveUser(username, passHash);
            return true;
        }
    }

    public boolean authenticate(String username, String passHach) {
        if (!exists(username)) {
            return false;
        } else {
            User user = comptes.get(username);
            if (BCrypt.checkpw(passHach, user.getPass())) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean saveUser(String username, String passHach) {
        try (FileWriter fw = new FileWriter("users.txt", true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(username + ":" + passHach);
            out.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String ownerCurrentUser() {
        String currentUser = System.getProperty("user.name");
        return currentUser;
    }

    public static boolean checkPermission(String fileOwner, String currentUser, String permissions, char action) {
        FichierProtege fichierProtege = new FichierProtege("", fileOwner, permissions);
        return ControleAcces.estAutorise(currentUser, fichierProtege, action);
    }
}