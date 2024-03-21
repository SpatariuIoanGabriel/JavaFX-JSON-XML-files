package Domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void testConstructor() {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        assertEquals("Alex Smith", patient.getName());
        assertEquals("alexsmith@gmail.com", patient.getEmail());
        assertEquals("Tooth Decay", patient.getDisease());
        assertEquals(1, patient.getId());
    }

    @Test
    void testGettersAndSetters() {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        patient.setName("Emily Jones");
        assertEquals("Emily Jones", patient.getName());

        patient.setEmail("emilyjones@gmail.com");
        assertEquals("emilyjones@gmail.com", patient.getEmail());

        patient.setDisease("Gum Disease");
        assertEquals("Gum Disease", patient.getDisease());

        patient.setId(2);
        assertEquals(2, patient.getId());
    }

    @Test
    void testEquals() {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        Patient patient2 = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        Patient patient3 = new Patient("Emily Jones", "emilyjones@gmail.com", "Gum Disease", 2);

        assertEquals(patient, patient);
        assertEquals(patient, patient2);
        assertNotEquals(patient, patient3);
        assertNotEquals(patient, new Object());
    }

    @Test
    void testToString() {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        String expected = "Patient{name = Alex Smith, email = alexsmith@gmail.com, disease = Tooth Decay, PatientId = 1}";
        assertEquals(expected, patient.toString());
    }
}
