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

import ma.youcode.lineperm.dao.LogDao;
import ma.youcode.lineperm.model.*;
import java.util.stream.*;
import java.util.Map;

public class LogAnalyzer {

    private List<AccessLog> logs;
    LogDao logDao = new LogDao();

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
        return logDao.compterTotal();
    }

    public long getDeniedAccessCount() {
        return logDao.compterRefuse();
    }

    public List<String> userDistincts(){
        return logDao.userDistincts();
    }

    public Map<String , Long> actionUser(){
        return logDao.actionUser();
    }

    public List<String> topFichier() {
    return logDao.topFichiers();

}


public List<String> userRefused(String username){
    return logDao.refuseByUser();
}

public String mostUser(){
    return logDao.mostUser();
}

public Map<String , Integer> actionWithType(){
    return logDao.repartitionByAction();
} 


    

}
