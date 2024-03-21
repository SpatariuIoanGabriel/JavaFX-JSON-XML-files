package Domain;

import java.util.Objects;
import java.io.Serializable;

public class Patient implements Identifiable<Integer>, Serializable{
    private String name;
    private String email;
    private String disease;
    private int PatientId;

    public Patient(String name, String email, String disease, int patientId) {
        this.name = name;
        this.email = email;
        this.disease = disease;
        this.PatientId = patientId;
    }

    @Override
    public Integer getId() {return this.PatientId;}

    @Override
    public void setId(Integer id) {this.PatientId = id;}

    public String getDisease() {return this.disease;}

    public void setDisease(String disease) {this.disease = disease;}

    public String getName() {return this.name;}

    public void setName(String name) {this.name = name;}

    public String getEmail() {return this.email;}

    public void setEmail(String email) {this.email = email;}

    @Override
    public String toString() {
        return "Patient{" +
                "name = " + name +
                ", email = " + email +
                ", disease = " + disease +
                ", PatientId = " + PatientId +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o.getClass() != Patient.class)
            return false;
        Patient p = (Patient) o;
        return Objects.equals(p.PatientId, this.PatientId);
    }
}
