package Repository;

import Domain.Patient;

import java.io.*;

public class PatientRepoTextFile extends FileRepo<Patient, Integer>  {
    public PatientRepoTextFile(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line = null;
            while((line = reader.readLine()) != null)
            {
                String[] stringArray = line.split(",");
                if(stringArray.length != 4) {
                    continue;
                } else {
                    Patient patient = new Patient(
                            stringArray[0].trim(),
                            stringArray[1].trim(),
                            stringArray[2].trim(),
                            Integer.parseInt(stringArray[3].trim()));
                    listWithElements.put(Integer.parseInt(stringArray[3].trim()), patient);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeFile() {
        try( BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Iterable<Patient> listOfPatients = getAllEntities();
            for(Patient patient: listOfPatients)
            {
                writer.write(patient.getName() + ", " +
                        patient.getEmail() + ", " +
                        patient.getDisease() + ", " +
                        patient.getId() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
