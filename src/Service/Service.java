package Service;

import Exception.*;
import Repository.IRepository;
import java.time.LocalDate;
import Domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Service {
    private IRepository<Patient, Integer> patientsRepo;
    private IRepository<Appointment, Integer> appointmentsRepo;

    public Service(IRepository<Patient, Integer> patientRepo, IRepository<Appointment, Integer> appointmentRepo) {
        this.patientsRepo = patientRepo;
        this.appointmentsRepo = appointmentRepo;
    }

    // Patient CRUD
    public void addPatient(String Name, String Email, String disease, int PatientId) throws NoIdenticalEntities {
        Patient newPatient = new Patient(Name, Email, disease, PatientId);
        PatientValidator.patientValidator(newPatient);
        this.patientsRepo.addEntity(newPatient);
    }

    public void removePatientById(int id) throws NoEntityFound {
        PatientValidator.validatePatientId(id);
        this.patientsRepo.removeEntityById(id);
    }

    public Patient getPatientById(int id) throws NoEntityFound {
        PatientValidator.validatePatientId(id);
        return patientsRepo.getEntityById(id);
    }

    public Iterable<Patient> getAllPatients() {return this.patientsRepo.getAllEntities();}

    public void updatePatient(int id, String newName, String newEmail, String newDisease) throws NoEntityFound {
        Patient patient = patientsRepo.getEntityById(id);

        patient.setName(newName);
        patient.setEmail(newEmail);
        patient.setDisease(newDisease);

        PatientValidator.patientValidator(patient);

        this.patientsRepo.updateEntityById(id, patient);
    }

    // Appointment CRUD

    public void addAppointment(int number, Patient patient, LocalDate date) throws NoIdenticalEntities {
        Appointment newAppointment = new Appointment(number, patient, date);
        AppointmentValidator.appointmentValidator(newAppointment);
        this.appointmentsRepo.addEntity(newAppointment);
    }

    public void removeAppointmentById(int id) throws NoEntityFound {
        AppointmentValidator.validateNumber(id);
        this.appointmentsRepo.removeEntityById(id);
    }

    public Appointment getAppointmentById(int id) throws NoEntityFound {
        AppointmentValidator.validateNumber(id);
        return appointmentsRepo.getEntityById(id);
    }

    public Iterable<Appointment> getAllAppointments() {return this.appointmentsRepo.getAllEntities();}

    public void updateAppointment(Integer number, LocalDate newDate) throws NoEntityFound {
        Appointment appointment = getAppointmentById(number);
        appointment.setDate(newDate);

        AppointmentValidator.appointmentValidator(appointment);

        this.appointmentsRepo.updateEntityById(number, appointment);
    }
    public boolean isPatientAlreadyBooked(int patientId) {
        for (Appointment appointment : appointmentsRepo.getAllEntities()) {
            if (appointment.getPatient().getId() == patientId) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Patient> showPatientsWithGivenDisease(String disease) {
        List<Patient> listOfPatients = new ArrayList<>((Collection) patientsRepo.getAllEntities());
        ArrayList<Patient> patientsFromAGivenCity = (ArrayList<Patient>) listOfPatients.stream()
                .filter(patient -> patient.getDisease().toLowerCase().contains(disease.toLowerCase()))
                .sorted((patient1, patient2)-> {
                    return (patient1.getId().compareTo(patient2.getId()));
                })
                .collect(Collectors.toList());
        return patientsFromAGivenCity;
    }


    public ArrayList<Patient> showPatientsEndingWithAGivenLetter(String letter) {
        List<Patient> listOfPatients = new ArrayList<>((Collection) patientsRepo.getAllEntities());
        ArrayList<Patient> patientsEndingWithLetter = listOfPatients.stream()
                .filter(patient -> patient.getName().endsWith(letter))
                .collect(Collectors.toCollection(ArrayList::new));
        return patientsEndingWithLetter;
    }


    public String getEmailById(int patientId) {
        List<Patient> listOfPatients = new ArrayList<>((Collection) patientsRepo.getAllEntities());
        String email = listOfPatients.stream()
                .filter(patient -> patient.getId() == patientId)
                .map(Patient::getEmail)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Patient with ID " + patientId + " not found."));
        return email;
    }


    public ArrayList<Appointment> showAllAppointmentsOfAPatientByAGivenId(int id) {
        List<Appointment> listOfAppointments = new ArrayList<>((Collection) appointmentsRepo.getAllEntities());
        ArrayList<Appointment> appointmentsOfPatient = listOfAppointments.stream()
                .filter(appointment -> appointment.getPatient().getId() == id)
                .collect(Collectors.toCollection(ArrayList::new));
        return appointmentsOfPatient;
    }


    public ArrayList<Appointment> filteringAppointmentsBeforeDate(LocalDate date) {
        List<Appointment> listOfAppointments = new ArrayList<>((Collection) appointmentsRepo.getAllEntities());
        ArrayList<Appointment> appointmentsBeforeDate = listOfAppointments.stream()
                .filter(appointment -> appointment.getDate().isBefore(date))
                .collect(Collectors.toCollection(ArrayList::new));
        return appointmentsBeforeDate;
    }
}