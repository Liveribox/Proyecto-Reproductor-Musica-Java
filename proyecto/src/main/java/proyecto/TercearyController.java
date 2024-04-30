package proyecto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class TercearyController {

    private String usuario="";
    private String contrasenya="";

    //conexiones conexioness = new conexiones();
    sonidoIntuder sonidoIntuderr = new sonidoIntuder();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Salir;

    @FXML
    private PasswordField contrasenyaField;

    @FXML
    private Button crearUsuario;

    @FXML
    private TextField nombreUsuario;

    @FXML
    private Button botonIntruder;

    @FXML
    void SalirAccion(ActionEvent event) throws IOException {
        App.setRoot("primary");
        this.sonidoIntuderr.getMediaPlayerIntruder().stop();
    }

    @FXML
    void botonIntruderAccion(ActionEvent event) {
        this.sonidoIntuderr.ArrancarIntruder("src/main/resources/sonido/party.mp3");
        this.botonIntruder.setVisible(false);
    }


    @FXML
    void crearUsuarioAccion(ActionEvent event) {
        this.usuario=this.nombreUsuario.getText();
        this.contrasenya=this.contrasenyaField.getText();
        if(this.usuario.isEmpty() || this.contrasenya.isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Campos incompletos");
            alert.setContentText("El campo de usuario o contraseña no esta rellenado");
            alert.showAndWait();
        }
        else{
            //App.conexioness.ConectarseA("jdbc:mysql://localhost:3306/proyectoBase?serverTimezone=Europe/Madrid", "root", "admini");
            //App.conexioness.ConectarseA("jdbc:mysql://172.19.0.2:3306/proyectoBase", "root", "admini");
            App.conexioness.CrearUsuario("SELECT nick, contrasenya FROM usuarios WHERE nick = ? AND contrasenya = ?", this.usuario, this.contrasenya);   
        }

    }

    @FXML
    void initialize() {
        assert Salir != null : "fx:id=\"Salir\" was not injected: check your FXML file 'terceary.fxml'.";
        assert contrasenyaField != null : "fx:id=\"contrasenyaField\" was not injected: check your FXML file 'terceary.fxml'.";
        assert botonIntruder != null : "fx:id=\"botonIntruder\" was not injected: check your FXML file 'terceary.fxml'.";
        assert crearUsuario != null : "fx:id=\"crearUsuario\" was not injected: check your FXML file 'terceary.fxml'.";
        assert nombreUsuario != null : "fx:id=\"nombreUsuario\" was not injected: check your FXML file 'terceary.fxml'.";

    }

}
