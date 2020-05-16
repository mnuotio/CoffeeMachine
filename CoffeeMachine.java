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

    // Constructor
    public CoffeeMaker(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
        this.state = State.INIT;
        printMainMenu();
        //    System.out.println("MACHINE CREATED");
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



/*

        **** SOLUTION FOR #5 BELOW *****
package machine;

import java.util.HashMap;
import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Integer> supplies = new HashMap<>();
        supplies.put("water", 400);
        supplies.put("milk", 540);
        supplies.put("beans", 120);
        supplies.put("cups", 9);
        supplies.put("money", 550);

        while (true) {

            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String input = scanner.nextLine();

            switch (input) {
                case "buy":
                    buy(scanner, supplies);
                    break;
                case "fill":
                    fill(scanner, supplies);
                    break;
                case "take":
                    take(scanner, supplies);
                    break;
                case "remaining":
                    printSupplies(supplies);
                    break;
                case "exit":
                    exit = true;
                    break;
            }


            if (exit) { // end program
                break;
            }

        } // end of main while loop

    }

    private static void take(Scanner scanner, HashMap<String, Integer> supplies) {
        System.out.println("I gave you $" + supplies.get("money"));
        supplies.put("money", 0);
    }

    private static void fill(Scanner scanner, HashMap<String, Integer> supplies) {
        System.out.println("Write how many ml of water do you want to add:");
        supplies.put("water", supplies.get("water") + scanner.nextInt());

        System.out.println("Write how many ml of milk do you want to add:");
        supplies.put("milk", supplies.get("milk") + scanner.nextInt());

        System.out.println("Write how many grams of coffee beans do you want to add:");
        supplies.put("beans", supplies.get("beans") + scanner.nextInt());

        System.out.println("Write how many disposable cups of coffee do you want to add:");
        supplies.put("cups", supplies.get("cups") + scanner.nextInt());
    }

    private static void buy(Scanner scanner, HashMap<String, Integer> supplies) {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
        String input = scanner.nextLine();

        // water, milk, beans, money
        switch (input) {
            case "1": // espresso
                buyDrink(supplies, 250, 0, 16, 4);
                break;
            case "2": // latte
                buyDrink(supplies, 350, 75, 20, 7);
                break;
            case "3": // cappuccino
                buyDrink(supplies, 200, 100, 12, 6);
                break;
            case "back" :
                break;
        }



    }

    private static void buyDrink(HashMap<String, Integer> supplies, int water, int milk, int beans, int money) {

        boolean enoughWater = water <= supplies.get("water");
        boolean enoughMilk = milk <= supplies.get("milk");
        boolean enoughBeans = beans <= supplies.get("beans");
        boolean enoughCups = supplies.get("cups") > 0;

        if (enoughWater && enoughMilk && enoughBeans && enoughCups) {
            System.out.println("I have enough resources, making you a coffee!");
            supplies.put("water", supplies.get("water") - water);
            supplies.put("milk", supplies.get("milk") - milk);
            supplies.put("beans", supplies.get("beans") - beans);
            supplies.put("money", supplies.get("money") + money);
            supplies.put("cups", supplies.get("cups") - 1);
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

    public static void printSupplies(HashMap<String, Integer> supplies) {
        System.out.println("The coffee machine has:");
        System.out.println(supplies.get("water") + " of water");
        System.out.println(supplies.get("milk") + " of milk");
        System.out.println(supplies.get("beans") + " of coffee beans");
        System.out.println(supplies.get("cups") + " of disposable cups");
        System.out.println(supplies.get("money") + " of money");
    }

}
*/