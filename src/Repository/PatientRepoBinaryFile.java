package Repository;

import Domain.Patient;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class PatientRepoBinaryFile extends FileRepo<Patient,Integer> {
    public PatientRepoBinaryFile(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            this.listWithElements = (Map<Integer, Patient>) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            oos.writeObject(this.listWithElements);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}