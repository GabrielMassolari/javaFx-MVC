package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Produto;

public class FXMLAnchorPaneCadastrosProdutosController implements Initializable {
    @FXML
    private ListView<Produto> listViewProduto;

    @FXML
    private Label labelProdutoCodigo;

    @FXML
    private Label labelProdutoNome;

    @FXML
    private Label labelProdutoPreco;

    @FXML
    private Label labelProdutoQuantidade;

    @FXML
    private Label labelProdutoCategoria;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonRemover;

    private List<Produto> listProdutos;
    private ObservableList<Produto> observableListProdutos;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoDAO.setConnection(connection);
        carregarListViewProduto();
        
        listViewProduto.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemListViewProduto(newValue));
    }

    public void carregarListViewProduto(){
        listProdutos = produtoDAO.listar();
        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        listViewProduto.setItems(observableListProdutos);
    }
    
    public void selecionarItemListViewProduto(Produto produto){
        if(produto != null){
            labelProdutoCodigo.setText(String.valueOf(produto.getCdProduto()));
            labelProdutoNome.setText(produto.getNome());
            labelProdutoPreco.setText(String.valueOf(produto.getPreco()));
            labelProdutoQuantidade.setText(String.valueOf(produto.getQuantidade()));
            labelProdutoCategoria.setText(produto.getCategoria().toString());
        }else{
            labelProdutoCodigo.setText("");
            labelProdutoNome.setText("");
            labelProdutoPreco.setText("");
            labelProdutoQuantidade.setText("");
            labelProdutoCategoria.setText("");
        }
    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Produto produto = new Produto();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosProdutosDialog(produto);
        if(buttonConfirmarClicked){
            produtoDAO.inserir(produto);
            carregarListViewProduto();
        }
    }
    
    @FXML 
    public void handleButtonAlterar() throws IOException{
        Produto produto = listViewProduto.getSelectionModel().getSelectedItem();
        if(produto != null){
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosProdutosDialog(produto);
            if(buttonConfirmarClicked) {
                produtoDAO.alterar(produto);
                carregarListViewProduto();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um produto na Tabela!");
            alert.show();
        }
    }
    
    @FXML 
    public void handleButtonRemover() throws IOException{
        Produto produto = listViewProduto.getSelectionModel().getSelectedItem();
        if(produto != null){
            produtoDAO.remover(produto);
            carregarListViewProduto();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um produto na Tabela!");
            alert.show();
        }
    }
    
    public boolean showFXMLAnchorPaneCadastrosProdutosDialog(Produto produto) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosProdutosDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosProdutosDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Produtos");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        FXMLAnchorPaneCadastrosProdutosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setProduto(produto);
        
        dialogStage.showAndWait();
        
        return controller.isButtonConfirmarClicked();
    }
}
