package by.rustem;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AccountDAO {
    private static final String FILE_NAME = "accounts.dat";
    private Map<String, Account> accounts = new HashMap<>();

    public AccountDAO() {
        loadAccounts();
    }

    private void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            accounts = (Map<String, Account>) ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Ошибка загрузки данных аккаунтов: " + e.getMessage());
            if (e instanceof FileNotFoundException) {
                accounts = new HashMap<>();
            }
        }
    }

    public void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Ошибка сохранения данных аккаунтов: " + e.getMessage());
        }
    }

    public Account getAccount(String accountId){
        return accounts.get(accountId);
    }

    public void addAccount(String accountId, Account account) {
        accounts.put(accountId, account);
        saveAccounts();
    }

    public void updateAccount(String accountId, Account account){
        accounts.put(accountId, account);
        saveAccounts();
    }

    public void deleteAccount(String accountId){
        accounts.remove(accountId);
        saveAccounts();
    }
}
