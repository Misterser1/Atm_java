package by.rustem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleTransactionLogger implements TransactionLogger {

    @Override
    public void logTransaction(String transactionDetails) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("transaction.log", true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = LocalDateTime.now().format(formatter);
            writer.write(formattedTime + " - " + transactionDetails);
            writer.newLine();
        }catch (IOException e){
            System.out.println("Ошибка при записи в лог-файл" + e.getMessage());
        }
    }
}
