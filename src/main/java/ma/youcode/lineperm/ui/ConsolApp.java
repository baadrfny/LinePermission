package ma.youcode.lineperm.ui;

import ma.youcode.lineperm.service.UserService;
import java.util.Scanner;



public class ConsolApp {

    UserService  userService = new UserService();
    Scanner scanner = new Scanner(System.in);
    String currentUser = null;

    

    public void start(){

        System.out.println("============ Sign Up To LineAPermission App ==========");

        
        System.out.print("Pls Entre ur username : ");
        String UserName = scanner.nextLine();

        System.out.print("Pls Entre ur password : ");
        String PassWord = scanner.nextLine();


        boolean created = userService.createAccount(UserName , PassWord);

        if (created) {
            System.out.println("Sign up Successfully");
        }else{
            System.out.println("Failed Sign up");
        }

    }



    public void login(){

        System.out.println("============ Login To LineAPermission App ==========");

        System.out.println("Entrer ur username : ");
        String UserName = scanner.nextLine();

        System.out.println("Entrer ur password : ");
        String PassWord = scanner.nextLine();


        boolean logged = userService.authenticate(UserName, PassWord);
        if (logged) {
            currentUser = UserName;
            System.out.println("Your Auth is successfully");
            showDashboard();

        }else{
            System.out.println("Your Auth is failed");
        }

    }


    public void showDashboard(){

        boolean sessionActive = true;

        while(sessionActive){
            System.out.println("\n========================================");
            System.out.println("      WELCOME TO YOUR DASHBOARD         ");
            System.out.println("      Logged in as: " + currentUser     );
            System.out.println("========================================");
            System.out.println("1. Log Out");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    sessionActive = false;
                    currentUser = null;
                    System.out.println("Logged out successfully");
                    break;
                default:
                    System.out.println("Invalid choice Pls try again");
            }
        }
        
    }


    public void logout() {
        System.out.println(">> User " + currentUser + " has been logged out successfully");
        currentUser = null;
    }
    //
}