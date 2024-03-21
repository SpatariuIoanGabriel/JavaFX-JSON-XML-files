package Repository;

import Domain.Patient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientDBRepo extends DatabaseRepository<Patient, Integer> {
    public PatientDBRepo(String tableName) {
        super(tableName);
        getData();
    }

    @Override
    public void getData() {
        try {
            openConnection();
            String selectString = "SELECT * FROM " + tableName + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int patientId = resultSet.getInt("PatientId");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String disease = resultSet.getString("disease");
                    Patient patient = new Patient(name, email, disease, patientId);
                    listWithElements.put(patientId, patient);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addEntity(Patient patient) {
        try {
            openConnection();
            String insertString = "INSERT INTO " + tableName + " (PatientId, name, email, disease) VALUES (?, ?, ?, ?);";
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertString)) {
                preparedStatement.setInt(1, patient.getId());
                preparedStatement.setString(2, patient.getName());
                preparedStatement.setString(3, patient.getEmail());
                preparedStatement.setString(4, patient.getDisease());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removeEntityById(Integer id) {
        try {
            openConnection();
            String deleteString = "DELETE FROM " + tableName + " WHERE PatientId = ?;";
            try (PreparedStatement preparedStatement = conn.prepareStatement(deleteString)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Patient getEntityById(Integer id) {
        try {
            openConnection();
            String getString = "SELECT * FROM " + tableName + " WHERE PatientId = ?;";
            try (PreparedStatement preparedStatement = conn.prepareStatement(getString)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String disease = resultSet.getString("disease");
                    return new Patient(name, email, disease, id);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void updateEntityById(Integer id, Patient newPatient) {
        try {
            openConnection();
            String updateString = "UPDATE " + tableName + " SET name = ?, email = ?, disease = ? WHERE PatientId = ?;";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateString)) {
                preparedStatement.setString(1, newPatient.getName());
                preparedStatement.setString(2, newPatient.getEmail());
                preparedStatement.setString(3, newPatient.getDisease());
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Iterable<Patient> getAllEntities() {
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            openConnection();
            String selectString = "SELECT * FROM " + tableName + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int patientId = resultSet.getInt("PatientId");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String disease = resultSet.getString("disease");
                    Patient patient = new Patient(name, email, disease, patientId);
                    patients.add(patient);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return patients;
    }
}
