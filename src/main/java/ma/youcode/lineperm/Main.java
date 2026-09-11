package ma.youcode.lineperm;
import ma.youcode.lineperm.service.UserService;
import ma.youcode.lineperm.ui.ConsolApp;

import java.io.Console;
import java.util.Scanner;

public class Main {

    Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {


        // UserService file = new UserService();
        // file.createFile();

        
        System.out.println("Please choose an option:");
        System.out.println("1. Option A (Sign Up)");
        System.out.println("2. Option B (Log In)");
        System.out.println("3. Option C (Exit)");
        System.out.print("Enter your choice (1-3): ");

        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            case 1:
                ConsolApp app = new ConsolApp();
                app.start();
                break;
            case 2:
                ConsolApp app2 = new ConsolApp();
                app2.login();
                break;
            case 3:
                System.out.println("Exiting the application.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
}
