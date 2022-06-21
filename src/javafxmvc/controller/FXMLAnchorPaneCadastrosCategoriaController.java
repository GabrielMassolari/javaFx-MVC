package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.CategoriaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Categoria;

public class FXMLAnchorPaneCadastrosCategoriaController implements Initializable {
    @FXML
    private ListView<Categoria> listViewCategoria;

    @FXML
    private Label labelCategoriaCodigo;

    @FXML
    private Label labelCategoriaNome;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonRemover;
    
    private List<Categoria> listCategorias;
    private ObservableList<Categoria> observableListCategorias;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoriaDAO.setConnection(connection);
        carregarListViewCategoria();
        
        listViewCategoria.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemListViewCategorias(newValue));
    }
    
    public void carregarListViewCategoria(){
        listCategorias = categoriaDAO.listar();
        observableListCategorias = FXCollections.observableArrayList(listCategorias);
        listViewCategoria.setItems(observableListCategorias);
    }
    
    public void selecionarItemListViewCategorias(Categoria categoria){
        if(categoria != null){
            labelCategoriaCodigo.setText(String.valueOf(categoria.getCdCategoria()));
            labelCategoriaNome.setText(categoria.getDescricao());
        }else{
            labelCategoriaCodigo.setText("");
            labelCategoriaNome.setText("");
        }
    }
    
    @FXML
    void handleButtonAlterar() throws IOException {
        Categoria categoria = listViewCategoria.getSelectionModel().getSelectedItem();
        if(categoria != null){
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosClientesDialog(categoria);
            if(buttonConfirmarClicked) {
                categoriaDAO.alterar(categoria);
                carregarListViewCategoria();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma categoria na Tabela!");
            alert.show();
        }
    }

    @FXML
    void handleButtonInserir() throws IOException {
        Categoria categoria = new Categoria();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosClientesDialog(categoria);
        if(buttonConfirmarClicked){
            categoriaDAO.inserir(categoria);
            carregarListViewCategoria();
        }
    }

    @FXML
    void handleButtonRemover() {
        Categoria categoria = listViewCategoria.getSelectionModel().getSelectedItem();
        if(categoria != null){
            categoriaDAO.remover(categoria);
            carregarListViewCategoria();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma categoria na Tabela!");
            alert.show();
        }
    }
    
    public boolean showFXMLAnchorPaneCadastrosClientesDialog(Categoria categoria) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosCategoriaDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosCategoriaDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Categoria");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        FXMLAnchorPaneCadastrosCategoriaDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCategoria(categoria);
        
        dialogStage.showAndWait();
        
        return controller.isButtonConfirmarClicked();
    }

}
