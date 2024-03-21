package Repository;

import Domain.Appointment;
import Domain.Patient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class AppointmentRepoJsonFile extends FileRepo<Appointment, Integer> {

    public AppointmentRepoJsonFile(String filename) {
        super(filename);
    }

    @Override
    public void readFromFile() {
        try (FileReader reader = new FileReader(fileName)) {
            JSONParser jsonParser = new JSONParser(); //analyse data
            JSONArray appointments = (JSONArray) jsonParser.parse(reader);

            for (Object o : appointments) {
                JSONObject appointmentObject = (JSONObject) o;
                int number = Math.toIntExact((Integer) appointmentObject.get("1_Number"));
                JSONObject patientObject = (JSONObject) appointmentObject.get("2_Patient");
                LocalDate date = LocalDate.parse((String) appointmentObject.get("2_Date"));

                Patient patient = new Patient(
                        (String) patientObject.get("1.name"),
                        (String) patientObject.get("2.email"),
                        (String) patientObject.get("3.disease"),
                        Math.toIntExact((Integer) patientObject.get("4.patientId"))
                );

                Appointment appointment = new Appointment(number, patient, date);
                listWithElements.put(number, appointment);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeFile() {
        JSONArray appointmentsArray = new JSONArray();
        for (Appointment appointment : listWithElements.values()) {
            JSONObject object = new JSONObject();
            JSONObject patientObject = new JSONObject();

            patientObject.put("1.name", appointment.getPatient().getName());
            patientObject.put("2.email", appointment.getPatient().getEmail());
            patientObject.put("3.disease", appointment.getPatient().getDisease());
            patientObject.put("4.patientId", appointment.getPatient().getId());

            object.put("1_Number", appointment.getId());
            object.put("2_Patient", patientObject);
            object.put("3_Date", appointment.getDate().toString());

            appointmentsArray.add(object);
        }

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(appointmentsArray.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

