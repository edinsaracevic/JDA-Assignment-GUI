package app;

import connection.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Person;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button buttonLoadDatabase;
    @FXML
    private Label labelID;
    @FXML
    private Label labelName;
    @FXML
    private Label labelSurname;
    @FXML
    private Label labelAge;
    @FXML
    private Label labelAddress;
    @FXML
    private Label labelSalary;
    @FXML
    private TextField fieldId;
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldSurname;
    @FXML
    private TextField fieldAge;
    @FXML
    private TextField fieldAddress;
    @FXML
    private TextField fieldSalary;
    @FXML
    private TableView<Person> tablePersons;
    @FXML
    private TableColumn<Person, Integer> columnId;
    @FXML
    private TableColumn<Person, String> columnName;
    @FXML
    private TableColumn<Person, String> columnSurname;
    @FXML
    private TableColumn<Person, Integer> columnAge;
    @FXML
    private TableColumn<Person, String> columnAddress;
    @FXML
    private TableColumn<Person, Integer> columnSalary;
    @FXML
    private Button buttonInsert;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonDelete;
    @FXML
    private Label labelMessage;
    @FXML
    private TextField fieldSearch;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonExit;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == buttonInsert) {
            String message = "Please enter values in all fields";
            if (fieldName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, message);
            } else if (fieldSurname.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, message);
            } else if (fieldAge.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, message);
            } else if (fieldAddress.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, message);
            } else if (fieldSalary.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, message);
            } else {
                insertPerson();
            }
        } else if (event.getSource() == buttonUpdate) {
            String message = "Please enter values in all fields";
            if (fieldName.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, message);
            } else if (fieldSurname.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, message);
            } else if (fieldAge.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, message);
            } else if (fieldAddress.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, message);
            } else if (fieldSalary.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, message);
            } else {
                updatePerson();
            }
        } else if (event.getSource() == buttonDelete) {
            if (fieldId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "What are you trying to delete?");
            } else {
                deletePerson();
            }
        } else if (event.getSource() == buttonLoadDatabase) {
            showPersons();
        } else if (event.getSource() == buttonSearch) {
            if (fieldSearch.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Can't search for nothing, right?");
            } else {
                showSearchedPersons();
            }
        } else if (event.getSource() == buttonExit) {
            JOptionPane.showMessageDialog(null, "This should be grade A, right? Thank you Dušan :)");
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showPersons();
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(
                    MyConnection.URL.getValue(),
                    MyConnection.USERNAME.getValue(),
                    MyConnection.PASSWORD.getValue());
            return conn;
        } catch (SQLException exception) {
            exception.getMessage();
            return null;
        }
    }

    public ObservableList<Person> searchPersonList() {
        ObservableList<Person> searchList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM person WHERE name LIKE '%" + fieldSearch.getText() + "%' OR surname LIKE '%" + fieldSearch.getText()
                + "%' OR address LIKE '%" + fieldSearch.getText() + "%' OR age LIKE '%" + fieldSearch.getText()
                + "%' OR salary LIKE '%" + fieldSearch.getText() + "%'";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Person person;
            while (rs.next()) {
                person = new Person(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Surname"),
                        rs.getInt("Age"),
                        rs.getString("Address"),
                        rs.getInt("Salary"));
                searchList.add(person);
            }
        } catch (SQLException exception) {
            exception.getMessage();
        }
        fieldSearch.clear();
        return searchList;
    }

    public ObservableList<Person> getPersonList() {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM person";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Person person;
            while (rs.next()) {
                person = new Person(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Surname"),
                        rs.getInt("Age"),
                        rs.getString("Address"),
                        rs.getInt("Salary"));
                personList.add(person);
            }
        } catch (SQLException exception) {
            exception.getMessage();
        }
        return personList;
    }

    public void showSearchedPersons() {
        ObservableList<Person> list = searchPersonList();
        columnId.setCellValueFactory(new PropertyValueFactory<Person, Integer>("personId"));
        columnName.setCellValueFactory(new PropertyValueFactory<Person, String>("personName"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<Person, String>("personSurname"));
        columnAge.setCellValueFactory(new PropertyValueFactory<Person, Integer>("personAge"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<Person, String>("personAddress"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<Person, Integer>("personSalary"));
        tablePersons.setItems(list);
    }

    public void showPersons() {
        ObservableList<Person> list = getPersonList();
        columnId.setCellValueFactory(new PropertyValueFactory<Person, Integer>("personId"));
        columnName.setCellValueFactory(new PropertyValueFactory<Person, String>("personName"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<Person, String>("personSurname"));
        columnAge.setCellValueFactory(new PropertyValueFactory<Person, Integer>("personAge"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<Person, String>("personAddress"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<Person, Integer>("personSalary"));
        tablePersons.setItems(list);
    }

    private void insertPerson() {
        String query = "INSERT INTO person VALUES (" + null + ",'" + fieldName.getText() + "','" + fieldSurname.getText() + "','" +
                fieldAge.getText() + "','" + fieldAddress.getText() + "','" + fieldSalary.getText() + "')";
        executeQuery(query);
        showPersons();
    }

    private void updatePerson() {
        String query = "UPDATE person SET name = '" + fieldName.getText() + "', surname = '" + fieldSurname.getText()
                + "', age = " + fieldAge.getText() + ", address = '" + fieldAddress.getText() + "', salary = " + fieldSalary.getText()
                + " WHERE id = " + fieldId.getText();
        executeQuery(query);
        showPersons();
    }

    private void deletePerson() {
        String query = "DELETE FROM person WHERE id = " + fieldId.getText() + "";
        executeQuery(query);
        showPersons();
    }

    @FXML
    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException exception) {
            exception.getMessage();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent mouseEvent) {
        if (!tablePersons.getSelectionModel().isEmpty()) {
            Person person = tablePersons.getSelectionModel().getSelectedItem();
            fieldId.setText("" + person.getPersonId());
            fieldName.setText(person.getPersonName());
            fieldSurname.setText(person.getPersonSurname());
            fieldAge.setText("" + person.getPersonAge());
            fieldAddress.setText(person.getPersonAddress());
            fieldSalary.setText("" + person.getPersonSalary());
        }
    }
}
