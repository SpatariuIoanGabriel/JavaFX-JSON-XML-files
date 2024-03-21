package Domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class AppointmentTest {

    @Test
    void testConstructor() {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        LocalDate date = LocalDate.of(2023, 1, 1);
        Appointment appointment = new Appointment(1, patient, date);

        assertNotNull(appointment);
        assertEquals(1, appointment.getId());
        assertEquals(patient, appointment.getPatient());
        assertEquals(date, appointment.getDate());
    }

    @Test
    void testGetAndSetId() {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        LocalDate date = LocalDate.now();
        Appointment appointment = new Appointment(1, patient, date);

        assertEquals(1, appointment.getId());

        appointment.setId(2);
        assertEquals(2, appointment.getId());
    }

    @Test
    void testGetAndSetPatient() {
        Patient patient1 = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        Patient patient2 = new Patient("Emily Jones", "emilyjones@gmail.com", "Gum Disease", 2);
        LocalDate date = LocalDate.now();
        Appointment appointment = new Appointment(1, patient1, date);

        assertEquals(patient1, appointment.getPatient());

        appointment.setPatient(patient2);
        assertEquals(patient2, appointment.getPatient());
    }

    @Test
    void testGetAndSetDate() {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2023, 12, 31);
        Appointment appointment = new Appointment(1, patient, date1);

        assertEquals(date1, appointment.getDate());

        appointment.setDate(date2);
        assertEquals(date2, appointment.getDate());
    }

    @Test
    void testToString() {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        LocalDate date = LocalDate.of(2023, 1, 1);
        Appointment appointment = new Appointment(1, patient, date);

        String expected = "Appointment{number: 1, " + patient.toString() + " , date: " + date + "}";
        assertEquals(expected, appointment.toString());
    }

    @Test
    void testEquals() {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        LocalDate date = LocalDate.of(2023, 1, 1);
        Appointment appointment1 = new Appointment(1, patient, date);
        Appointment appointment2 = new Appointment(1, patient, date);
        Appointment appointment3 = new Appointment(2, patient, date);

        assertEquals(appointment1, appointment2);
        assertEquals(appointment1, appointment1);
        assertNotEquals(appointment1, appointment3);
        assertNotEquals(appointment1, new Object());
    }
}
