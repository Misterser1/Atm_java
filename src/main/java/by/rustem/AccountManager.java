package by.rustem;

public interface AccountManager {

    //Проверка баланса
    void checkBalance();
    //Внос денег на счет
    void deposit(double amount);
    //Снимает деньги со счета (проверяя баланс)
    void withdraw(double amount);
    //Перевод денег на другой счет
    void transferFunds(String targetAccount, double amount);
    //Изменение PIN кода
    void changePin(int oldPin, int newPin);
}
