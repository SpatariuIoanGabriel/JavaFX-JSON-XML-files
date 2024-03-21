package Repository;

import Domain.Patient;
import Exception.NoEntityFound;
import Exception.NoIdenticalEntities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileRepoTest {
    private FileRepo patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository = new PatientRepoTextFile("testPatients.txt");
    }

    @Test
    void readFromFile(){
    }

    @Test
    void writeFile(){
    }

    @Test
    void addEntity() throws NoIdenticalEntities, NoEntityFound {
        Patient patient = new Patient("Alex Smith", "alexsmith@gmail.com", "Tooth Decay", 1);
        patientRepository.addEntity(patient);
        assertEquals(patient, patientRepository.getEntityById(1));
    }

    @Test
    void addDuplicateEntity() {
        Patient patient = new Patient("Jane Larson", "jane123@gmail.com", "Flu", 2);
        assertThrows(NoIdenticalEntities.class, () -> {
            patientRepository.addEntity(patient);
            patientRepository.addEntity(patient);
        });
    }

    @Test
    void removeEntityById() throws NoIdenticalEntities, NoEntityFound {
        Patient patient = new Patient("Alice Brian", "alicebrian@gmail.com", "Tooth Decay", 2);
        patientRepository.addEntity(patient);
        patientRepository.removeEntityById(2);
        assertThrows(NoEntityFound.class, () -> patientRepository.getEntityById(3));
    }

    @Test
    void updateEntityById() throws NoIdenticalEntities, NoEntityFound {
        Patient patient2 = new Patient("Bob Johnson", "bob123@gemail.com", "Gum Disease", 3);
        patientRepository.addEntity(patient2);

        Patient updatedPatient = new Patient("Alice Mitchel", "alice123@gmail.com", "Broken Tooth", 3);
        patientRepository.updateEntityById(3, updatedPatient);
    }
}