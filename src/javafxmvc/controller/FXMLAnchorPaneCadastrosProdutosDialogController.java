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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.dao.CategoriaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Categoria;
import javafxmvc.model.domain.Produto;


public class FXMLAnchorPaneCadastrosProdutosDialogController implements Initializable {
    @FXML
    private TextField textFieldProdutoNome;

    @FXML
    private TextField textFieldProdutoPreco;

    @FXML
    private TextField textFieldProdutoQuantidade;

    @FXML
    private ComboBox<Categoria> comboBoxCategoria;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonCancelar;
    
    private List<Categoria> listCategorias;
    private ObservableList<Categoria> observableListCategorias;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Produto produto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoriaDAO.setConnection(connection);
        carregarComboBoxCategorias();
    }
    
    public void carregarComboBoxCategorias(){
        listCategorias = categoriaDAO.listar();
        observableListCategorias = FXCollections.observableArrayList(listCategorias);
        comboBoxCategoria.setItems(observableListCategorias);
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        this.textFieldProdutoNome.setText(produto.getNome());
        this.textFieldProdutoPreco.setText(String.valueOf(produto.getPreco()));
        this.textFieldProdutoQuantidade.setText(String.valueOf(produto.getQuantidade()));
        this.comboBoxCategoria.getSelectionModel().select(produto.getCategoria());
    }
    
    @FXML
    public void handleButtonConfirmar(){
        if(validarEntradaDeDados()) {
            produto.setNome(textFieldProdutoNome.getText());
            produto.setPreco(Double.parseDouble(textFieldProdutoPreco.getText()));
            produto.setQuantidade(Integer.parseInt(textFieldProdutoQuantidade.getText()));
            produto.setCategoria(comboBoxCategoria.getSelectionModel().getSelectedItem());
        
            buttonConfirmarClicked = true;
            dialogStage.close();
        }

    }
    
    @FXML
    public void handleButtonCancelar(){
        dialogStage.close();
    }

    
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        
        if(textFieldProdutoNome.getText() == null || textFieldProdutoNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if(textFieldProdutoPreco.getText() == null || textFieldProdutoPreco.getText().length() == 0) {
            errorMessage += "Preço inválido!\n";
        }
        if(textFieldProdutoQuantidade.getText() == null || textFieldProdutoQuantidade.getText().length() == 0) {
            errorMessage += "Quantidade inválida!\n";
        }
        if(comboBoxCategoria.getSelectionModel().getSelectedItem() == null){
            errorMessage += "Categoria inválida!\n";
        }
        
        if(errorMessage.length() == 0){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
}
