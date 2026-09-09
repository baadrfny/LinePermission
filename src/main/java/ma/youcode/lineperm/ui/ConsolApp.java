package ma.youcode.lineperm.ui;

import ma.youcode.lineperm.service.UserService;
import java.util.Scanner;



public class ConsolApp {

    UserService  userService = new UserService();
    Scanner scanner = new Scanner(System.in);

    

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
            System.out.println("Your Auth is successfully");
        }else{
            System.out.println("Your Auth is failed");
        }

    }
}