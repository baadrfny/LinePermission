package ma.youcode.lineperm.model;

public class User {

    private int id;
    private String username;
    private String passHach;

    public User(int id, String username, String passHach) {
        this.id = id;
        this.username = username;
        this.passHach = passHach;
    }

    public User(String username, String passHach) {
        this.username = username;
        this.passHach = passHach;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return passHach;
    }



}
