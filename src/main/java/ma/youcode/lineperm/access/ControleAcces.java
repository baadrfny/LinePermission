package ma.youcode.lineperm.access;

import ma.youcode.lineperm.model.FichierProtege;

public class ControleAcces {

    public static boolean estAutorise(String currentUser, FichierProtege fichier, char action) {
        if (currentUser == null || fichier == null) {
            return false;
        }

        boolean isOwner = currentUser.equals(fichier.getProprietaire());
        String activePermission = isOwner ? fichier.getPermissionProprietaire() : fichier.getPermissionAutres();

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