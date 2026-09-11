package ma.youcode.lineperm.model;

public class VirtualFile {
    private String name;
    private String owner;
    private String permissions;

    public VirtualFile(String name, String owner, String permissions) {
        this.name = name;
        this.owner = owner;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
    
    public String getPermissions() {
        return permissions;
    }
}
