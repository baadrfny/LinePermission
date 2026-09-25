package ma.youcode.lineperm.service;

import ma.youcode.lineperm.access.ControleAcces;
import ma.youcode.lineperm.dao.FichierDao;
import ma.youcode.lineperm.dao.UserDao;
import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {

    private static final String DATA_DIR = "data";
    private static FichierDao fichierDao = new FichierDao();
    private static UserDao userDao = new UserDao();

    public static void listFiles(String currentUser) {

        User user = userDao.findByUsername(currentUser);

        if (user == null) {
            return;
        }

        List<FichierProtege> fichiers = fichierDao.findByPropietaire(user.getId());

        for (FichierProtege fichier : fichiers) {
            System.out.println(
                    fichier.getPermissions() + " " +
                            currentUser + " " +
                            fichier.getNom());
        }
    }

    public static boolean createFile(String currentUser, String fileName) {
        if (fileName.contains("/") || fileName.contains("\\")) {
            System.out.println("Permission denied.");
            return false;
        }

        Path path = Path.of(DATA_DIR, fileName);

        if (isFileNameTaken(fileName)) {
            System.out.println("Permission denied.");
            return false;
        }

        try {
            Files.createDirectories(path.getParent());
            if (Files.exists(path)) {
                System.out.println("Permission denied.");
                return false;
            }
            Files.createFile(path);
        } catch (IOException e) {
            System.out.println("Permission denied.");
            return false;
        }

        String defaultPermission = "rwd|---";

        User user = userDao.findByUsername(currentUser);

        if (user == null) {
            return false;
        }

        FichierProtege fichier = new FichierProtege(0, fileName, user.getId(), defaultPermission);
        fichierDao.save(fichier);
        System.out.println("File created seccessfuly");
        return true;
    }

    private static FichierProtege findFileRecord(String fileName) {
        return fichierDao.findByName(fileName);

    }

    public static boolean readFile(String currentUser, String fileName) {

        FichierProtege fichier = findFileRecord(fileName);
        if (fichier == null) {
            System.out.println("Permission denied.");
            return false;
        }

        if (!ControleAcces.estAutorise(currentUser, fichier, 'r')) {
            System.out.println("Permission denied.");
            return false;
        }

        Path path = Path.of(DATA_DIR, fileName);
        try {
            if (Files.exists(path)) {
                String content = Files.readString(path);
                System.out.print(content);
                return true;
            }
        } catch (IOException e) {
            System.out.println("Permission denied.");
        }

        return false;
    }

    public static boolean editFile(String currentUser, String fileName) {
        FichierProtege fichier = findFileRecord(fileName);
        if (fichier == null) {
            System.out.println("Permission denied.");
            return false;
        }

        if (!ControleAcces.estAutorise(currentUser, fichier, 'w')) {
            System.out.println("Permission denied.");
            return false;
        }

        boolean canRead = ControleAcces.estAutorise(currentUser, fichier, 'r');
        Path dataPath = Path.of(DATA_DIR, fileName);

        if (canRead) {
            try {
                if (Files.exists(dataPath) && Files.size(dataPath) > 0) {
                    System.out.println(Files.readString(dataPath));
                }
            } catch (IOException e) {
            }
        }

        System.out.println("Enter content (type EOF on a new line to finish):");
        Scanner scanner = new Scanner(System.in);
        StringBuilder content = new StringBuilder();

        while (true) {
            String line = scanner.nextLine();
            if (line.equals("EOF")) {
                break;
            }
            content.append(line).append("\n");
        }

        try {
            Files.createDirectories(dataPath.getParent());
            Files.writeString(dataPath, content.toString());
            System.out.println("File saved successfully");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean changePermission(String currentUser, String fileName, String permissionChange) {

        FichierProtege fichier = findFileRecord(fileName);

        if (fichier == null) {
            System.out.println("File not found");
            return false;
        }

        User owner = userDao.findById(fichier.getProprietaireId());

        if (owner == null || !currentUser.equals(owner.getUsername())) {
            System.out.println("Permission denied");
            return false;
        }

        if (permissionChange == null || permissionChange.length() != 3) {
            System.out.println("Invalid permission");
            return false;
        }

        for (int i = 0; i < 3; i++) {
            char c = permissionChange.charAt(i);

            if (i == 0 && c != 'r' && c != '-') {
                return false;
            }

            if (i == 1 && c != 'w' && c != '-') {
                return false;
            }

            if (i == 2 && c != 'd' && c != '-') {
                return false;
            }
        }

        String[] parts = fichier.getPermissions().split("\\|", 2);

        String newFullPermission = parts[0] + "|" + permissionChange;

        fichierDao.updatePermission(fichier.getId(), newFullPermission);

        System.out.println("Permissions updated successfully.");
        return true;
    }

    private static boolean isFileNameTaken(String fileName) {
        return findFileRecord(fileName) != null;
    }

    public static boolean deleteFile(String currentUser, String fileName) {
        FichierProtege fichier = findFileRecord(fileName);

        if (fichier == null) {
            System.out.println("Permission denied.");
            return false;
        }

        User owner = userDao.findById(fichier.getProprietaireId());

        if (owner == null || !currentUser.equals(owner.getUsername())) {
            System.out.println("Permission denied.");
            return false;
        }

        Path path = Path.of(DATA_DIR, fileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.println("Permission denied.");
            return false;
        }

        fichierDao.delete(fichier.getId());
        System.out.println("File removed successfully.");
        return true;
    }

    // private static void removeFileRecordFromDb(String fileName) {
    // File file = new File(FILES_DB);
    // List<String> lines = new ArrayList<>();

    // try (BufferedReader br = new BufferedReader(new FileReader(file))) {
    // String line;
    // while ((line = br.readLine()) != null) {
    // String[] parts = line.split(":", 3);
    // if (parts.length == 3 && parts[0].equals(fileName)) {
    // continue;
    // }
    // lines.add(line);
    // }
    // } catch (IOException e) {
    // return;
    // }

    // try (PrintWriter pw = new PrintWriter(new FileWriter(file, false))) {
    // for (String l : lines) {
    // pw.println(l);
    // }
    // } catch (IOException e) {
    // }
    // }

    // private static void updateFilePermissionsInDb(String fileName, String
    // newPermissions) {
    // File file = new File(FILES_DB);
    // List<String> lines = new ArrayList<>();

    // try (BufferedReader br = new BufferedReader(new FileReader(file))) {
    // String line;
    // while ((line = br.readLine()) != null) {
    // String[] parts = line.split(":", 3);
    // if (parts.length == 3 && parts[0].equals(fileName)) {
    // lines.add(parts[0] + ":" + parts[1] + ":" + newPermissions);
    // } else {
    // lines.add(line);
    // }
    // }
    // } catch (IOException e) {
    // return;
    // }

    // try (PrintWriter pw = new PrintWriter(new FileWriter(file, false))) {
    // for (String l : lines) {
    // pw.println(l);
    // }
    // } catch (IOException e) {
    // }
    // }
}