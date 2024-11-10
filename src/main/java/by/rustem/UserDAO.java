package by.rustem;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private static final String FILE_NAME = "users.dat";
    private Map<String, User> users = new HashMap<>();

    public UserDAO(){
        loadUsers();
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))){
            users = (Map<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Ошибка загрузки данных пользователей: " + e.getMessage());
        }
    }

    public void saveUsers(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))){
            oos.writeObject(users);
        } catch (IOException e){
            System.out.println("Ошибка сохранения данных пользователей: " + e.getMessage());
        }
    }

    public User getUser(String username, User user){
        return users.get(username);
    }

    public void addUser(String username, User user){
        users.put(username, user);
        saveUsers();
    }

    public void updateUser(String username, User user){
        users.put(username, user);
        saveUsers();
    }

    public void deleteUser(String username, User user){
        users.remove(username);
        saveUsers();
    }
}
