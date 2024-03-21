package Repository;

import Domain.Patient;
import Exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class MemoryRepoTest {

    MemoryRepository<Patient, Integer> repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryRepository<>();
    }

    @Test
    void testAddEntity() {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);

        assertDoesNotThrow(() -> repository.addEntity(patient));

        assertThrows(NoIdenticalEntities.class, () -> repository.addEntity(patient));
    }

    @Test
    void testRemoveEntityById() throws NoIdenticalEntities {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        repository.addEntity(patient);

        assertDoesNotThrow(() -> repository.removeEntityById(1));

        assertThrows(NoEntityFound.class, () -> repository.removeEntityById(2));
    }

    @Test
    void testGetEntityById() throws NoIdenticalEntities, NoEntityFound {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        repository.addEntity(patient);

        assertEquals(patient, repository.getEntityById(1));

        assertThrows(NoEntityFound.class, () -> repository.getEntityById(2));
    }

    @Test
    void testGetAllEntities() throws NoIdenticalEntities {
        Patient patient1 = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        Patient patient2 = new Patient("Emily Jones", "emilyjones@gmail.com", "Gum Disease", 2);

        repository.addEntity(patient1);
        repository.addEntity(patient2);

        Iterable<Patient> patients = repository.getAllEntities();

        Map<Integer, Patient> patientMap = new HashMap<>();
        for (Patient patient : patients) {
            patientMap.put(patient.getId(), patient);
        }

        assertTrue(patientMap.containsKey(1));
        assertTrue(patientMap.containsKey(2));
    }

    @Test
    void testUpdateEntityById() throws NoIdenticalEntities {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        repository.addEntity(patient);

        Patient updatedPatient = new Patient("Emily Jones", "emilyjones@gmail.com", "Gum Disease", 2);

        assertDoesNotThrow(() -> repository.updateEntityById(1, updatedPatient));

        assertThrows(NoEntityFound.class, () -> repository.updateEntityById(2, updatedPatient));
    }
}
