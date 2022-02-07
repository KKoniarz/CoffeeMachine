package machine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachine {

    public static void main(String[] args)
    {
        Machine myMachine = new Machine();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(myMachine.getMachineRunning())
        {
            input = scanner.nextLine();
            myMachine.machineAction(input);
        }
    }

}

class Coffee
{
    private String name;
    private int waterPerCup;
    private int milkPerCup;
    private int coffeePerCup;
    private int price;

    public Coffee(String name, int waterPerCup, int milkPerCup, int coffeePerCup, int price)
    {
        this.name = name;
        this.waterPerCup = waterPerCup;
        this.milkPerCup = milkPerCup;
        this.coffeePerCup = coffeePerCup;
        this.price = price;
    }
    public String getName() {return name;}
    public int getWaterPerCup() {return waterPerCup;}
    public int getMilkPerCup() {return milkPerCup;}
    public int getCoffeePerCup() {return coffeePerCup;}
    public int getPrice() {return price;}
}

class Machine
{
    private int water;
    private int milk;
    private int coffee;
    private int cups;
    private int money;

    private States state;
    private boolean machineRunning;

    private Coffee espresso;
    private Coffee latte;
    private Coffee cappuccino;

    public Machine()
    {
        water = 400;
        milk = 540;
        coffee = 120;
        cups = 9;
        money = 550;

        espresso = new Coffee("espresso",250,0,16,4);
        latte = new Coffee("latte",350,75,20,7);
        cappuccino = new Coffee("cappuccino",200,100,12,6);

        machineRunning = true;
        state = States.MENU;

        System.out.println("Write action (buy, fill, take, remaining, exit):");

    }

    private enum States{
        MENU,
        COFFEESELECT,
        FILLWATER,
        FILLMILK,
        FILLCOFFEE,
        FILLCUPS
    }

    public boolean getMachineRunning(){return machineRunning;}

    public void printMachineStatus()
    {
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffee + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + money +" of money");
        System.out.println();
    }

    public void buyCoffee(Coffee toMake)
    {
        String output = "";

        if (water < toMake.getWaterPerCup()) output += "Sorry, not enough water!\n";
        if (milk < toMake.getMilkPerCup()) output += "Sorry, not enough milk!\n";
        if (coffee < toMake.getCoffeePerCup()) output += "Sorry, not enough coffee!\n";
        if(cups == 0) output += "Sorry, not enough cups!\n";
        if(output.equals(""))
        {
            output += "I have enough resources, making you a coffee!";
            water -= toMake.getWaterPerCup();
            milk -= toMake.getMilkPerCup();
            coffee -= toMake.getCoffeePerCup();
            cups--;
            money += toMake.getPrice();
        }
        System.out.println(output);
    }

    public void takeMoney()
    {
        System.out.println("I gave you $" + money);
        System.out.println();
        money = 0;
    }


    public void machineAction(String userInput)
    {
        switch (state) {
            case MENU:
                switch (userInput) {
                    case "buy":
                        state = States.COFFEESELECT;
                        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                        break;
                    case "fill":
                        state = States.FILLWATER;
                        System.out.println("Write how many ml of water you want to add:");
                        break;
                    case "take":
                        takeMoney();
                        System.out.println("Write action (buy, fill, take, remaining, exit):");
                        break;
                    case "remaining":
                        printMachineStatus();
                        System.out.println("Write action (buy, fill, take, remaining, exit):");
                        break;
                    case "exit":
                        machineRunning = false;
                        break;
                    default:
                        System.out.println("Invalid input.");
                        System.out.println();
                        System.out.println("Write action (buy, fill, take, remaining, exit):");
                }
                break;
            case COFFEESELECT:
                switch (userInput) {
                    case "1":
                        buyCoffee(espresso);
                        state = States.MENU;
                        System.out.println("Write action (buy, fill, take, remaining, exit):");
                        break;
                    case "2":
                        buyCoffee(latte);
                        state = States.MENU;
                        System.out.println("Write action (buy, fill, take, remaining, exit):");
                        break;
                    case "3":
                        buyCoffee(cappuccino);
                        state = States.MENU;
                        System.out.println("Write action (buy, fill, take, remaining, exit):");
                        break;
                    case "back":
                        state = States.MENU;
                        System.out.println("Write action (buy, fill, take, remaining, exit):");
                        break;
                    default:
                        System.out.println("Invalid input.");
                        System.out.println();
                        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                }
                break;
            case FILLWATER:
                water += Integer.parseInt(userInput);
                System.out.println("Write how many ml of milk you want to add:");
                state = States.FILLMILK;
                break;
            case FILLMILK:
                milk += Integer.parseInt(userInput);
                System.out.println("Write how many grams of coffee beans you want to add:");
                state = States.FILLCOFFEE;
                break;
            case FILLCOFFEE:
                coffee += Integer.parseInt(userInput);
                System.out.println("Write how many disposable cups of coffee you want to add: ");
                state = States.FILLCUPS;
                break;
            case FILLCUPS:
                cups += Integer.parseInt(userInput);
                state = States.MENU;
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                break;
            }
        }
}
