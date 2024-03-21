package Repository;

import Domain.Appointment;
import java.io.*;
import java.util.Map;

public class AppointmentRepoBinaryFile extends FileRepo<Appointment,Integer> {
    public AppointmentRepoBinaryFile(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            this.listWithElements = (Map<Integer, Appointment>) ois.readObject();
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