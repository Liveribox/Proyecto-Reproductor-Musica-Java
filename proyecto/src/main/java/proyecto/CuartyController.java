package proyecto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CuartyController {

    //conexiones conexioness = new conexiones();
    sonidoIntuder sonidoIntuderr = new sonidoIntuder();

    private String usuariol="";
    private String contrasenya="";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BorrarBoton;

    @FXML
    private Button IntruderBoton;

    @FXML
    private Button VolverBoton;

    @FXML
    private TextField contraseña;

    @FXML
    private TextField usuario;

    @FXML
    void BorrarUsuarioAccion(ActionEvent event) {
        this.usuariol=this.usuario.getText();
        this.contrasenya=this.contraseña.getText();
        if(this.usuariol.isEmpty() || this.contrasenya.isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Campos incompletos");
            alert.setContentText("El campo de usuario o contraseña no esta rellenado");
            alert.showAndWait();
        }
        else{
            App.conexioness.BorrarUsuario("SELECT nick, contrasenya FROM usuarios WHERE nick = ? AND contrasenya = ?", this.usuariol, this.contrasenya);
        }
    }

    @FXML
    void IntruderBotonAccion(ActionEvent event) {
        this.sonidoIntuderr.ArrancarIntruder("src/main/resources/sonido/party.mp3");
        this.IntruderBoton.setVisible(false);
    }

    @FXML
    void VolverAIncioSesionAccion(ActionEvent event) throws IOException {
        App.setRoot("primary");
        this.sonidoIntuderr.getMediaPlayerIntruder().stop();
    }

    @FXML
    void initialize() {
        assert BorrarBoton != null : "fx:id=\"BorrarBoton\" was not injected: check your FXML file 'cuarty.fxml'.";
        assert VolverBoton != null : "fx:id=\"VolverBoton\" was not injected: check your FXML file 'cuarty.fxml'.";
        assert IntruderBoton != null : "fx:id=\"IntruderBoton\" was not injected: check your FXML file 'cuarty.fxml'.";
        assert contraseña != null : "fx:id=\"contraseña\" was not injected: check your FXML file 'cuarty.fxml'.";
        assert usuario != null : "fx:id=\"usuario\" was not injected: check your FXML file 'cuarty.fxml'.";

    }

}
