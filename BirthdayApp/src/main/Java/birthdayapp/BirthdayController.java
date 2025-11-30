package birthdayapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class BirthdayController {

    @FXML
    private TableView<Birthday> table;

    @FXML
    private TableColumn<Birthday, String> colName;

    @FXML
    private TableColumn<Birthday, String> colDate;

    @FXML
    private TextField searchField;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadTable();
    }

    public void loadTable() {
        ObservableList<Birthday> list = BirthdayDAO.getAll();
        table.setItems(list);
    }

    @FXML
    public void add() {
        BirthdayDAO.insert("নতুন নাম", "2000-01-01");
        loadTable();
    }

    @FXML
    public void delete() {
        Birthday selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            BirthdayDAO.delete(selected.getId());
            loadTable();
        }
    }

    @FXML
    public void refresh() {
        loadTable();
    }
}
