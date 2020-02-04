package budget;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

enum PurchaseType {
    FOOD, CLOTHES, ENTERTAINMENT, OTHER, ALL
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static double balance = 0.0;

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        while (true) {
            System.out.println("Choose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "5) Save\n" +
                    "6) Load\n" +
                    "7) Analyze (Sort)\n" +
                    "0) Exit"
            );
            int choice = sc.nextInt();
            System.out.println();
            sc.nextLine();
            switch (choice) {
                case 1:
                    addIncome();
                    break;
                case 2:
                    addPurchase();
                    break;
                case 3:
                    showPurchaseList();
                    break;
                case 4:
                    showBalance();
                    break;
                case 5:
                    saveToFile();
                    break;
                case 6:
                    loadFromFile();
                    break;
                case 7:
                    analyze();
                    break;
                case 0:
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Unknown operation");

            }
            System.out.println();

        }
    }

    public static void analyze() {
        while (true) {
            System.out.println("How do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");
            int choice = sc.nextInt();
            System.out.println();
            sc.nextLine();
            switch (choice) {
                case 1:
                    Purchase.showPurchaseList(PurchaseType.ALL, true);
                    break;
                case 2:
                    sortByType();
                    break;
                case 3:
                    sortCertainType();
                    break;
                case 4:
                    return;
            }
            System.out.println();
        }
    }

    public static void sortByType() {
        Map<Double, String> map = new TreeMap<>(Comparator.reverseOrder());
        map.put(Purchase.getSum(PurchaseType.FOOD), "Food");
        map.put(Purchase.getSum(PurchaseType.OTHER), "Other");
        map.put(Purchase.getSum(PurchaseType.CLOTHES), "Clothes");
        map.put(Purchase.getSum(PurchaseType.ENTERTAINMENT), "Entertainment");
        System.out.println("Types:");
        for (var entry : map.entrySet()) {
            System.out.printf("%s - $%.2f\n", entry.getValue(), entry.getKey());
        }
        System.out.printf("Total sum: $%.2f\n", Purchase.getSum(PurchaseType.ALL));
    }

    public static void sortCertainType() {
        System.out.println("Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other");
        int choice = sc.nextInt();
        System.out.println();
        sc.nextLine();
        switch (choice) {
            case 1:
                Purchase.showPurchaseList(PurchaseType.FOOD, true);
                break;
            case 2:
                Purchase.showPurchaseList(PurchaseType.CLOTHES, true);
                break;
            case 3:
                Purchase.showPurchaseList(PurchaseType.ENTERTAINMENT, true);
                break;
            case 4:
                Purchase.showPurchaseList(PurchaseType.OTHER, true);
                break;
        }
    }

    public static void saveToFile() {
        File file = new File("purchases.txt");
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(balance);
            for (Purchase p : Purchase.getPurchaseList(PurchaseType.ALL)) {
                writer.println(p.getName());
                writer.println(p.getPrice());
                writer.println(p.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Purchases were saved!");
    }

    public static void loadFromFile() {
        File file = new File("purchases.txt");
        try (Scanner reader = new Scanner(file)) {
            balance = Double.parseDouble(reader.nextLine());
            while (reader.hasNextLine()) {
                String name = reader.nextLine();
                double price = Double.parseDouble(reader.nextLine());
                String type = reader.nextLine();
                new Purchase(name, price, PurchaseType.valueOf(type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Purchases were loaded!");
    }

    public static void addIncome() {
        System.out.println("Enter income:");
        balance += sc.nextDouble();
        System.out.println("Income was added!");
    }

    public static void addPurchase() {
        while (true) {
            System.out.println("Choose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) Back");

            int choice = sc.nextInt();
            System.out.println();
            sc.nextLine();
            if (choice == 5) {
                return;
            }
            System.out.println("Enter purchase name:");
            String name = sc.nextLine();
            System.out.println("Enter its price");
            double price = sc.nextDouble();

            switch (choice) {
                case 1:
                    new Purchase(name, price, PurchaseType.FOOD);
                    break;
                case 2:
                    new Purchase(name, price, PurchaseType.CLOTHES);
                    break;
                case 3:
                    new Purchase(name, price, PurchaseType.ENTERTAINMENT);
                    break;
                case 4:
                    new Purchase(name, price, PurchaseType.OTHER);
                    break;
                default:
            }
            balance -= price;
            if (balance < 0) {
                balance = 0.0;
            }
            System.out.println("Purchase was added!\n");
        }
    }

    public static void showPurchaseList() {
        while (true) {
            System.out.println("Choose the type of purchases\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");

            int choice = sc.nextInt();
            System.out.println();
            sc.nextLine();
            switch (choice) {
                case 1:
                    Purchase.showPurchaseList(PurchaseType.FOOD, false);
                    break;
                case 2:
                    Purchase.showPurchaseList(PurchaseType.CLOTHES, false);
                    break;
                case 3:
                    Purchase.showPurchaseList(PurchaseType.ENTERTAINMENT, false);
                    break;
                case 4:
                    Purchase.showPurchaseList(PurchaseType.OTHER, false);
                    break;
                case 5:
                    Purchase.showPurchaseList(PurchaseType.ALL, false);
                    break;
                case 6:
                    return;
                default:
            }
            System.out.println();
        }
    }

    public static void showBalance() {
        System.out.printf("Balance: $%.2f\n", balance);
    }

}

class Purchase implements Comparable {

    private static List<Purchase> purchases = new ArrayList<>();
    private static List<Purchase> foodPurchases = new ArrayList<>();
    private static List<Purchase> clothingPurchases = new ArrayList<>();
    private static List<Purchase> entertainmentPurchases = new ArrayList<>();
    private static List<Purchase> otherPurchases = new ArrayList<>();

    private static double totalSum = 0;
    private static double foodSum = 0;
    private static double clothingSum = 0;
    private static double entertainmentSum = 0;
    private static double otherSum = 0;

    private String name;
    private double price;
    private PurchaseType type;

    public Purchase(String name, double price, PurchaseType type) {
        this.name = name;
        this.price = price;
        this.type = type;
        totalSum += price;
        purchases.add(this);
        switch (type) {
            case FOOD:
                foodPurchases.add(this);
                foodSum += price;
                break;
            case OTHER:
                otherPurchases.add(this);
                otherSum += price;
                break;
            case CLOTHES:
                clothingPurchases.add(this);
                clothingSum += price;
                break;
            case ENTERTAINMENT:
                entertainmentPurchases.add(this);
                entertainmentSum += price;
                break;
        }
    }

    public static List<Purchase> getPurchaseList(PurchaseType type) {
        switch (type) {
            case ENTERTAINMENT:
                return entertainmentPurchases;
            case CLOTHES:
                return clothingPurchases;
            case OTHER:
                return otherPurchases;
            case FOOD:
                return foodPurchases;
            default:
                return purchases;
        }
    }

    public static double getSum(PurchaseType type) {
        switch (type) {
            case FOOD:
                return foodSum;
            case OTHER:
                return otherSum;
            case CLOTHES:
                return clothingSum;
            case ENTERTAINMENT:
                return entertainmentSum;
            default:
                return totalSum;
        }
    }

    private static List<Purchase> getSortedList(PurchaseType type) {
        List<Purchase> sortedList = new ArrayList<>(getPurchaseList(type));
        sortedList.sort(Collections.reverseOrder());
        return sortedList;
    }

    public static void showPurchaseList(PurchaseType type, boolean isSorted) {
        List<Purchase> mylist;
        if (isSorted) {
            mylist = getSortedList(type);
        } else {
            mylist = getPurchaseList(type);
        }
        switch (type) {
            case ENTERTAINMENT:
                System.out.println("Entertainment:");
                break;
            case CLOTHES:
                System.out.println("Clothes: ");
                break;
            case OTHER:
                System.out.println("Other: ");
                break;
            case FOOD:
                System.out.println("Food: ");
                break;
            default:
                System.out.println("All: ");
        }
        if (mylist.isEmpty()) {
            System.out.println("Purchase list is empty!");
            return;
        }
        for (Purchase p : mylist) {
            System.out.printf("%s $%.2f\n", p.getName(), p.getPrice());
        }
        double sum = getSum(type);
        System.out.printf("Total: $%.2f \n", sum);
    }

    public PurchaseType getType() {
        return type;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return Double.compare(this.price, ((Purchase) o).price);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

}
