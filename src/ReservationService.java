import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReservationService {

    private List<Student> students;
    private List<Equipment> equipmentList;
    private List<Reservation> reservations;

    private DiscountPolicy discountPolicy;

    private int reservationCounter = 1;

    public ReservationService(List<Student> students,
                              List<Equipment> equipmentList,
                              DiscountPolicy discountPolicy) {

        this.students = students;
        this.equipmentList = equipmentList;
        this.discountPolicy = discountPolicy;

        this.reservations = new ArrayList<>();
    }

    public Reservation createReservation(String studentId,
                                         String equipmentId,
                                         int days) {

        Student student = students.stream()
                .filter(s -> s.getId().equals(studentId))
                .findFirst()
                .orElse(null);

        if (student == null) {
            System.out.println("Student not found.");
            return null;
        }

        Equipment equipment = equipmentList.stream()
                .filter(e -> e.getId().equals(equipmentId))
                .findFirst()
                .orElse(null);

        if (equipment == null) {
            System.out.println("Equipment not found.");
            return null;
        }

        if (!equipment.isAvailable()) {
            System.out.println("Equipment is not available.");
            return null;
        }

        if (days < 1 || days > 14) {
            System.out.println("Days must be between 1 and 14.");
            return null;
        }

        String reservationId =
                String.format("R%03d", reservationCounter++);

        Reservation reservation =
                new Reservation(
                        reservationId,
                        student,
                        equipment,
                        days,
                        ReservationStatus.ACTIVE
                );

        equipment.setAvailable(false);

        reservations.add(reservation);

        System.out.println("Reservation created.");
        System.out.println("Cost: "
                + reservation.calculateTotalCost(discountPolicy));

        return reservation;
    }

    public void returnEquipment(String reservationId) {

        Reservation reservation = reservations.stream()
                .filter(r -> r.getId().equals(reservationId))
                .findFirst()
                .orElse(null);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            System.out.println("Reservation is not active.");
            return;
        }

        reservation.setStatus(ReservationStatus.RETURNED);

        reservation.getEquipment().setAvailable(true);

        int points =
                (int) (reservation.calculateTotalCost(discountPolicy) / 10);

        reservation.getStudent().addLoyaltyPoints(points);

        System.out.println(
                "Equipment returned. Points added: " + points
        );
    }

    public void printActiveReservations() {

        reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.ACTIVE)
                .forEach(r ->
                        System.out.println(r.getDisplayText()));
    }

    public void printReport() {

        double revenue = reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.RETURNED)
                .mapToDouble(r ->
                        r.calculateTotalCost(discountPolicy))
                .sum();

        System.out.println("=== COMPLETED RESERVATIONS ===");

        reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.RETURNED)
                .forEach(r ->
                        System.out.println(r.getDisplayText()));

        System.out.println("Revenue: " + revenue);

        Student bestStudent = students.stream()
                .max(Comparator.comparingInt(
                        Student::getLoyaltyPoints))
                .orElse(null);

        if (bestStudent != null) {
            System.out.println(
                    "Top student: "
                            + bestStudent.getFullName()
                            + " (" + bestStudent.getLoyaltyPoints()
                            + " points)"
            );
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }
}