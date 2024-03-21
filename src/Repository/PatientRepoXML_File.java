package Repository;

import Domain.Patient;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PatientRepoXML_File extends FileRepo<Patient, Integer> {

    public PatientRepoXML_File(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); // create xml instance
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize(); // normalize eliminates empty nodes

            NodeList patientList = doc.getElementsByTagName("patient");

            for (int i = 0; i < patientList.getLength(); i++) {
                Node node = patientList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element pElement = (Element) node;
                    String name = pElement.getElementsByTagName("name").item(0).getTextContent();
                    String email = pElement.getElementsByTagName("email").item(0).getTextContent();
                    String disease = pElement.getElementsByTagName("disease").item(0).getTextContent();
                    int id = Integer.parseInt(pElement.getElementsByTagName("PatientId").item(0).getTextContent());

                    Patient patient = new Patient(name, email, disease, id);
                    listWithElements.put(id, patient);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeFile() {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write("<patients>\n");
            for (Patient patient : listWithElements.values()) {
                fileWriter.write("  <patient>\n");
                fileWriter.write("    <name>" + patient.getName() + "</name>\n");
                fileWriter.write("    <email>" + patient.getEmail() + "</email>\n");
                fileWriter.write("    <disease>" + patient.getDisease() + "</disease>\n");
                fileWriter.write("    <PatientId>" + patient.getId() + "</PatientId>\n");
                fileWriter.write("  </patient>\n");
            }
            fileWriter.write("</patients>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
