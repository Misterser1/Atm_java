package by.rustem;

import javax.crypto.SecretKey;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ATMConsoleView implements UserInterface{
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        TransactionLogger transactionLogger = new SimpleTransactionLogger();
        ATMConsoleView result = new ATMConsoleView(transactionLogger);
        result.handleUserActions();

        UserDAO userDAO = new UserDAO();
        AccountDAO accountDAO = new AccountDAO();

        SecretKey secretKey = SimplePinEncryptionUtil.generateSecretKey();

        String encryptedPin = SimplePinEncryptionUtil.encryptPin("1234", secretKey);

        User user = new User("Maga", encryptedPin);
        userDAO.addUser(user.getUsername(), user);

        Account account = new Account(5000, encryptedPin);
        accountDAO.addAccount("Maga", account);
        Account loadedAccount = accountDAO.getAccount("Maga");
        if (loadedAccount != null){
            System.out.println("Баланс: " + loadedAccount.getBalance());
            System.out.println("Пин-код: " + SimplePinEncryptionUtil.decryptPin(loadedAccount.getEncryptedPin(), secretKey));
        } else {
            System.out.println("Аккаунт не найден");
        }

    }

    private TransactionLogger transactionLogger;

    public ATMConsoleView(TransactionLogger transactionLogger) {
        this.transactionLogger = transactionLogger;
    }

    @Override
    public void displayMainMenu() {
        System.out.println("Добрый день, что бы вы хотели сделать?");
        System.out.println("1. Снять наличные");
        System.out.println("2. Внести наличные");
        System.out.println("3. Проверить баланс");
        System.out.println("4. Перевести средства");
        System.out.println("5. Изменить PIN-код");
        System.out.println("0. Завершить работу\n");
    }

    @Override
    public void handleUserActions() {
        Scanner scanner = new Scanner(System.in);
        SimpleAccountManager accountManager = new SimpleAccountManager();
        boolean running = true;

        while (running) {
            displayMainMenu();
            System.out.print("Выберите операцию: ");

            try {
                int select = scanner.nextInt();
                switch (select) {
                    case 1:
                        System.out.print("Введите сумму для снятия: ");
                        double amount = scanner.nextDouble();
                        executor.submit(() -> accountManager.withdraw(amount));
                        pause();
                        break;
                    case 2:
                        System.out.print("Введите сумму для пополнения: ");
                        amount = scanner.nextDouble();
                        executor.submit(() -> accountManager.deposit(amount));
                        pause();
                        break;
                    case 3:
                        executor.submit(accountManager::checkBalance);
                        pause();
                        break;
                    case 4:
                        System.out.print("Введите ФИО для перевода: ");
                        scanner.nextLine();
                        String fio = scanner.nextLine();
                        System.out.print("Введите сумму для перевода: ");
                        int sum = scanner.nextInt();
                        executor.submit(() -> accountManager.transferFunds(fio, sum));
                        pause();
                        break;
                    case 5:
                        System.out.print("Введите старый пин-код: ");
                        int oldPin = scanner.nextInt();
                        System.out.print("Введите новый пин-код: ");
                        int newPin = scanner.nextInt();
                        executor.submit(() -> accountManager.changePin(oldPin, newPin));
                        pause();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Спасибо за использование ATM!");
                        executor.shutdown();
                        pause();
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                        pause();
                }
            } catch (Exception e) {
                System.out.println("Ошибка ввода. Пожалуйста, введите число.");
                scanner.nextLine();
                pause();
            }
        }

        scanner.close();
    }

    private void pause() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
