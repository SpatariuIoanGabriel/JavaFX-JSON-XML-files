package UI;

import Domain.Appointment;
import Domain.AppointmentValidator;
import Domain.Patient;
import Domain.PatientValidator;
import Service.Service;
import Exception.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    private Service service;

    public UI(Service service) {
        this.service = service;
    }

    public void printMenu() {
        System.out.println("\nMENU");
        System.out.println("0. Exit the program");
        System.out.println("1. Add a new patient");
        System.out.println("2. Remove a patient");
        System.out.println("3. Get a patient by id");
        System.out.println("4. Update a patient");
        System.out.println("5. List all the patients");
        System.out.println("6. Add a new appointment");
        System.out.println("7. Remove an appointment");
        System.out.println("8. Get an appointment by id");
        System.out.println("9. Update an appointment");
        System.out.println("10. List all the appointments");
        System.out.println("11. Show patients with a given disease");
        System.out.println("12. Show patients whose names end with a given letter");
        System.out.println("13. Get email of a patient by ID");
        System.out.println("14. Show all appointments of a patient by ID");
        System.out.println("15. Filter appointments before a certain date");
    }

    public void run() {
        while (true) {
            printMenu();
            System.out.print("Write your choice: ");
            Scanner scanCommand = new Scanner(System.in);
            int command;

            try {
                command = scanCommand.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid option.");
                scanCommand.nextLine();
                continue;
            }

            switch (command) {
                case 0:
                    System.out.println("The program is over!");
                    return;
                case 1:
                    addPatient();
                    break;
                case 2:
                    removePatientById();
                    break;
                case 3:
                    getPatientById();
                    break;
                case 4:
                    updatePatient();
                    break;
                case 5:
                    listAllPatients();
                    break;
                case 6:
                    addAppointment();
                    break;
                case 7:
                    removeAppointmentByID();
                    break;
                case 8:
                    getAppointmentById();
                    break;
                case 9:
                    updateAppointment();
                    break;
                case 10:
                    listAllAppointments();
                    break;
                case 11:
                    showPatientsWithGivenDisease();
                    break;
                case 12:
                    showPatientsEndingWithAGivenLetter();
                    break;
                case 13:
                    getEmailOfPatientById();
                    break;
                case 14:
                    showAllAppointmentsOfAPatientById();
                    break;
                case 15:
                    filterAppointmentsBeforeDate();
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option!");
                    break;
            }
        }
    }

    public void listAllPatients() {
        Iterable<Patient> listOfPatients = this.service.getAllPatients();

        for (Patient patient : listOfPatients) {
            System.out.println(patient);
        }
    }

    public void addPatient() {
        Scanner scanner = new Scanner(System.in);
        String name = "";
        String email = "";
        int id = 0;
        String disease = "";

        while (true) {
            System.out.println("Write patient's name: ");
            name = scanner.nextLine();
            try {
                PatientValidator.validateName(name);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
            }
        }

        while (true) {
            System.out.println("Write patient's email: ");
            email = scanner.nextLine();
            try {
                PatientValidator.validateEmail(email);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
            }
        }

        while (true) {
            System.out.println("Write patient's id: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                try {
                    PatientValidator.validatePatientId(id);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation error: " + e.getMessage());
                }
            } else {
                System.out.println("The input is not correct. Write a valid number!.");
                scanner.nextLine();
            }
        }

        while (true) {
            System.out.println("Write patient's disease: ");
            disease = scanner.nextLine();
            try {
                PatientValidator.validateDisease(disease);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
            }
        }

        try {
            service.addPatient(name, email, disease, id);
        } catch (NoIdenticalEntities e) {
            System.out.println("There is another patient with id=" + id + ".");
        }
    }


    public void getPatientById() {
        Scanner scanner = new Scanner(System.in);
        int id;

        while (true) {
            System.out.println("Write patient's id: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                try {
                    PatientValidator.validatePatientId(id);
                    Patient patient = service.getPatientById(id);
                    System.out.println(patient);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation error: " + e.getMessage());
                } catch (NoEntityFound e) {
                    System.out.println("There is no patient with id= " + id);
                    break;
                }
            } else {
                System.out.println("The input is not correct. Write a valid number!");
                scanner.nextLine();
            }
        }
    }


        public void updatePatient() {
            Scanner scanner = new Scanner(System.in);
            int id;
            while (true) {
                System.out.println("Write patient's id: ");
                if (scanner.hasNextInt()) {
                    id = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        PatientValidator.validatePatientId(id);
                        if (service.getPatientById(id) == null) {
                            System.out.println("There is no patient with id=" + id + ".");
                            return;
                        }
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Validation error: " + e.getMessage());
                    } catch (NoEntityFound e) {
                        System.out.println("There is no patient with id=" + id + ".");
                        return;
                    }
                } else {
                    System.out.println("The input is not correct. Write a valid number!.");
                    scanner.nextLine();
                }
            }
    
            String updateName = "";
            while (true) {
                System.out.println("Write the new patient's name: ");
                updateName = scanner.nextLine();
                try {
                    PatientValidator.validateName(updateName);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation error: " + e.getMessage());
                }
            }
    
            String updateEmail = "";
            while (true) {
                System.out.println("Write the new patient's email: ");
                updateEmail = scanner.nextLine();
                try {
                    PatientValidator.validateEmail(updateEmail);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation error: " + e.getMessage());
                }
            }
    
            String updateDisease = "";
            while (true) {
                System.out.println("Write the new patient's disease: ");
                updateDisease = scanner.nextLine();
                try {
                    PatientValidator.validateDisease(updateDisease);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation error: " + e.getMessage());
                }
            }
    
            try {
                service.updatePatient(id, updateName, updateEmail, updateDisease);
            } catch (NoEntityFound e) {
                System.out.println("There is no patient with id=" + id + ".");
            }
        }


    public void removePatientById() {
        Scanner scanner = new Scanner(System.in);
        int id;
        while (true) {
            System.out.println("Write the patient's id: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                try {
                    PatientValidator.validatePatientId(id);
                    service.removePatientById(id);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation error: " + e.getMessage());
                } catch (NoEntityFound e) {
                    System.out.println("There is no patient with id=" + id + ".");
                    break;
                }
            } else {
                System.out.println("The input is not correct. Write a valid number!");
                scanner.nextLine();
            }
        }
    }

    public void listAllAppointments() {
        Iterable<Appointment> listOfAppointments = this.service.getAllAppointments();

        if (listOfAppointments.iterator().hasNext()) {
            for (Appointment appointment : listOfAppointments) {
                System.out.println(appointment);
            }
        } else {
            System.out.println("No appointment found!");
        }
    }

    public void addAppointment() {
        Scanner scanner = new Scanner(System.in);
        int number;

        while (true) {
            System.out.println("Write the number of the appointment: ");
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                scanner.nextLine();
                try {
                    AppointmentValidator.validateNumber(number);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation error: " + e.getMessage());
                }
            } else {
                System.out.println("The input is not correct. Write a valid number!");
                scanner.nextLine();
            }
        }

        int id;

        while (true) {
            System.out.println("Write patient's id: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                try {
                    PatientValidator.validatePatientId(id);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation error: " + e.getMessage());
                }
            } else {
                System.out.println("The input is not correct. Write a valid number!");
                scanner.nextLine();
            }
        }

        Patient patient;
        try {
            patient = service.getPatientById(id);
        } catch (NoEntityFound e) {
            System.out.println("There is no patient with id= " + id + ".");
            return;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = null;

        while (date == null) {
            System.out.print("Please enter a date and respect the format (YYYY-MM-DD): ");
            String writtenDate = scanner.nextLine();

            try {
                date = LocalDate.parse(writtenDate, dateFormatter);
                AppointmentValidator.validateDate(date);
                if (service.isPatientAlreadyBooked(id)) {
                    System.out.println("This patient is already booked for an appointment.");
                    return;
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.println("The input is not correct. Write a valid date!");
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
            }
        }

        try {
            Appointment newAppointment = new Appointment(number, patient, date);
            AppointmentValidator.appointmentValidator(newAppointment);
            service.addAppointment(number, patient, date);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (NoIdenticalEntities e) {
            System.out.println("There is another appointment with the same number.");
        }
    }



    public void getAppointmentById() {
        Scanner scanner = new Scanner(System.in);
        int searchId;

        while (true) {
            System.out.println("Write the number of the appointment:");
            if (scanner.hasNextInt()) {
                searchId = scanner.nextInt();
                scanner.nextLine();
                try {
                    AppointmentValidator.validateNumber(searchId);
                    Appointment appointment = service.getAppointmentById(searchId);
                    System.out.println(appointment);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation error: " + e.getMessage());
                } catch (NoEntityFound e) {
                    System.out.println("There is no appointment with id=" + searchId);
                    break;
                }
            } else {
                System.out.println("The input is not correct. Write a valid number!");
                scanner.nextLine();
            }
        }
    }


    public void updateAppointment() {
        Scanner scanner = new Scanner(System.in);
        int number;

        while (true) {
            System.out.println("Write appointment's number: ");
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                scanner.nextLine();

                try {
                    AppointmentValidator.validateNumber(number);
                    Appointment appointment = service.getAppointmentById(number);
                    System.out.println(appointment);

                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = null;

                    while (date == null) {
                        System.out.print("Please enter a date and respect the format (YYYY-MM-DD): ");
                        String writtenDate = scanner.nextLine();

                        try {
                            date = LocalDate.parse(writtenDate, dateFormatter);
                            AppointmentValidator.validateDate(date);
                            Appointment updatedAppointment = new Appointment(number, appointment.getPatient(), date);
                            AppointmentValidator.appointmentValidator(updatedAppointment);
                            service.updateAppointment(number, date);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("The input is not correct. Write a valid date!");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Validation error: " + e.getMessage());
                        }
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation error: " + e.getMessage());
                } catch (NoEntityFound e) {
                    System.out.println("There is no appointment with id=" + number);
                    break;
                }
            } else {
                System.out.println("The input is not correct. Write a valid number!");
                scanner.nextLine();
            }
        }
    }

    public void removeAppointmentByID() {
        Scanner deleteScanner = new Scanner(System.in);
        int number;
        while (true) {
            System.out.println("Write the number of the appointment: ");
            if (deleteScanner.hasNextInt()) {
                number = deleteScanner.nextInt();
                deleteScanner.nextLine();
                try {
                    AppointmentValidator.validateNumber(number);
                    service.removeAppointmentById(number);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Validation error: " + e.getMessage());
                } catch (NoEntityFound e) {
                    System.out.println("There is no appointment with id= " + number + ".");
                    break;
                }
            } else {
                System.out.println("The input is not correct. Write a valid number!");
                deleteScanner.nextLine();
            }
        }
    }

    private void showPatientsWithGivenDisease() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter disease:");
        String disease = scanner.nextLine();
        ArrayList<Patient> patients = service.showPatientsWithGivenDisease(disease);
        patients.forEach(System.out::println);
    }

    private void showPatientsEndingWithAGivenLetter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter letter:");
        String letter = scanner.nextLine();
        ArrayList<Patient> patients = service.showPatientsEndingWithAGivenLetter(letter);
        patients.forEach(System.out::println);
    }

    private void getEmailOfPatientById() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter patient ID:");
        int id = scanner.nextInt();
        String email = service.getEmailById(id);
        System.out.println("Email: " + email);
    }

    private void showAllAppointmentsOfAPatientById() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter patient ID:");
        int id = scanner.nextInt();
        ArrayList<Appointment> appointments = service.showAllAppointmentsOfAPatientByAGivenId(id);
        appointments.forEach(System.out::println);
    }

    private void filterAppointmentsBeforeDate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter date (YYYY-MM-DD):");
        String dateString = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateString);
        ArrayList<Appointment> appointments = service.filteringAppointmentsBeforeDate(date);
        appointments.forEach(System.out::println);
    }

}