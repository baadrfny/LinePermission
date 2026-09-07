package ma.youcode.lineperm;
import ma.youcode.lineperm.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

        userService.createAccount("badr", "password123");

        userService.createAccount("badr", "password123");

        userService.createAccount("ali", "ali2026");
    }
}
