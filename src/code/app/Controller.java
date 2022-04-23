package code.app;

import code.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.net.URL;
import java.util.List;
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

        fieldAge.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fieldAge.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        fieldSalary.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fieldSalary.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

    }

    public ObservableList<Person> searchPersonList() {
        ObservableList<Person> searchList = FXCollections.observableArrayList();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query<Person> personQuery = session.createQuery("select p from Person as p where name LIKE '%"
                    + fieldSearch.getText() + "%' OR surname LIKE '%" + fieldSearch.getText()
                    + "%' OR address LIKE '%" + fieldSearch.getText() + "%' OR age LIKE '%"
                    + fieldSearch.getText() + "%' OR salary LIKE '%" + fieldSearch.getText() + "%'");
            session.getTransaction().commit();
            List<Person> persons = FXCollections.observableArrayList(personQuery.list());
            searchList.addAll(persons);
            session.close();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            e.getCause().printStackTrace();
        }
        fieldSearch.clear();
        return searchList;
    }

    public ObservableList<Person> getPersonList() {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query<Person> personQuery = session.createQuery("from Person");
            session.getTransaction().commit();
            List<Person> persons = FXCollections.observableArrayList(personQuery.list());
            personList.addAll(persons);
            session.close();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        }
        return personList;
    }

    public void showSearchedPersons() {
        ObservableList<Person> list = searchPersonList();
        columnId.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
        columnAge.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<Person, Integer>("salary"));
        tablePersons.setItems(list);
    }

    public void showPersons() {
        ObservableList<Person> list = getPersonList();
        columnId.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
        columnAge.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<Person, Integer>("salary"));
        tablePersons.setItems(list);
    }

    private void insertPerson() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Person person = new Person();
            person.setName(fieldName.getText());
            person.setSurname(fieldSurname.getText());
            person.setAge(Integer.parseInt(fieldAge.getText()));
            person.setAddress(fieldAddress.getText());
            person.setSalary(Integer.parseInt(fieldSalary.getText()));
            session.save(person);
            session.getTransaction().commit();

        } catch (HibernateException e) {
                System.err.println(e.getMessage());
        }
        clearText();
        showPersons();
    }

    private void updatePerson() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Person retrievedPerson = session.get(Person.class, Integer.parseInt(fieldId.getText()));
            retrievedPerson.setName(fieldName.getText());
            retrievedPerson.setSurname(fieldSurname.getText());
            retrievedPerson.setAge(Integer.parseInt(fieldAge.getText()));
            retrievedPerson.setAddress(fieldAddress.getText());
            retrievedPerson.setSalary(Integer.parseInt(fieldSalary.getText()));
            session.update(retrievedPerson);
            session.getTransaction().commit();
        } catch (HibernateException e) {
                System.err.println(e.getMessage());
        }
        clearText();
        showPersons();
    }

    private void deletePerson() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Person retrievedPerson = session.get(Person.class, Integer.parseInt(fieldId.getText()));
            session.delete(retrievedPerson);
            session.getTransaction().commit();
        } catch (HibernateException e) {
                System.err.println(e.getMessage());
        }
        clearText();
        showPersons();
    }

    private void clearText(){
        fieldId.clear();
        fieldName.clear();
        fieldSurname.clear();
        fieldAge.clear();
        fieldAddress.clear();
        fieldSalary.clear();
    }

    @FXML
    private void handleMouseAction(MouseEvent mouseEvent) {
        if (!tablePersons.getSelectionModel().isEmpty()) {
            Person person = tablePersons.getSelectionModel().getSelectedItem();
            fieldId.setText("" + person.getId());
            fieldName.setText(person.getName());
            fieldSurname.setText(person.getSurname());
            fieldAge.setText("" + person.getAge());
            fieldAddress.setText(person.getAddress());
            fieldSalary.setText("" + person.getSalary());
        }
    }

}
