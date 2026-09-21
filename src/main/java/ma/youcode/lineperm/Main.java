package ma.youcode.lineperm;
import ma.youcode.lineperm.service.*;
import ma.youcode.lineperm.ui.ConsolApp;
import ma.youcode.lineperm.database.DBConnection;
import ma.youcode.lineperm.model.*;
import ma.youcode.lineperm.database.DatabaseInitializer;


import java.io.Console;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    
    
    public static void main(String[] args) {

        System.out.println("trying to connect");
        DBConnection.getInstance();

        DatabaseInitializer.initialize();

        


        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.loadLogs();

        
        System.out.println("Please choose an option:");
        System.out.println("1. Option A (Sign Up)");
        System.out.println("2. Option B (Log In)");
        System.out.println("3. Option C (Exit)");
        System.out.println("4. Option D (Total Actions)");
        System.out.println("5. Option E (Total of refuse)");
        System.out.println("6. Option F (Distincts Users)");
        System.out.println("7. Option G (Total Actions od every User)");
        System.out.println("8. Option H (Top 3 files)");
        System.out.println("9. Option I (Refused Users)");
        System.out.println("10. Option J (Most active user)");
        System.out.println("11. Option K (Action distributios with type)");
        System.out.print("Enter your choice : ");

        int choice = new Scanner(System.in).nextInt();

        

        switch (choice) {
            case 1:
                ConsolApp app = new ConsolApp(analyzer);
                app.start();
                break;
            case 2:
                ConsolApp app2 = new ConsolApp(analyzer);
                app2.login();
                break;
            case 3:
                System.out.println("Exiting the application.");
                System.exit(0);
                break;
            case 4:
                System.out.println("Total actions :" + analyzer.getTotalActions());
                break;
            case 5:
                System.out.println("Total refuse :" + analyzer.getDeniedAccessCount());
                break;
            case 6:
                System.out.println("Users Distincts :" + analyzer.userDistincts());
                break;
            case 7 :
                System.out.println("Total Actions of every user is : " + analyzer.actionUser());
                break;
            case 8 : System.out.println("Top 3 files : " + analyzer.topFichier());
                    break;
            case 9 :
                System.out.print("Enter username : ");
                String username = scanner.nextLine();
                System.out.println("Access refuse for this user is : " + analyzer.userRefused(username));
                break;
            case 10 : 
                System.out.println("Most active user : " + analyzer.mostUser());
                break;
            case 11 : 
                System.out.println("Action distributios with type : " + analyzer.actionWithType());
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                
        }
    }
    
}
