package ma.youcode.lineperm.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import ma.youcode.lineperm.dao.UserDao;

import org.mindrot.jbcrypt.BCrypt;

import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.access.ControleAcces;
import ma.youcode.lineperm.model.FichierProtege;

public class UserService {

    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public boolean exists(String username) {
        return userDao.findByUsername(username) != null;
    }

    public boolean createAccount(String username, String passHach) {
        if (exists(username)) {
            return false;
        } else {
            String passHash = BCrypt.hashpw(passHach, BCrypt.gensalt());
            User user = new User(username, passHash);
            userDao.save(user);
            return true;
        }
    }

    public boolean authenticate(String username, String passHach) {
        User user = userDao.findByUsername(username);

        if (user == null) {
            return false;
        }

        return BCrypt.checkpw(passHach, user.getPass());
    }


    

    public static String ownerCurrentUser() {
    String currentUser = System.getProperty("user.name");
    return currentUser;
    }

    // public static boolean checkPermission(String fileOwner, String currentUser,
    // String permissions, char action) {
    // FichierProtege fichierProtege = new FichierProtege("", fileOwner,
    // permissions);
    // return ControleAcces.estAutorise(currentUser, fichierProtege, action);
    // }
}