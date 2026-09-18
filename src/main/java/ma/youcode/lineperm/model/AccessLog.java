package ma.youcode.lineperm.model;

public class AccessLog {
    
    private String date;
    private  String heure;
    private String utilisateur;
    private String fichier;
    private String action; 
    private String resultat;

    public AccessLog(String date, String heure, String utilisateur,  String action, String fichier, String resultat) {
        this.date = date;
        this.heure = heure;
        this.utilisateur = utilisateur;
        this.fichier = fichier;
        this.action = action;
        this.resultat = resultat;
    }

    

    public String getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public String getFichier() {
        return fichier;
    }

    public String getAction() {
        return action;
    }

    public String getResultat() {
        return resultat;
    }


    public static AccessLog fromLine(String line){
        String[]  parts = line.split(";");
        if (parts.length < 6 ) return null;
        return new AccessLog(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }
    
}
