package ma.youcode.lineperm.model;

public class FichierProtege {
    private int id;
    private String nom;
    private int proprietaireId;
    private String permissions;

    public FichierProtege(int id , String nom, int proprietaireId, String permissions) {
        this.id = id;
        this.nom = nom;
        this.proprietaireId = proprietaireId;
        this.permissions = permissions;
    }

    public FichierProtege(int id ,String nom, int proprietaireId) {
        this(id, nom, proprietaireId, "rwd|---");
    }

    public Integer getId(){
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getProprietaireId() {
        return proprietaireId;
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