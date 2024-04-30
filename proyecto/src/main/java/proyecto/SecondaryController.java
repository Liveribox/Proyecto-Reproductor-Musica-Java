package proyecto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;


public class SecondaryController {

    //acciones accioness = new acciones();

    //conexiones conexioness = new conexiones();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BuscarCancinesPlaylist;

    @FXML
    private Button IniciarCancion;

    @FXML
    private Button BuscarCanciones;

    @FXML
    private Button VolverBoton;

    @FXML
    private Slider LugarMusicaSlider;

    @FXML
    private Button Pausa;

    @FXML
    private Slider PitchMusica;

    @FXML
    private Button Play;

    @FXML
    private Button ReiniciarPitch;

    @FXML
    private Button ReiniciarVolumen;

    @FXML
    private Slider VolumenMusica;

    @FXML
    private Button añadirPlaylist;

    @FXML
    private Button borrarPlaylist;

    @FXML
    private Button crearPlaylist;

    @FXML
    private ListView<String> lista;

    @FXML
    private ScrollBar scrollLista;

    @FXML
    private Label tiempoCancion;

    @FXML
    private Label tiempoTranscurrido;

    @FXML
    private Label volumenSlider;

    @FXML
    void BuscarCancinesPlaylistAccion(ActionEvent event){
        System.out.println(App.conexioness.getUsuarioNombre());
        App.accioness.BuscadorFunc("src/main/resources/usuarios/"+App.conexioness.getUsuarioNombre());
        System.out.println(App.accioness.getDirectorioSeleccionado());
        ObservableList<String> items = FXCollections.observableArrayList (App.accioness.getDirectorioSeleccionado());
        this.lista.setItems(items);
        
    }

    @FXML
    void BuscarCancionesAccion(ActionEvent event) throws URISyntaxException {
        App.accioness.BuscadorFunc("src/main/resources/musica");
        System.out.println(App.accioness.getDirectorioSeleccionado());
        ObservableList<String> items = FXCollections.observableArrayList (App.accioness.getDirectorioSeleccionado());
        this.lista.setItems(items);
        
        if(this.lista.getItems().isEmpty()){
            System.out.println("La lista es vacia");
        }
        else{
            System.out.println("La lista no esta vacia");
        }
    }

    @FXML
    void IniciarCancionAccion(ActionEvent event) throws InterruptedException {
        if(this.lista.getItems().isEmpty()){
            System.out.println("La lista es vacia");
            return;
        }
        
        

        this.PitchMusica.setValue(1);
        this.VolumenMusica.setValue(1);
        this.Pausa.setVisible(true);
        this.Play.setVisible(false);
        String musica = this.lista.getFocusModel().getFocusedItem();
        App.accioness.ArrancarMusica(musica);

        /*Na da mas inciciar una cancion se pondra la duracion de la cancion y el tiempo que transcurre*/
        App.accioness.getMediaPlayer().setOnReady(() -> {
            this.tiempoCancion.setText(String.format("%.2f", App.accioness.getMediaPlayer().getTotalDuration().toMinutes()).replace(',', ':'));
            this.LugarMusicaSlider.setMax(App.accioness.getMediaPlayer().getTotalDuration().toSeconds());
            
            this.LugarMusicaSlider.valueProperty().addListener((p,o,value) ->{
                if(this.LugarMusicaSlider.isPressed()){
                    App.accioness.getMediaPlayer().seek(Duration.seconds(value.doubleValue()));
                }
            });

            App.accioness.getMediaPlayer().currentTimeProperty().addListener((p,o,value) ->{
                this.LugarMusicaSlider.setValue(value.toSeconds());
                this.tiempoTranscurrido.setText(String.format("%.2f", value.toMinutes()).replace(',', ':') );
            });
        });
    }

    
    @FXML
    void LugarMusicaSliderAccion(MouseEvent event) {
        /*Podras cambiar la posicion de una cancion y la duracion de la cancion y el tiempo que transcurre se cambiara*/
            this.tiempoCancion.setText(String.format("%.2f", App.accioness.getMediaPlayer().getTotalDuration().toMinutes()).replace(',', ':'));
            this.LugarMusicaSlider.setMax(App.accioness.getMediaPlayer().getTotalDuration().toSeconds());
            
            this.LugarMusicaSlider.valueProperty().addListener((p,o,value) -> {
                if(this.LugarMusicaSlider.isPressed()){
                    App.accioness.getMediaPlayer().seek(Duration.seconds(value.doubleValue()));
                }
            });

            App.accioness.getMediaPlayer().currentTimeProperty().addListener((p,o,value) -> {
                this.LugarMusicaSlider.setValue(value.toSeconds());
                this.tiempoTranscurrido.setText(String.format("%.2f", value.toMinutes()).replace(',', ':') );
            });
            
    }

    
    @FXML
    void MoverListaAccion(MouseEvent event) {

    }

    @FXML
    void PausaAccion(ActionEvent event) {
        App.accioness.PauseMusic();
        this.Pausa.setVisible(false);
        this.Play.setVisible(true);
    }

    @FXML
    void PitchMusicaAccion(MouseEvent event) {
        this.PitchMusica.valueProperty().addListener((observable,newValue,oldValue) -> {
            App.accioness.getMediaPlayer().setRate(newValue.doubleValue()); //el valor del pitch del media player cambia segun mueva el slider entre los valores de 0 a 1
        });
    }

    @FXML
    void PlayAccion(ActionEvent event) {
        App.accioness.PlayMusic();
        this.Pausa.setVisible(true);
        this.Play.setVisible(false);
    }

    @FXML
    void ReiniciarPitchAccion(ActionEvent event) {
        this.PitchMusica.setValue(1);
        App.accioness.getMediaPlayer().setRate(1);
        
    }

    @FXML
    void ReiniciarVolumenAccion(ActionEvent event) {
        this.VolumenMusica.setValue(1);
        App.accioness.getMediaPlayer().setVolume(1);
    }

    @FXML
    void VolumenMusicaAccion(MouseEvent event) {
        this.VolumenMusica.valueProperty().addListener((observable,newValue,oldValue) -> { // Observa el nuevo y viejo valor
            App.accioness.getMediaPlayer().setVolume(newValue.doubleValue()); //el valor del volumen del media player cambia segun mueva el slider entre los valores de 0 a 1
        });
    }

    @FXML
    void añadirPlaylistAccion(ActionEvent event) { //Añade la musica seleccionada a una playlist ya creada
        //String musica = this.lista.getFocusModel().getFocusedItem();
        //App.accioness.AnyadirMusica("src/main/resources/usuarios/"+App.conexioness.getUsuarioNombre(),musica);
    }
    

    @FXML
    void borrarPlaylistAccion(ActionEvent event) {
        App.accioness.borrarPlaylist("src/main/resources/usuarios/"+App.conexioness.getUsuarioNombre());
    }

    @FXML
    void VolverBotonAccion(ActionEvent event) throws IOException {
        App.setRoot("primary");
        App.accioness.mediaPlayer.stop();

    }

    @FXML
    void crearPlaylistAccion(ActionEvent event) { //Creas una playlist a secas segun el usuario que esté logeado
        //this.accioness.crearPlaylist("src/main/resources/usuarios/"+App.conexioness.getUsuarioNombre());
        App.accioness.crearPlaylist("src/main/resources/usuarios/"+App.conexioness.getUsuarioNombre());
    }

    @FXML
    void initialize() {
        this.VolumenMusica.setMin(0);
        this.VolumenMusica.setMax(1);
        this.VolumenMusica.setValue(1);
        
        this.PitchMusica.setMin(0.1);
        this.PitchMusica.setMax(2);
        this.PitchMusica.setValue(1);

        this.Play.setVisible(false);
        this.Pausa.setVisible(true);

        assert BuscarCancinesPlaylist != null : "fx:id=\"BuscarCancinesPlaylist\" was not injected: check your FXML file 'secondary.fxml'.";
        assert BuscarCanciones != null : "fx:id=\"BuscarCanciones\" was not injected: check your FXML file 'secondary.fxml'.";
        assert IniciarCancion != null : "fx:id=\"IniciarCancion\" was not injected: check your FXML file 'secondary.fxml'.";
        assert LugarMusicaSlider != null : "fx:id=\"LugarMusicaSlider\" was not injected: check your FXML file 'secondary.fxml'.";
        assert Pausa != null : "fx:id=\"Pausa\" was not injected: check your FXML file 'secondary.fxml'.";
        assert PitchMusica != null : "fx:id=\"PitchMusica\" was not injected: check your FXML file 'secondary.fxml'.";
        assert Play != null : "fx:id=\"Play\" was not injected: check your FXML file 'secondary.fxml'.";
        assert ReiniciarPitch != null : "fx:id=\"ReiniciarPitch\" was not injected: check your FXML file 'secondary.fxml'.";
        assert ReiniciarVolumen != null : "fx:id=\"ReiniciarVolumen\" was not injected: check your FXML file 'secondary.fxml'.";
        assert VolumenMusica != null : "fx:id=\"VolumenMusica\" was not injected: check your FXML file 'secondary.fxml'.";
        assert VolverBoton != null : "fx:id=\"VolverBoton\" was not injected: check your FXML file 'secondary.fxml'.";
        assert añadirPlaylist != null : "fx:id=\"añadirPlaylist\" was not injected: check your FXML file 'secondary.fxml'.";
        assert borrarPlaylist != null : "fx:id=\"borrarPlaylist\" was not injected: check your FXML file 'secondary.fxml'.";
        assert crearPlaylist != null : "fx:id=\"crearPlaylist\" was not injected: check your FXML file 'secondary.fxml'.";
        assert lista != null : "fx:id=\"lista\" was not injected: check your FXML file 'secondary.fxml'.";
        assert tiempoCancion != null : "fx:id=\"tiempoCancion\" was not injected: check your FXML file 'secondary.fxml'.";
        assert tiempoTranscurrido != null : "fx:id=\"tiempoTranscurrido\" was not injected: check your FXML file 'secondary.fxml'.";
        assert volumenSlider != null : "fx:id=\"volumenSlider\" was not injected: check your FXML file 'secondary.fxml'.";

    }

}
