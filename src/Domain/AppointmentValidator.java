package Domain;

import java.time.LocalDate;

public class AppointmentValidator {

    public static void appointmentValidator(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment object can't be null!");
        }

        validateNumber(appointment.getId());
        validatePatient(appointment.getPatient());
        validateDate(appointment.getDate());
    }

    public static void validateNumber(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Appointment number must be a positive integer!");
        }
    }

    private static void validatePatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient object can't be null!");
        }
        PatientValidator.patientValidator(patient);
    }

    public static void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date can't be null!");
        }
    }
}
