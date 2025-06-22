package org.example.Model;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Aspect
public class LoggingAspect {
    private static final String LOG_FILE = "application.log";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @After("@annotation(Log)")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String parameters = formatParameters(joinPoint.getArgs());
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        
        String logEntry = String.format("\"%s\", \"%s\", \"%s\"", methodName, parameters, timestamp);
        
        writeToLogFile(logEntry);
    }
    
    private String formatParameters(Object[] args) {
        if (args == null || args.length == 0) {
            return "sin parametros";
        }
        
        return Arrays.stream(args)
                .map(arg -> arg != null ? arg.toString() : "null")
                .reduce((a, b) -> a + "|" + b)
                .orElse("sin parametros");
    }
    
    private void writeToLogFile(String logEntry) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }
} 