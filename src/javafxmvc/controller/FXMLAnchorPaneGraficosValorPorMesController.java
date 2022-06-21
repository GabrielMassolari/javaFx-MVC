package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafxmvc.model.dao.VendaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;


public class FXMLAnchorPaneGraficosValorPorMesController implements Initializable {
    
    @FXML
    private BarChart<String, Double> barChart;
    
    @FXML
    private ComboBox<Integer> comboBoxAnos;
    
    @FXML
    private Button buttonGerarGrafico;
    
    @FXML
    private NumberAxis numberAxis;
    
    @FXML
    private CategoryAxis categoryAxis;
    
    private ObservableList<String> observableListMeses = FXCollections.observableArrayList();
    private ObservableList<Integer> observableListAnos;
    
    //Manipulação Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VendaDAO vendaDAO = new VendaDAO();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendaDAO.setConnection(connection);
         String[] arrayMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
         observableListMeses.addAll(Arrays.asList(arrayMeses));
         categoryAxis.setCategories(observableListMeses);
         carregarComboBox();
    }
    
    public void carregarComboBox(){
        List<Integer> listAnos = vendaDAO.listarAnosQuePossuemVendas();
        observableListAnos = FXCollections.observableArrayList(listAnos);
        comboBoxAnos.setItems(observableListAnos);
    }

    public void handleButtonGerarGrafico(){
        if(comboBoxAnos.getSelectionModel().getSelectedItem() != null){
            barChart.getData().clear();
            barChart.layout();
            int ano = comboBoxAnos.getSelectionModel().getSelectedItem();
            barChart.setTitle("Valor Vendas por Mês " + ano);
            Map<Integer, Double> dados = vendaDAO.listarValorVendasPorMes(ano);
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(String.valueOf(ano));
            for(Map.Entry<Integer, Double> dadosItem : dados.entrySet()){
                String mes = retornaNomeMes((int)dadosItem.getKey());
                Double valor = dadosItem.getValue();
                series.getData().add(new XYChart.Data<>(mes, valor));
            }
            barChart.getData().add(series);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um ano!");
            alert.show();
        }
        
    }
    
    public String retornaNomeMes(int mes) {
        switch (mes) {
            case 1:
                return "Jan";
            case 2:
                return "Fev";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Set";
            case 10:
                return "Out";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
            default:
                return "";
        }
    }
      
    
}
