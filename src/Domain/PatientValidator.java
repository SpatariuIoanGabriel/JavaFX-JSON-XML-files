package Domain;

import java.util.regex.Pattern;

public class PatientValidator {

    public static void patientValidator(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient object can't be null!");
        }

        validateName(patient.getName());
        validateEmail(patient.getEmail());
        validateDisease(patient.getDisease());
        validatePatientId(patient.getId());
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name can't be null or empty!");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            throw new IllegalArgumentException("Invalid email format!");
        }
    }

    public static void validateDisease(String disease) {
        if (disease == null || disease.trim().isEmpty()) {
            throw new IllegalArgumentException("Disease description can't be null or empty!");
        }
    }

    public static void validatePatientId(Integer patientId) {
        if (patientId == null || patientId <= 0) {
            throw new IllegalArgumentException("Patient ID must be a positive integer!");
        }
    }
}
