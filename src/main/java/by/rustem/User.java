package by.rustem;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String encryptedPassword;

    public User(String username, String encryptedPassword){
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEncryptedPassword(){
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword){
        this.encryptedPassword = encryptedPassword;
    }
}
