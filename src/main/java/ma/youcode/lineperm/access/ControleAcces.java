package ma.youcode.lineperm.access;

import ma.youcode.lineperm.dao.UserDao;
import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.User;

public class ControleAcces {

    private static UserDao userDao = new UserDao();

    public static boolean estAutorise(String currentUser, FichierProtege fichier, char action) {

        if (currentUser == null || fichier == null) {
            return false;
        }

        User owner = userDao.findById(fichier.getProprietaireId());

        if (owner == null) {
            return false;
        }

        boolean isOwner = currentUser.equals(owner.getUsername());

        String activePermission = isOwner
                ? fichier.getPermissionProprietaire()
                : fichier.getPermissionAutres();

        int index = -1;

        if (action == 'r') {
            index = 0;
        } else if (action == 'w') {
            index = 1;
        } else if (action == 'd') {
            index = 2;
        }

        if (index == -1 || index >= activePermission.length()) {
            return false;
        }

        return activePermission.charAt(index) != '-';
    }
}