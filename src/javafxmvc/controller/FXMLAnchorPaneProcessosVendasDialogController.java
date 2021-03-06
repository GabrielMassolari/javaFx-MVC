package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxmvc.model.dao.ClienteDAO;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.ItemDeVenda;
import javafxmvc.model.domain.Produto;
import javafxmvc.model.domain.Venda;


public class FXMLAnchorPaneProcessosVendasDialogController implements Initializable {
    @FXML
    private ComboBox<Cliente> comboBoxVendaCliente;
    
    @FXML
    private DatePicker datePickerVendaData;
    
    @FXML
    private CheckBox checkBoxVendaPago;
    
    @FXML
    private TableView<ItemDeVenda> tableViewItensDeVenda;
    
    @FXML
    private TableColumn<ItemDeVenda, Produto> tableColumnItemDeVendaProduto;
    
    @FXML
    private TableColumn<ItemDeVenda, Integer> tableColumnItemDeVendaQuantidade;
    
    @FXML
    private TableColumn<ItemDeVenda, Double> tableColumnItemDeVendaValor;
    
    @FXML
    private TextField textFieldVendaValor;
    
    @FXML
    private ComboBox<Produto> comboBoxVendaProduto;
    
    @FXML
    private TextField textFieldVendaItemDeVendaQuantidade;
    
    @FXML
    private Button buttonAdicionar;
    
    @FXML
    private Button buttonRemover;
   
    @FXML
    private Button buttonConfirmar;
    
    @FXML
    private Button buttonCancelar;
    
     private List<Cliente> listClientes;
     private List<Produto> listProdutos;
     private ObservableList<Cliente> observableListClientes;
     private ObservableList<Produto> observableListProdutos;
     private ObservableList<ItemDeVenda> observableListItensDeVenda;
     
     private final Database database = DatabaseFactory.getDatabase("postgresql");
     private final Connection connection = database.conectar();
     private final ClienteDAO clienteDAO = new ClienteDAO();
     private final ProdutoDAO produtoDAO = new ProdutoDAO();
     
     private Stage dialogStage;
     private boolean buttonConfirmarClicked = false;
     private Venda venda;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        produtoDAO.setConnection(connection);
        carregarComboBoxClientes();
        carregarComboBoxProdutos();
        tableColumnItemDeVendaProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        tableColumnItemDeVendaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnItemDeVendaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));    
    }
    
    public void carregarComboBoxClientes(){
        listClientes = clienteDAO.listar();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxVendaCliente.setItems(observableListClientes);
    }
    
    public void carregarComboBoxProdutos(){
        listProdutos = produtoDAO.listar();
        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        comboBoxVendaProduto.setItems(observableListProdutos);
    }
    
    
    
    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public Venda getVenda(){
        return this.venda;
    }
    
   public void setVenda(Venda venda){
       this.venda = venda;
       
       comboBoxVendaCliente.getSelectionModel().select(venda.getCliente());
       datePickerVendaData.setValue(venda.getData());
       checkBoxVendaPago.setSelected(venda.getPago());
       observableListItensDeVenda = FXCollections.observableArrayList(venda.getItensDeVenda());
       tableViewItensDeVenda.setItems(observableListItensDeVenda);
       textFieldVendaValor.setText(String.format("%.2f", venda.getValor()));
   }
    
    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }
    
    @FXML
    public void handleButtonAdicionar() {
        Produto produto;
        ItemDeVenda itemDeVenda = new ItemDeVenda();
        if(comboBoxVendaProduto.getSelectionModel().getSelectedItem() != null) {
            produto = (Produto) comboBoxVendaProduto.getSelectionModel().getSelectedItem();
            if(produto.getQuantidade() >= Integer.parseInt(textFieldVendaItemDeVendaQuantidade.getText())){
                itemDeVenda.setProduto((Produto) comboBoxVendaProduto.getSelectionModel().getSelectedItem());
                itemDeVenda.setQuantidade(Integer.parseInt(textFieldVendaItemDeVendaQuantidade.getText()));
                itemDeVenda.setValor(itemDeVenda.getProduto().getPreco() * itemDeVenda.getQuantidade());
                venda.getItensDeVenda().add(itemDeVenda);
                venda.setValor(venda.getValor() + itemDeVenda.getValor());
                observableListItensDeVenda = FXCollections.observableArrayList(venda.getItensDeVenda());
                tableViewItensDeVenda.setItems(observableListItensDeVenda);
                textFieldVendaValor.setText(String.format("%.2f", venda.getValor()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Problemas na escolha do produto!");
                alert.setContentText("N??o existe a quantidade de produtos dispon??veis no estoque!");
                alert.show();
            }
        }
    }
    
    @FXML
    public void handleButtonRemover(){
        ItemDeVenda itemDeVenda = tableViewItensDeVenda.getSelectionModel().getSelectedItem();
        venda.getItensDeVenda().remove(itemDeVenda);
        venda.setValor(venda.getValor() - itemDeVenda.getValor());
        observableListItensDeVenda = FXCollections.observableArrayList(venda.getItensDeVenda());
        tableViewItensDeVenda.setItems(observableListItensDeVenda);
        textFieldVendaValor.setText(String.format("%.2f", venda.getValor()));
    }
    
    @FXML
    public void handleButtonConfirmar(){
        if (validarEntradaDeDados()){
            venda.setCliente((Cliente) comboBoxVendaCliente.getSelectionModel().getSelectedItem());
            venda.setPago(checkBoxVendaPago.isSelected());
            venda.setData(datePickerVendaData.getValue());
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }
    
    private boolean validarEntradaDeDados(){
        String errorMessage = "";
        
        if(comboBoxVendaCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente inv??lido!\n";
        }
        if(datePickerVendaData.getValue() == null) {
            errorMessage += "Data inv??lida!\n";
        }
        if(observableListItensDeVenda == null) {
            errorMessage += "Itens de Venda inv??lidos!\n";
        }
        
        if(errorMessage.length() == 0){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inv??lidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }  
}
