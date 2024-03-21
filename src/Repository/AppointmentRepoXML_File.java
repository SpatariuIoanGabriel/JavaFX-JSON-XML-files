package Repository;

import Domain.Appointment;
import Domain.Patient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class AppointmentRepoXML_File extends FileRepo<Appointment, Integer> {

    private PatientRepoXML_File patientRepo;

    public AppointmentRepoXML_File(String filename) {
        super(filename);
    }

    @Override
    public void readFromFile() {
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList appointmentList = doc.getElementsByTagName("appointment");

            for (int i = 0; i < appointmentList.getLength(); i++) {
                Element aElement = (Element) appointmentList.item(i);
                int number = Integer.parseInt(aElement.getAttribute("number"));
                LocalDate date = LocalDate.parse(aElement.getElementsByTagName("date").item(0).getTextContent());

                Element patientElement = (Element) aElement.getElementsByTagName("patient").item(0);
                String name = patientElement.getElementsByTagName("name").item(0).getTextContent();
                String email = patientElement.getElementsByTagName("email").item(0).getTextContent();
                String disease = patientElement.getElementsByTagName("disease").item(0).getTextContent();
                int patientId = Integer.parseInt(patientElement.getAttribute("PatientId"));

                Patient patient = new Patient(name, email, disease, patientId);

                Appointment appointment = new Appointment(number, patient, date);
                listWithElements.put(number, appointment);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void writeFile() {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write("<appointments>\n");
            for (Appointment appointment : listWithElements.values()) {
                fileWriter.write("  <appointment number=\"" + appointment.getId() + "\">\n");

                Patient patient = appointment.getPatient();
                fileWriter.write("      <name>" + patient.getName() + "</name>\n");
                fileWriter.write("      <email>" + patient.getEmail() + "</email>\n");
                fileWriter.write("      <disease>" + patient.getDisease() + "</disease>\n");
                fileWriter.write("    <patient PatientId=\"" + patient.getId() + "\">\n");
                fileWriter.write("    </patient>\n");

                fileWriter.write("    <date>" + appointment.getDate().toString() + "</date>\n");
                fileWriter.write("  </appointment>\n");
            }
            fileWriter.write("</appointments>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
