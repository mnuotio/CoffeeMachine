package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMaker machine = new CoffeeMaker(400, 540, 120, 9, 550);

        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("exit")) {
                break;
            }
            machine.use(userInput);
        }
    }
}

class CoffeeMaker {
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;
    private State state;

    public CoffeeMaker(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
        this.state = State.INIT;
        printMainMenu();
    }

    // method to interact with the coffeeMaker
    public void use(String action) {


        if (state == State.BUY) {
            buy(action);
            state = State.INIT;
            printMainMenu();
        }

        if (state == State.ADDCUPS) {
            this.cups += Integer.parseInt(action);
            state = State.INIT;
            printMainMenu();
            return;
        }

        if (state == State.ADDBEANS) {
            this.beans += Integer.parseInt(action);
            System.out.println("Write how many disposable cups of coffee do you want to add:");
            state = State.ADDCUPS;
            return;
        }

        if (state == State.ADDMILK) {
            this.milk += Integer.parseInt(action);
            System.out.println("Write how many grams of coffee beans do you want to add:");
            state = State.ADDBEANS;
            return;
        }

        if (state == State.ADDWATER) {
            this.water += Integer.parseInt(action);
            System.out.println("Write how many ml of milk do you want to add:");
            state = State.ADDMILK;
            return;
        }

        if (state == State.INIT) {
            switch (action) {
                case "remaining":
                    state = State.REMAINING;
                    printSupplies();
                    state = State.INIT;
                    printMainMenu();
                    break;
                case "take":
                    state = State.TAKE;
                    take();
                    state = State.INIT;
                    printMainMenu();
                    break;
                case "buy":
                    state = State.BUY;
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
                    break;
                case "fill":
                    state = State.ADDWATER;
                    System.out.println("Write how many ml of water do you want to add:");
                    break;
            }
        }
    }

    private void printSupplies() {
        System.out.println("The coffee machine has:");
        System.out.println(this.water + " of water");
        System.out.println(this.milk + " of milk");
        System.out.println(this.beans + " of coffee beans");
        System.out.println(this.cups + " of disposable cups");
        System.out.println(this.money + " of money");
    }

    private void printMainMenu() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    private void take() {
        System.out.println("I gave you $" + this.money);
        this.money = 0;
    }

    private void buy(String drinkType) {
        // water, milk, beans, money
        switch (drinkType) {
            case "1": // espresso
                buyDrink(250, 0, 16, 4);
                break;
            case "2": // latte
                buyDrink(350, 75, 20, 7);
                break;
            case "3": // cappuccino
                buyDrink(200, 100, 12, 6);
                break;
            case "back":
                break;
        }
    }

    private void buyDrink(int water, int milk, int beans, int money) {

        boolean enoughWater = water <= this.water;
        boolean enoughMilk = milk <= this.milk;
        boolean enoughBeans = beans <= this.beans;
        boolean enoughCups = this.cups > 0;

        if (enoughWater && enoughMilk && enoughBeans && enoughCups) {
            System.out.println("I have enough resources, making you a coffee!");
            this.water -= water;
            this.milk -= milk;
            this.beans -= beans;
            this.money += money;
            this.cups -= 1;
        } else {
            System.out.print("Sorry, not enough ");
            if (!enoughWater) {
                System.out.println("water!");
            } else if (!enoughMilk) {
                System.out.println("milk!");
            } else if (!enoughBeans) {
                System.out.println("beans!");
            } else {
                System.out.println("cups!");
            }
        }
    }
}

enum State {
    INIT, BUY, ADDWATER, ADDMILK, ADDBEANS, ADDCUPS, TAKE, REMAINING;
}