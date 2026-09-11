package ma.youcode.lineperm.model;

public class FichierProtege {
    private String nom;
    private String proprietaire;
    private String permissions;

    public FichierProtege(String nom, String proprietaire, String permissions) {
        this.nom = nom;
        this.proprietaire = proprietaire;
        this.permissions = permissions;
    }

    public FichierProtege(String nom, String proprietaire) {
        this(nom, proprietaire, "rwd|---");
    }

    public String getNom() {
        return nom;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public String getPermissions() {
        return permissions;
    }

    public String getPermissionProprietaire() {
        String[] parts = permissions.split("\\|");
        return parts.length > 0 ? parts[0] : "rwd";
    }

    public String getPermissionAutres() {
        String[] parts = permissions.split("\\|");
        return parts.length > 1 ? parts[1] : "---";
    }
}