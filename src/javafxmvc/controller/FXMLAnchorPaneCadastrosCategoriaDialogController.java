package javafxmvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.domain.Categoria;

public class FXMLAnchorPaneCadastrosCategoriaDialogController implements Initializable {
    @FXML
    private Label labelCategoriaNome;

    @FXML
    private TextField textFieldCategoriaNome;

    @FXML
    private Button buttonConfirmar;

    @FXML
    private Button buttonCancelar;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Categoria categoria;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        this.textFieldCategoriaNome.setText(categoria.getDescricao());
    }
    
    @FXML
    void handleButtonCancelar() {
        dialogStage.close();
    }

    @FXML
    void handleButtonConfirmar() {
        if(validarCampo()){
            categoria.setDescricao(textFieldCategoriaNome.getText());
            
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    private boolean validarCampo(){
        return textFieldCategoriaNome.getText() != null || textFieldCategoriaNome.getText().length() != 0;
    }
}
