package ma.youcode.lineperm.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ma.youcode.lineperm.model.*;
import java.util.stream.*;
import java.util.Map;

public class LogAnalyzer {

    private List<AccessLog> logs;

    public void logAction(String logEntry) {
        try (FileWriter writer = new FileWriter("access.log", true)) {
            writer.write(logEntry + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error writing log");
        }
    }

    public void loadLogs() {

        Path path = Path.of("access.log");

        try {
            if (!Files.exists(path)) {
                logs = new ArrayList<>();
                return;
            }

            logs = Files.lines(path).map(AccessLog::fromLine).toList();

            System.out.println("Logs loaded successfully!");

        } catch (Exception e) {
            System.out.println("Error : " + e);
            logs = new ArrayList<>();
        }

    }

    public long getTotalActions() {
        return logs.stream().count();
    }

    public long getDeniedAccessCount() {
        return logs.stream().filter(log -> log.getResultat().equals("REFUSE")).count();
    }

    public List<String> userDistincts(){
        return logs.stream().map(log -> log.getUtilisateur()).distinct().toList();
    }

    public Map<String , Long> actionUser(){
        return logs.stream().collect(Collectors.groupingBy(AccessLog::getUtilisateur , Collectors.counting()));
    }

    public List<String> topFichier() {
    return logs.stream()
            .collect(Collectors.groupingBy(AccessLog::getFichier, Collectors.counting()))
            .entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .limit(3)
            .map(Map.Entry::getKey)
            .toList();

}


public List<String> userRefused(String username){
    return logs.stream().filter(log -> log.getResultat().equals("REFUSE") && log.getUtilisateur().equals(username)).map(log -> log.getFichier()).toList();
}

public Optional<Map.Entry<String , Long>> mostUser(){
    return logs.stream().collect(Collectors.groupingBy(log -> log.getUtilisateur(), Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue());
}



    

}
