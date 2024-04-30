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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PrimaryController {

    
    sonidoIntuder sonidoIntuderr = new sonidoIntuder();

    private String usuariol="";
    private String contrasenya="";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BorrarUsuario;

    @FXML
    private Button CrearUsuario;

    @FXML
    private Button botonIntruder;

    @FXML
    private Button IniciarUsuario;

    @FXML
    private PasswordField contraseña;

    @FXML
    private TextField usuario;

    @FXML
    void botonIntruderAccion(ActionEvent event) {
        this.sonidoIntuderr.ArrancarIntruder("src/main/resources/sonido/party.mp3");
        this.botonIntruder.setVisible(false);
    }

    @FXML
    void BorrarUsuarioAccion(ActionEvent event) throws IOException {
        App.setRoot("cuarty");
        this.sonidoIntuderr.getMediaPlayerIntruder().stop();
    }

    @FXML
    void CrearUsuarioAccion(ActionEvent event) throws IOException {
        App.setRoot("terceary");
        this.sonidoIntuderr.getMediaPlayerIntruder().stop();
    }

    @FXML
    void IniciarUsuarioAccion(ActionEvent event) throws IOException {
        this.usuariol=this.usuario.getText();
        this.contrasenya=this.contraseña.getText();
        if(this.usuariol.isEmpty() || this.contrasenya.isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Campos incompletos");
            alert.setContentText("Los campos o algunos de los campos estan incompletos");
            alert.showAndWait();
            return;
        }
        else{
            
            App.conexioness.IniciarSesion("SELECT nick, contrasenya FROM usuarios WHERE nick = ? AND contrasenya = ?", this.usuariol, this.contrasenya);
            if(App.conexioness.getUsuarioRepetido() >=1 && App.conexioness.getContrasenyaRepetida() >= 1){
                System.out.println(App.conexioness.getUsuarioNombre()); //Obtiene el nombre del usuario elegido
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Exito");
                alert.setHeaderText("Exito al iniciar sesion");
                alert.setContentText("Se iniciara sesion en " + this.usuariol);
                alert.showAndWait();
                App.setRoot("secondary");
            }
            else{
                System.out.println("El usuario o contraseña no coincide");
            }
            
        }
        
    }

    @FXML
    void initialize() {
        assert BorrarUsuario != null : "fx:id=\"BorrarUsuario\" was not injected: check your FXML file 'primary.fxml'.";
        assert CrearUsuario != null : "fx:id=\"CrearUsuario\" was not injected: check your FXML file 'primary.fxml'.";
        assert botonIntruder != null : "fx:id=\"botonIntruder\" was not injected: check your FXML file 'primary.fxml'.";
        assert IniciarUsuario != null : "fx:id=\"IniciarUsuario\" was not injected: check your FXML file 'primary.fxml'.";
        assert contraseña != null : "fx:id=\"contraseña\" was not injected: check your FXML file 'primary.fxml'.";
        assert usuario != null : "fx:id=\"usuario\" was not injected: check your FXML file 'primary.fxml'.";

    }

}
