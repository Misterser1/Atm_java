package by.rustem;

import javax.crypto.SecretKey;

public class SimpleAccountManager implements AccountManager{
    private double balance = 5000;
    private String encryptedPin;
    private TransactionLogger transactionLogger;

    private static final SecretKey secretKey = SimplePinEncryptionUtil.generateSecretKey();

    public SimpleAccountManager(){
        this.transactionLogger = new SimpleTransactionLogger();
        this.encryptedPin = SimplePinEncryptionUtil.encryptPin("1234", secretKey);
    }

    public SimpleAccountManager(TransactionLogger transactionLogger) {
        this.transactionLogger = transactionLogger;
        this.encryptedPin = SimplePinEncryptionUtil.encryptPin("1234", secretKey);
    }

    @Override
    public synchronized void checkBalance() {
        System.out.println("Текущий баланс: " + balance);
    }

    @Override
    public synchronized void deposit(double amount) {
        if(amount <= 0) {
            System.out.println("Сумма пополнения должна быть положительной!");
        }else {
            balance += amount;
            System.out.println("Пополнение прошло успешно!");
            transactionLogger.logTransaction("Пополнение счета на " + amount + " рублей");
        }
    }

    @Override
    public synchronized void withdraw(double amount) {
        if(amount <= 0){
            System.out.println("Сумма снятия должна быть положительной!");
            transactionLogger.logTransaction("Попытка снятия с неверной суммой: " + amount);
        }
        else if(amount > balance){
            System.out.println("На балансе недостаточно средств!");
            transactionLogger.logTransaction("Попытка снятия " + amount + " рублей с недостаточным балансом.");
        }else{
            balance -= amount;
            System.out.println("Снятие прошло успешно!");
            System.out.println("Заберите ваши деньги");
            transactionLogger.logTransaction("Снятие " + amount + " рублей");
        }
    }

    @Override
    public synchronized void transferFunds(String targetAccount, double amount) {
        if(amount <= 0){
            System.out.println("Сумма перевода должна быть положительной");
        }
        else if (amount > balance){
            System.out.println("На балансе недостаточно средств!");
        }else{
            balance -= amount;
            System.out.println("Перевод на счет " + targetAccount + " прошел успешно!");
            transactionLogger.logTransaction("Перевод на счет " + targetAccount + " на сумму " + amount + " рублей");
        }
    }

    @Override
    public synchronized void changePin(int oldPin, int newPin) {
        String decryptedPin = SimplePinEncryptionUtil.decryptPin(encryptedPin, secretKey);

        if(Integer.parseInt(decryptedPin) == oldPin) {
            encryptedPin = SimplePinEncryptionUtil.encryptPin(Integer.toString(newPin), secretKey);
            System.out.println("PIN код успешно изменен!");
            transactionLogger.logTransaction("Изменение PIN-кода");
        }else{
            System.out.println("Ошибка! неверный старый PIN код");
        }
    }
}
