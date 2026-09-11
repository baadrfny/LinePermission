package ma.youcode.lineperm.service;

import ma.youcode.lineperm.access.ControleAcces;
import ma.youcode.lineperm.model.FichierProtege;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {

    private static final String FILES_DB = "files.txt";
    private static final String DATA_DIR = "data";

    public static void listFiles(String currentUser) {
        File file = new File(FILES_DB);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 3);
                if (parts.length == 3) {
                    String fileName = parts[0];
                    String owner = parts[1];
                    String permissions = parts[2];
                    System.out.println(permissions + " " + owner + " " + fileName);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading files.");
        }
    }

    public static void createFile(String currentUser, String fileName) {
        if (fileName.contains("/") || fileName.contains("\\")) {
            System.out.println("Permission denied.");
            return;
        }

        Path path = Path.of(DATA_DIR, fileName);

        if (isFileNameTaken(fileName)) {
            System.out.println("Permission denied.");
            return;
        }

        try {
            Files.createDirectories(path.getParent());
            if (Files.exists(path)) {
                System.out.println("Permission denied.");
                return;
            }
            Files.createFile(path);
        } catch (IOException e) {
            System.out.println("Permission denied.");
            return;
        }

        String defaultPermission = "rwd|---";

        try (FileWriter fw = new FileWriter(FILES_DB, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(fileName + ":" + currentUser + ":" + defaultPermission);
            System.out.println("File created successfully.");

        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public static void readFile(String currentUser, String fileName) {
        FichierProtege fichier = findFileRecord(fileName);
        if (fichier == null) {
            System.out.println("Permission denied.");
            return;
        }

        if (!ControleAcces.estAutorise(currentUser, fichier, 'r')) {
            System.out.println("Permission denied.");
            return;
        }

        Path path = Path.of(DATA_DIR, fileName);
        try {
            if (Files.exists(path)) {
                String content = Files.readString(path);
                System.out.print(content);
            }
        } catch (IOException e) {
            System.out.println("Permission denied.");
        }
    }

    public static void editFile(String currentUser, String fileName) {
        FichierProtege fichier = findFileRecord(fileName);
        if (fichier == null) {
            System.out.println("Permission denied.");
            return;
        }

        if (!ControleAcces.estAutorise(currentUser, fichier, 'w')) {
            System.out.println("Permission denied.");
            return;
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
        }
    }

    public static void changePermission(String currentUser, String fileName, String permissionChange) {
        FichierProtege fichier = findFileRecord(fileName);
        if (fichier == null) {
            System.out.println("Permission denied.");
            return;
        }

        if (!currentUser.equals(fichier.getProprietaire())) {
            System.out.println("Permission denied.");
            return;
        }

        boolean isRemove = permissionChange.startsWith("-");
        char targetPerm = permissionChange.replace("-", "").charAt(0);

        String[] parts = fichier.getPermissions().split("\\|");
        String ownerPerm = parts[0];
        StringBuilder otherPerm = new StringBuilder(parts[1]);

        int index = -1;
        if (targetPerm == 'r') index = 0;
        else if (targetPerm == 'w') index = 1;
        else if (targetPerm == 'd') index = 2;

        if (index != -1) {
            if (isRemove) {
                otherPerm.setCharAt(index, '-');
            } else {
                otherPerm.setCharAt(index, targetPerm);
            }
        }

        String newFullPermission = ownerPerm + "|" + otherPerm.toString();
        updateFilePermissionsInDb(fileName, newFullPermission);
        System.out.println("Permissions updated successfully.");
    }

    private static FichierProtege findFileRecord(String fileName) {
        File file = new File(FILES_DB);
        if (!file.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 3);
                if (parts.length == 3 && parts[0].equals(fileName)) {
                    return new FichierProtege(parts[0], parts[1], parts[2]);
                }
            }
        } catch (IOException e) {
        }
        return null;
    }

    private static boolean isFileNameTaken(String fileName) {
        return findFileRecord(fileName) != null;
    }

    private static void updateFilePermissionsInDb(String fileName, String newPermissions) {
        File file = new File(FILES_DB);
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 3);
                if (parts.length == 3 && parts[0].equals(fileName)) {
                    lines.add(parts[0] + ":" + parts[1] + ":" + newPermissions);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, false))) {
            for (String l : lines) {
                pw.println(l);
            }
        } catch (IOException e) {
        }
    }
}