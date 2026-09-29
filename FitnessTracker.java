
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class FitnessRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    String date;
    int steps;
    int exerciseMinutes;
    double waterLitres;

    FitnessRecord(String date, int steps,
                  int exerciseMinutes, double waterLitres) {
        this.date = date;
        this.steps = steps;
        this.exerciseMinutes = exerciseMinutes;
        this.waterLitres = waterLitres;
    }

    void display() {
        System.out.println("Date: " + date);
        System.out.println("Steps: " + steps);
        System.out.println("Exercise Time: " + exerciseMinutes + " minutes");
        System.out.println("Water Intake: " + waterLitres + " litres");
        System.out.println("-----------------------------");
    }
}

public class FitnessTracker {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<FitnessRecord> records = new ArrayList<>();
    static final String FILE_NAME = "fitness.dat";

    public static void main(String[] args) {
        loadRecords();

        while (true) {
            System.out.println("\n===== FITNESS TRACKER APP =====");
            System.out.println("1. Add Daily Record");
            System.out.println("2. View All Records");
            System.out.println("3. Search Record by Date");
            System.out.println("4. Total Records");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    addRecord();
                    break;

                case 2:
                    viewRecords();
                    break;

                case 3:
                    searchRecord();
                    break;

                case 4:
                    System.out.println(
                        "Total Records: " + records.size()
                    );
                    break;

                case 5:
                    saveRecords();
                    System.out.println("Thank you for using Fitness Tracker!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.nextLine();
        }

        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    static double readDouble() {
        while (!sc.hasNextDouble()) {
            System.out.print("Enter a valid number: ");
            sc.nextLine();
        }

        double value = sc.nextDouble();
        sc.nextLine();
        return value;
    }

    static void addRecord() {
        System.out.print("Enter Date (DD-MM-YYYY): ");
        String date = sc.nextLine();

        System.out.print("Enter Daily Steps: ");
        int steps = readInt();

        System.out.print("Enter Exercise Time (minutes): ");
        int exerciseMinutes = readInt();

        System.out.print("Enter Water Intake (litres): ");
        double waterLitres = readDouble();

        if (steps < 0 || exerciseMinutes < 0 || waterLitres < 0) {
            System.out.println("Values cannot be negative!");
            return;
        }

        FitnessRecord record = new FitnessRecord(
            date, steps, exerciseMinutes, waterLitres
        );

        records.add(record);
        saveRecords();

        System.out.println("Fitness record added successfully!");
    }

    static void viewRecords() {
        if (records.isEmpty()) {
            System.out.println("No fitness records found!");
            return;
        }

        System.out.println("\n===== ALL FITNESS RECORDS =====");

        for (FitnessRecord record : records) {
            record.display();
        }

        System.out.println("Total Records: " + records.size());
    }

    static void searchRecord() {
        System.out.print("Enter date to search (DD-MM-YYYY): ");
        String date = sc.nextLine();

        boolean found = false;

        for (FitnessRecord record : records) {
            if (record.date.equalsIgnoreCase(date)) {
                System.out.println("Fitness record found:");
                record.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No record found for this date!");
        }
    }

    static void saveRecords() {
        try (ObjectOutputStream out =
                 new ObjectOutputStream(
                     new FileOutputStream(FILE_NAME))) {

            out.writeObject(records);

        } catch (IOException e) {
            System.out.println("Error saving records: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    static void loadRecords() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in =
                 new ObjectInputStream(
                     new FileInputStream(FILE_NAME))) {

            records = (ArrayList<FitnessRecord>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading records: " + e.getMessage());
        }
    }
}