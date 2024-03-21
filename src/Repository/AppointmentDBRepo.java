package Repository;

import Domain.Appointment;
import Domain.Patient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentDBRepo extends DatabaseRepository<Appointment, Integer> {

    public AppointmentDBRepo(String tableName) {
        super(tableName);
        getData();
    }

    @Override
    public void getData() {
        try {
            openConnection();
            String selectString = "SELECT a.number, a.date, p.PatientId, p.name, p.email, p.disease " +
                    "FROM " + tableName + " a " +
                    "JOIN patients p ON a.PatientId = p.PatientId;";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int number = resultSet.getInt("number");
                    LocalDate date = LocalDate.parse(resultSet.getString("date"));
                    int patientId = resultSet.getInt("PatientId");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String disease = resultSet.getString("disease");

                    Patient patient = new Patient(name, email, disease, patientId);
                    Appointment appointment = new Appointment(number, patient, date);
                    listWithElements.put(number, appointment);
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
    public void addEntity(Appointment item) {
        try {
            openConnection();
            String insertString = "INSERT INTO " + tableName + " (number, PatientId, date) VALUES (?, ?, ?);";
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertString)) {
                preparedStatement.setInt(1, item.getId());
                preparedStatement.setInt(2, item.getPatient().getId());
                preparedStatement.setString(3, item.getDate().toString());
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
            String deleteString = "DELETE FROM " + tableName + " WHERE number = ?;";
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
    public Appointment getEntityById(Integer id) {
        Appointment appointment = null;
        try {
            openConnection();
            String getString = "SELECT a.number, a.date, p.PatientId, p.name, p.email, p.disease " +
                    "FROM " + tableName + " a " +
                    "JOIN patients p ON a.PatientId = p.PatientId " +
                    "WHERE a.number = ?;";
            try (PreparedStatement preparedStatement = conn.prepareStatement(getString)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int number = resultSet.getInt("number");
                    LocalDate date = LocalDate.parse(resultSet.getString("date"));
                    Patient patient = new Patient(resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("disease"),
                            resultSet.getInt("PatientId"));
                    appointment = new Appointment(number, patient, date);
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
        return appointment;
    }

    @Override
    public void updateEntityById(Integer id, Appointment newAppointment) {
        try {
            openConnection();
            String updateString = "UPDATE " + tableName + " SET date = ?, PatientId = ? WHERE number = ?;";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateString)) {
                preparedStatement.setString(1, newAppointment.getDate().toString());
                preparedStatement.setInt(2, newAppointment.getPatient().getId());
                preparedStatement.setInt(3, id);
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
    public Iterable<Appointment> getAllEntities() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try {
            openConnection();
            String selectString = "SELECT a.number, a.date, p.PatientId, p.name, p.email, p.disease " +
                    "FROM " + tableName + " a " +
                    "JOIN patients p ON a.PatientId = p.PatientId;";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int number = resultSet.getInt("number");
                    LocalDate date = LocalDate.parse(resultSet.getString("date"));
                    Patient patient = new Patient(resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("disease"),
                            resultSet.getInt("PatientId"));
                    Appointment appointment = new Appointment(number, patient, date);
                    appointments.add(appointment);
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
        return appointments;
    }
}
