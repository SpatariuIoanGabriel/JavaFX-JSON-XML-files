package Repository;

import Domain.Patient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PatientRepoJsonFile extends FileRepo<Patient, Integer> {

    public PatientRepoJsonFile(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        try (FileReader reader = new FileReader(fileName)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray patients = (JSONArray) jsonParser.parse(reader);

            for (Object o : patients) {
                JSONObject patientObject = (JSONObject) o;
                String name = (String) patientObject.get("1.name");
                String email = (String) patientObject.get("2.email");
                String disease = (String) patientObject.get("3.disease");
                int id = Math.toIntExact((Integer) patientObject.get("4.patientId"));

                Patient patient = new Patient(name, email, disease, id);
                listWithElements.put(id, patient);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeFile() {
        JSONArray patients = new JSONArray();
        for (Patient patient : listWithElements.values()) {
            JSONObject obj = new JSONObject();
            obj.put("1.name", patient.getName());
            obj.put("2.email", patient.getEmail());
            obj.put("3.disease", patient.getDisease());
            obj.put("4.patientId", patient.getId());

            patients.add(obj);
        }

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(patients.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}