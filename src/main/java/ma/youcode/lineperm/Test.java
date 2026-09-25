package ma.youcode.lineperm;

import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.dao.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import ma.youcode.lineperm.model.FichierProtege;
public class Test {
    public static void main(String[] args){
        
        UserDao userDao = new UserDao();
        User user = userDao.findByUsername("badr");
        User user2 = userDao.findById(4);
        List<User> users = userDao.findAll();
        
        // userDao.delete(2);
        System.out.println("=========  User :  ==============");
        System.out.println("Find by Username: " + user.getUsername());
        System.out.println("Find by id : "+ user2.getUsername());
        System.out.println("Find all users : ");
        for(int i = 0 ; i < users.size() ; i ++){
            System.out.println("user : " + users.get(i).getUsername());
        }
        

        System.out.println("============  Fichiers  ===========");

        FichierDao fichierDao = new FichierDao();

        // FichierProtege fichier = new FichierProtege(
        //     0,
        //     "fichierFinal.txt",
        //     3,
        //     "rwd|---"
        // );

        // boolean result = fichierDao.save(fichier);

        // System.out.println("Fichier saved : " + result);
        

        // FichierDao fichier = new FichierDao();
        FichierProtege fichier = fichierDao.findById(2);
        System.out.println("Fichier founded : " + fichier.getNom());
        // boolean delFichier = fichierDao.delete(3);
        // System.out.println("File deleted : " + delFichier);

        
        LogDao log = new LogDao();
        List<String> usero = log.refuseByUser();
        
        for (String usr : usero) {
            System.out.println("user : " + usr);
        }


        
        
        Map<String, Integer> actions = log.repartitionByAction();
        System.out.println(actions);


        }




}
