import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        List<Student> students = new ArrayList<>();

        students.add(new Student("S001",
                "Anna Kowalska",
                "12c",
                120));

        students.add(new Student("S002",
                "Marek Nowak",
                "12c",
                40));

        students.add(new Student("S003",
                "Julia Zielinska",
                "13a",
                0));

        List<Equipment> equipment = new ArrayList<>();

        equipment.add(
                new LaptopSet(
                        "E001",
                        "Lenovo ThinkPad Lab",
                        80,
                        32,
                        true
                )
        );

        equipment.add(
                new LaptopSet(
                        "E002",
                        "Dell XPS Demo",
                        100,
                        16,
                        false
                )
        );

        equipment.add(
                new CameraKit(
                        "E003",
                        "Sony Content Kit",
                        90,
                        3,
                        true
                )
        );

        equipment.add(
                new CameraKit(
                        "E004",
                        "Canon Interview Kit",
                        70,
                        1,
                        true
                )
        );

        ReservationService service =
                new ReservationService(
                        students,
                        equipment,
                        new LoyaltyDiscountPolicy()
                );

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n=== MEDIA LAB ===");
            System.out.println("1. Display students");
            System.out.println("2. Display equipment");
            System.out.println("3. Create reservation");
            System.out.println("4. Return equipment");
            System.out.println("5. Active reservations");
            System.out.println("6. Report");
            System.out.println("0. Exit");

            int choice =
                    Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:

                    service.getStudents()
                            .forEach(System.out::println);

                    break;

                case 2:

                    service.getEquipmentList()
                            .forEach(e ->
                                    System.out.println(
                                            e.getDisplayText()
                                    ));

                    break;

                case 3:

                    System.out.print("Student id: ");
                    String studentId =
                            scanner.nextLine();

                    System.out.print("Equipment id: ");
                    String equipmentId =
                            scanner.nextLine();

                    System.out.print("Days: ");
                    int days =
                            Integer.parseInt(
                                    scanner.nextLine()
                            );

                    service.createReservation(
                            studentId,
                            equipmentId,
                            days
                    );

                    break;

                case 4:

                    System.out.print(
                            "Reservation id: "
                    );

                    String reservationId =
                            scanner.nextLine();

                    service.returnEquipment(
                            reservationId
                    );

                    break;

                case 5:

                    service.printActiveReservations();

                    break;

                case 6:

                    service.printReport();

                    break;

                case 0:

                    System.out.println("Bye");
                    return;

                default:

                    System.out.println(
                            "Wrong option."
                    );
            }
        }
    }
}