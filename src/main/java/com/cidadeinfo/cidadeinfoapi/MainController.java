package com.cidadeinfo.cidadeinfoapi;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.cidadeinfo.cidadeinfoapi.fileHandling.FileHandling;
import com.cidadeinfo.cidadeinfoapi.apiHandling.ApiHandling;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONObject;

import java.util.List;

public class MainController {
    @FXML
    private TextField ufTextField;
    @FXML
    private Button submitButton;
    @FXML
    private Button submitButton1;
    @FXML
    private Label alertLabel;
    @FXML
    private Label alertLabel1;
    @FXML
    private TableView<Cidade> tableView;
    @FXML
    private TableColumn <Cidade, Long> id;
    @FXML
    private TableColumn <Cidade, String> nome;

    private ApiHandling apiHandling = new ApiHandling();
    private FileHandling fileHandling = new FileHandling(apiHandling);

    public void submit(ActionEvent event) throws Exception {
        try {
            tableView.getItems().clear();
            apiHandling.setUF(ufTextField.getText());
            JSONObject obj = apiHandling.getEstado();
            tabela();
            alertLabel.setStyle("-fx-text-fill: #00e500;");
            alertLabel.setText("Buscando Dados: "+ obj.get("nome")+", ID do Estado: "+ obj.get("id")+", Sigla do Estado: "+obj.get("sigla"));
        } catch (Exception e) {
            alertLabel.setStyle("-fx-text-fill: #ff0000;");
            alertLabel.setText(String.valueOf(e.getMessage()));
        }
    }

    public void CSVButton(ActionEvent event) throws Exception {
        try {
            fileHandling.createCSV();
            alertLabel1.setStyle("-fx-text-fill: #00e500;");
            alertLabel1.setText("Arquivo CSV Criado Com Sucesso");
        } catch (Exception e) {
            alertLabel1.setStyle("-fx-text-fill: #ff0000;");
            alertLabel1.setText(String.valueOf(e.getMessage()));
        }
    }

    public void tabela() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Cidade> cities = mapper.readValue(apiHandling.getCidades().toJSONString(),
                new TypeReference<>() {
                });
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        tableView.getItems().addAll(cities);
        tableView.sort();
    }


}
