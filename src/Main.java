import Domain.Appointment;
import Domain.Patient;
import Exception.*;
import Repository.*;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;
import Service.Service;
import UI.UI;


public class Main {
    public static void main(String[] args) throws NoEntityFound, NoIdenticalEntities {
        IRepository<Patient, Integer> repoPatient = null;
        IRepository<Appointment, Integer> repoAppointment = null;

        try (FileReader fr = new FileReader("settings.properties")) {
            Properties props = new Properties();
            props.load(fr);

            String repoType = props.getProperty("repositoryType");
            String sourceNamePatient = props.getProperty("repositoryPatientsLocation");
            String sourceNameAppointment = props.getProperty("repositoryAppointmentsLocation");

            switch (repoType) {
                case "inmemory":
                    repoPatient = new PatientRepo();
                    repoAppointment = new AppointmentRepo();
                    break;
                case "textfile":
                    repoPatient = new PatientRepoTextFile(sourceNamePatient);
                    repoAppointment = new AppointmentRepoTextFile(sourceNameAppointment);
                    break;
                case "binaryfile":
                    repoPatient = new PatientRepoBinaryFile(sourceNamePatient);
                    repoAppointment = new AppointmentRepoBinaryFile(sourceNameAppointment);
                    break;
                case "database":
                    repoPatient = new PatientDBRepo(sourceNamePatient);
                    repoAppointment = new AppointmentDBRepo(sourceNameAppointment);
                    break;
                case "json":
                    repoPatient = new PatientRepoJsonFile(sourceNamePatient);
                    repoAppointment = new AppointmentRepoJsonFile(sourceNameAppointment);
                    break;
                case "xml":
                    repoPatient = new PatientRepoXML_File(sourceNamePatient);
                    repoAppointment = new AppointmentRepoXML_File(sourceNameAppointment);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Patient patient = new Patient("Alex", "alex123@gmail.com", "Cold", 1);
        Appointment appointment = new Appointment(1, patient, LocalDate.of(2023, 1, 1));
        repoPatient.addEntity(patient);
        repoAppointment.addEntity(appointment);

        Patient patient2 = new Patient("Alice", "alice456@gmail.com", "Cold", 2);
        Appointment appointment2 = new Appointment(2, patient2, LocalDate.of(2023, 1, 1));
        repoPatient.addEntity(patient2);
        repoAppointment.addEntity(appointment2);

        Patient patient3 = new Patient("Mike", "mike789@gmail.com", "Flu", 3);
        Appointment appointment3 = new Appointment(3, patient3, LocalDate.of(2023, 1, 1));
        repoPatient.addEntity(patient3);
        repoAppointment.addEntity(appointment3);

        Service service = new Service(repoPatient, repoAppointment);
        UI ui = new UI(service);
        ui.run();

        Service reportService = new Service(repoPatient, repoAppointment);

        System.out.println("\n");
        System.out.println("Patients with Cold:");
        reportService.showPatientsWithGivenDisease("Cold").forEach(System.out::println);

        System.out.println("\n");
        System.out.println("Patients whose name ends with 'e':");
        reportService.showPatientsEndingWithAGivenLetter("e").forEach(System.out::println);

        System.out.println("\n");
        try {
            String email = reportService.getEmailById(1);
            System.out.println("Email of patient with ID 1: " + email);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n");
        int patientIdForAppointments = 2;
        System.out.println("Appointments for patient with ID " + patientIdForAppointments + ":");
        reportService.showAllAppointmentsOfAPatientByAGivenId(patientIdForAppointments)
                .forEach(System.out::println);

        System.out.println("\n");
        LocalDate date = LocalDate.of(2024, 1, 1);
        System.out.println("Appointments before " + date + ":");
        reportService.filteringAppointmentsBeforeDate(date)
                .forEach(System.out::println);
    }

//        Patient patient1 = new Patient("Alex", "alex123@gmail.com", "Broken Tooth", 1);
//        Patient patient2 = new Patient("Alice", "alice123@gmail.com", "Gum Disease", 2);
//        try {
//            repoPatient.addEntity(patient1);
//            repoPatient.addEntity(patient2);
//        } catch (NoIdenticalEntities e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//        for (Patient patient: repoPatient.getAllEntities())
//            System.out.println(patient);
//        System.out.println("\n");
//
//        Appointment appointment1 = new Appointment(1, patient1, LocalDate.of(2023, 1, 1));
//        Appointment appointment2 = new Appointment(2, patient2, LocalDate.of(2023, 1, 1));
//        try {
//            repoAppointment.addEntity(appointment1);
//            repoAppointment.addEntity(appointment2);
//        } catch (NoIdenticalEntities e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//        for (Appointment appointment: repoAppointment.getAllEntities())
//            System.out.println(appointment);
//        System.out.println("\n");
//
//        Service service = new Service(repoPatient, repoAppointment);
//        UI ui = new UI(service);
//        ui.run();
//
//        // Text file for patients
//        Patient patient_1 = new Patient("Alex", "alex123@gmail.com", "Gum Disease", 1);
//        Patient patient_2 = new Patient("Alice", "alice123@gmail.com", "Broken Tooth", 2);
//        try {
//            repoPatient.addEntity(patient_1);
//        } catch (NoIdenticalEntities e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            repoPatient.addEntity(patient_2);
//        } catch (NoIdenticalEntities e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println("Patients from text file:");
//        for (Patient patient : repoPatient.getAllEntities()) {
//            System.out.println(patient);
//        }
//        System.out.println("\n");
//
////        //Text file for appointments
//        Appointment appointment_1 = new Appointment(1, patient_1, LocalDate.of(2023, 1, 1));
//        Appointment appointment_2 = new Appointment(2, patient_2, LocalDate.of(2023, 1, 1));
//        try {
//            repoAppointment.addEntity(appointment_1);
//        } catch (NoIdenticalEntities e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            repoAppointment.addEntity(appointment_2);
//        } catch (NoIdenticalEntities e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println("Appointments from text file:");
//        for (Appointment appointment : repoAppointment.getAllEntities()) {
//            System.out.println(appointment);
//        }
//        System.out.println("\n");
//
////        //Binary file for patients
//        try {
//            repoPatient.addEntity(patient_1);
//        } catch (NoIdenticalEntities e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            repoPatient.addEntity(patient_2);
//        } catch (NoIdenticalEntities e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println("Patients from binary file:");
//        for (Patient patient : repoPatient.getAllEntities()) {
//            System.out.println(patient);
//        }
//        System.out.println("\n");
//
////        // Binary file for appointments
//        try {
//            repoAppointment.addEntity(appointment_1);
//        } catch (NoIdenticalEntities e) {
//            System.out.println(e.getMessage());
//        }
//        try {
//            repoAppointment.addEntity(appointment_2);
//        } catch (NoIdenticalEntities e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println("Appointments from binary file:");
//        for (Appointment appointment : repoAppointment.getAllEntities()) {
//            System.out.println(appointment);
//        }
//        System.out.println("\n");
//    }
}