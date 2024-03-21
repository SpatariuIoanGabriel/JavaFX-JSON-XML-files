package Service;

import Domain.Appointment;
import Domain.Patient;
import Exception.*;
import Repository.AppointmentRepo;
import Repository.PatientRepo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class ServiceTest {

    @Test
    void testAddPatient() {
        PatientRepo patientRepo = new PatientRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        Service service = new Service(patientRepo, appointmentRepo);

        assertDoesNotThrow(() -> service.addPatient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1));
        assertEquals(1, patientRepo.listWithElements.size());
    }

    @Test
    void testRemovePatientById() throws NoIdenticalEntities {
        PatientRepo patientRepo = new PatientRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        Service service = new Service(patientRepo, appointmentRepo);
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        patientRepo.addEntity(patient);

        assertDoesNotThrow(() -> service.removePatientById(1));
        assertThrows(NoEntityFound.class, () -> service.removePatientById(2));
    }

    @Test
    void testGetPatientById() throws NoEntityFound, NoIdenticalEntities {
        PatientRepo patientRepo = new PatientRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        Service service = new Service(patientRepo, appointmentRepo);
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        patientRepo.addEntity(patient);

        assertEquals(patient, service.getPatientById(1));
        assertThrows(NoEntityFound.class, () -> service.getPatientById(2));
    }

    @Test
    void testGetAllPatients() throws NoIdenticalEntities {
        PatientRepo patientRepo = new PatientRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        Service service = new Service(patientRepo, appointmentRepo);
        Patient patient1 = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        Patient patient2 = new Patient("Emily Jones", "emilyjones@gmail.com", "Gum Disease", 2);

        patientRepo.addEntity(patient1);
        patientRepo.addEntity(patient2);

        Iterable<Patient> patients = service.getAllPatients();
        int count = 0;
        for (Patient p : patients) {
            count++;
        }

        assertEquals(2, count);
    }

    @Test
    void testUpdatePatient() throws NoEntityFound, NoIdenticalEntities {
        PatientRepo patientRepo = new PatientRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        Service service = new Service(patientRepo, appointmentRepo);
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        patientRepo.addEntity(patient);

        assertDoesNotThrow(() -> service.updatePatient(1, "Emily Jones", "emilyjones@gmail.com", "Gum Disease"));
        assertEquals("Emily Jones", patientRepo.getEntityById(1).getName());
        assertThrows(NoEntityFound.class, () -> service.updatePatient(2, "Emily Jones", "emilyjones@gmail.com", "Gum Disease"));
    }

    @Test
    void testAddAppointment() {
        PatientRepo patientRepo = new PatientRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        Service service = new Service(patientRepo, appointmentRepo);
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        LocalDate date = LocalDate.now();

        assertDoesNotThrow(() -> service.addAppointment(1, patient, date));
        assertEquals(1, appointmentRepo.listWithElements.size());
    }

    @Test
    void testRemoveAppointmentById() throws NoIdenticalEntities {
        PatientRepo patientRepo = new PatientRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        Service service = new Service(patientRepo, appointmentRepo);
        Appointment appointment = new Appointment(1, new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1), LocalDate.now());
        appointmentRepo.addEntity(appointment);

        assertDoesNotThrow(() -> service.removeAppointmentById(1));
        assertThrows(NoEntityFound.class, () -> service.removeAppointmentById(2));
    }

    @Test
    void testGetAppointmentById() throws NoEntityFound, NoIdenticalEntities {
        PatientRepo patientRepo = new PatientRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        Service service = new Service(patientRepo, appointmentRepo);
        Appointment appointment = new Appointment(1, new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1), LocalDate.now());
        appointmentRepo.addEntity(appointment);

        assertEquals(appointment, service.getAppointmentById(1));
        assertThrows(NoEntityFound.class, () -> service.getAppointmentById(2));
    }

    @Test
    void testGetAllAppointments() throws NoIdenticalEntities {
        PatientRepo patientRepo = new PatientRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        Service service = new Service(patientRepo, appointmentRepo);
        Appointment appointment1 = new Appointment(1, new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1), LocalDate.now());
        Appointment appointment2 = new Appointment(2, new Patient("Emily Jones", "emilyjones@gmail.com", "Gum Disease", 2), LocalDate.now());

        appointmentRepo.addEntity(appointment1);
        appointmentRepo.addEntity(appointment2);

        Iterable<Appointment> appointments = service.getAllAppointments();
        int count = 0;
        for (Appointment a : appointments) {
            count++;
        }

        assertEquals(2, count);
    }

    @Test
    void testUpdateAppointment() throws NoEntityFound, NoIdenticalEntities {
        PatientRepo patientRepo = new PatientRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        Service service = new Service(patientRepo, appointmentRepo);
        LocalDate newDate = LocalDate.now();
        Appointment appointment = new Appointment(1, new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1), LocalDate.now());
        appointmentRepo.addEntity(appointment);

        assertDoesNotThrow(() -> service.updateAppointment(1, newDate));
        assertEquals(newDate, appointmentRepo.getEntityById(1).getDate());
        assertThrows(NoEntityFound.class, () -> service.updateAppointment(2, newDate));
    }

    @Test
    void testIsPatientAlreadyBooked() throws NoIdenticalEntities {
        PatientRepo patientRepo = new PatientRepo();
        AppointmentRepo appointmentRepo = new AppointmentRepo();
        Service service = new Service(patientRepo, appointmentRepo);

        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        patientRepo.addEntity(patient);
        Appointment appointment = new Appointment(1, patient, LocalDate.now());
        appointmentRepo.addEntity(appointment);

        assertTrue(service.isPatientAlreadyBooked(1));

        assertFalse(service.isPatientAlreadyBooked(2));
    }
}
