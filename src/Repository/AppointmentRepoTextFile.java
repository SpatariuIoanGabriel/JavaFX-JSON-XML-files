package Repository;

import Domain.Appointment;
import Domain.Patient;

import java.io.*;
import java.time.LocalDate;

public class AppointmentRepoTextFile extends FileRepo<Appointment, Integer> {
    public AppointmentRepoTextFile(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] stringOfData = line.split(",");
                if (stringOfData.length != 6) {
                    continue;
                } else {
                    int appointmentNumber = Integer.parseInt(stringOfData[0].trim());
                    String patientName = stringOfData[1].trim();
                    String email = stringOfData[2].trim();
                    String disease = stringOfData[3].trim();
                    int patientId = Integer.parseInt(stringOfData[4].trim());
                    LocalDate dateOfAppointment = LocalDate.parse(stringOfData[5].trim());

                    Appointment appointmentRead = new Appointment(
                            appointmentNumber,
                            new Patient(patientName, email, disease, patientId),
                            dateOfAppointment);

                    listWithElements.put(appointmentNumber, appointmentRead);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Iterable<Appointment> listOfAppointments = getAllEntities();
            for (Appointment appointment : listOfAppointments) {
                writer.write(
                            appointment.getId() + ", " +
                                appointment.getPatient().getName() + ", " +
                                appointment.getPatient().getEmail() + ", " +
                                appointment.getPatient().getDisease() + ", " +
                                appointment.getPatient().getId() + ", " +
                                appointment.getDate()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

