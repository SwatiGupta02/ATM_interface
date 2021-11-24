import java.util.*;

// INPUT : 
// Account || Pin  ||  Balance 
// 1          1234     2000
// 2          2345     4000
// 3          3456     10,000
// 4          4567     100


class Card{
    private int pin;

    public Card(int pin){
        this.pin = pin;
    }
    
    public int getPin(){
        return pin;
    }
    
    public void setPin(int pin){
        this.pin = pin;
    }
}

class Account{
    private int balance;

    public Account(int balance){
        this.balance = balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    void update_balance(int amount, int transaction){
        if(transaction == 1){
            balance -= amount;
        }
        else{
            balance += amount;
        }
    }
}

class User{
    private int account_no;

    Account acc;
    Card card;

    public User(int account_no, int pin, int balance){
        acc = new Account(balance);
        card = new Card(pin);
        this.account_no = account_no;
    }

    public void setAccount(int acc) {
        this.account_no = acc;
    }

    public int getAccount() {
        return account_no;
    }
}


class ATM{

    static int machine_balance = 1000;
    static HashMap<Integer, User> accounts = new HashMap<>();

    public static void main(String[] args){

        accounts.put(1, new User(1, 1234, 2000));
        accounts.put(2, new User(2, 2345, 4000));
        accounts.put(3, new User(3, 3456, 10000));
        accounts.put(4, new User(4, 4567, 100));

        while(true){        
            System.out.println("Welcome! Please enter your account number");
            Scanner scan = new Scanner(System.in);
            int acc_no = scan.nextInt();
            if(!accounts.containsKey(acc_no)){
                System.out.println("Please enter a valid account number");
                continue;
            }
            System.out.println("Please enter your pin to continue");
            int pin = scan.nextInt();

            User account_details = accounts.get(acc_no);

            boolean isCardValid = verify_pin(account_details, pin);

            if(!isCardValid){
                System.out.println("Please enter a correct pin");
                continue;
            }

            System.out.println("Select transaction : 1 - Withdraw 2 - Deposit 3 - Display Balance");
            int transaction = scan.nextInt();
            int amount = 0;
            switch (transaction) {
                case 2:
                    System.out.println("Enter amount to be deposited?");
                    amount = scan.nextInt();
                    deposit(account_details, amount);
                    break;
                
                case 1:
                    System.out.println("Enter amount to be withdrawn?");
                    amount = scan.nextInt();
                    withdraw(account_details, amount);
                    break;

                case 3:
                    display_balance(account_details);
                    break;
                    
                default:
                    System.out.println("Number not selected");
                    break;
            }
        }
    }

    static boolean verify_pin(User account_details, int pin){
        if(account_details.card.getPin() == pin){
            return true;
        }      
        else return false;
    }

    static void deposit(User account_details, int amount){
        update_machine_balance(amount, 0);
        account_details.acc.update_balance(amount, 0);
        System.out.println("The transaction was successful");
    }

    static void withdraw(User account_details, int amount){
        if(account_details.acc.getBalance()  < amount){
            System.out.println("Account balance not sufficient");
            return;
        }
        if(machine_balance < amount){
            System.out.println("Starting balance that can be entered in the machine is : " + machine_balance);
            return;
        }
        if(amount%500 != 0){
            int rem = amount%500;
            if(rem%100 != 0){
                System.out.println("The amount entered cannot be dispensed, please enter multiple of 100 and 500");
                return;
            }
        }
        account_details.acc.update_balance(amount, 1);
        update_machine_balance(amount, 1);
        System.out.println("The transaction was successful");
    }
    
    static void display_balance(User account_details){
        System.out.println("The balance in your account is : " + account_details.acc.getBalance());
        System.out.println("The transaction was successful");
    }

    static void update_machine_balance(int amount, int transaction){
        if(transaction == 1){
            machine_balance -= amount;
        }
        else{
            machine_balance += amount;
        }
    }
}
