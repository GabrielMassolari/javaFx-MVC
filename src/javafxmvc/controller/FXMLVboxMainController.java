package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;


public class FXMLVboxMainController implements Initializable {
    @FXML
    private MenuItem menuItemCadastrosClientes;
    
    @FXML
    private MenuItem menuItemProcessosVendas;
    
    @FXML
    private MenuItem menuItemGraficosVendasPorMes;
    
    @FXML
    private MenuItem menuItemRelatoriosQuantidadeDeProdutosEstoque;
    
    @FXML
    private AnchorPane anchorPane;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    public void handleMenuItemCadastrosClientes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosClientes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemCadastrosCategoria() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosCategoria.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemCadastrosProdutos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosProdutos.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemProcessosVendas() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneProcessosVendas.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemGraficosVendasPorMes() throws IOException{
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneGraficosVendasPorMes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemGraficosValorPorMes() throws IOException{
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneGraficosValorPorMes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemRelatoriosEstoque() throws IOException{
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneRelatoriosEstoque.fxml"));
        anchorPane.getChildren().setAll(a);
    }
}
