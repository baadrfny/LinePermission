package ma.youcode.lineperm.ui;

import ma.youcode.lineperm.service.FileService;
import ma.youcode.lineperm.service.UserService;
import java.util.Scanner;

public class ConsolApp {

    UserService userService = new UserService();
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
            showShell();
        }else{
            System.out.println("Your Auth is failed");
        }
    }

    public void showShell(){
        boolean sessionActive = true;

        System.out.println("\n========================================");
        System.out.println("       WELCOME TO LINEPERM SHELL         ");
        System.out.println("      Logged in as: " + currentUser     );
        System.out.println("========================================");

        while(sessionActive){
            System.out.print(currentUser + "@linperm>");
            String commandInput = scanner.nextLine().trim();

            String[] parts = commandInput.split("\\s+", 2);
            String command = parts[0];
            String arg = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "ls":
                    if (arg.equals("-l")) {
                        FileService.listFiles(currentUser);
                    } else {
                        System.out.println("Invalid command. try : ls -l");
                    }
                    break;
                case "touch":
                    if (!arg.isEmpty()) {
                        FileService.createFile(currentUser, arg);
                    } else {
                        System.out.println("Invalid command. try : touch <filename>");
                    }
                    break;
                case "nano":
                    if (!arg.isEmpty()) {
                        FileService.editFile(currentUser, arg);
                    } else {
                        System.out.println("Invalid command try : nano <filename>");
                    }
                    break;
                case "cat":
                    if (!arg.isEmpty()) {
                        FileService.readFile(currentUser, arg);
                    } else {
                        System.out.println("Invalid command try : cat <filename>");
                    }
                    break;
                case "rm":
                    if (!arg.isEmpty()) {
                        FileService.deleteFile(currentUser, arg);
                    } else {
                        System.out.println("Invalid command try : rm <filename>");
                    }
                    break;
                case "chmod":
                    String[] chmodParts = arg.split("\\s+", 2);
                    if (chmodParts.length == 2) {
                        FileService.changePermission(currentUser, chmodParts[1], chmodParts[0]);
                    } else {
                        System.out.println("Invalid command try : chmod <+/-perm> <filename>");
                    }
                    break;
                case "help":
                    System.out.println("Available commands:");
                    System.out.println("  ls -l            : List files with details");
                    System.out.println("  touch <filename> : Create a new file");
                    System.out.println("  nano <filename>  : Edit file");
                    System.out.println("  cat <filename>   : Read file");
                    System.out.println("  chmod <perm> <file>: Change permissions");
                    System.out.println("  rm <filename>    : Remove a file");
                    System.out.println("  help             : Show this help message");
                    System.out.println("  logout           : Log out of the application");
                    break;
                case "logout":
                    logout();
                    sessionActive = false;
                    
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

}