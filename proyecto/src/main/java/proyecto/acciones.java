package proyecto;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.LongFunction;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;


public class acciones {
    
    //Atributos
    Media media = null;
    MediaPlayer mediaPlayer = null;
    MediaView mediaView = null;
    FileChooser buscador = null;
    DirectoryChooser buscador2 = null;
    DirectoryChooser buscador3 = null;
    String[] directorioSeleccionado=null;
    String directorioSeleccionado2="";
    String musica="";
    File rutaSeleccionada = null;
    File lugarInicial = null;
    private boolean musicaActiva = false;

    //Constructor
    acciones(){}


    //Metodos
    public String ax(String ruta){
        return ruta;
    }


    String rutaOriginal = System.getProperty("user.dir"); //ruta original ejemplo /home/alvaro/proyecto
    public void ArrancarMusica(String musica) throws InterruptedException{
        this.musica = musica;

        try {
            if(!this.musica.contains(".wav") && !this.musica.contains(".mp3")){
                throw new MalformedURLException();
            }
            else{
                //System.out.println(System.getProperty("user.dir"));
                System.setProperty("user.dir", this.rutaSeleccionada.getPath()+"/"+musica);
                //System.out.println(System.getProperty("user.dir")); // Ruta completa con cacion
                java.nio.file.Path base = Paths.get(System.getProperty("user.dir"));
                java.nio.file.Path base2 = Paths.get(rutaOriginal);
                java.nio.file.Path nuevaRuta = base2.relativize(base);
                String RutaRelativa = nuevaRuta.toString();
                System.out.println("Ruta base: " + rutaOriginal);
                System.out.println("Ruta que se mete en la media: " + RutaRelativa);
                //System.out.println("Ruta absoluta: " + System.getProperty("user.dir"));
               //System.setProperty("user.dir", "/home/alvaro/proyecto");
                
                //String rutaCompleta = System.getProperty("user.dir");
                App.conexioness.buscaCanciones(RutaRelativa);
                if(App.conexioness.getCancionExistente() <= 0){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("¡¡ Musica lost !!");
                    alert.setHeaderText("Musica sin paradero");
                    alert.setContentText("La musica no se encuentra o no esta disponible");
                    alert.showAndWait();
                    return;
                }
                
                if(this.mediaPlayer != null){
                    
                    this.mediaPlayer.stop();
         
                }
                 
                File cancion = new File(RutaRelativa);
                System.out.println("Comprobando media");
                this.media = new Media(cancion.toURI().toString());
                System.out.println("Comprobando mediaplayer");
                this.mediaPlayer = new MediaPlayer(this.media);
                System.out.println("Anyadiendo volumen");
                this.mediaPlayer.setVolume(1.0);
                System.out.println("Activando musica");
                this.mediaPlayer.play();
                System.out.println("Musica activa: " + musicaActiva);
                
            }
        } catch (MalformedURLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Error al reproducir audio");
            alert.setContentText("El auido es incompatible o eso no era un audio");
            alert.showAndWait();
        }

    }

    

    public String [] BuscadorFunc(String ruta){
        //Crea el buscador para poder seleccionar los directorios
        this.lugarInicial = new File(ruta);
        this.buscador2 = new DirectoryChooser();
        this.buscador2.setInitialDirectory(this.lugarInicial);
        this.rutaSeleccionada = this.buscador2.showDialog(null);

        if( rutaSeleccionada != null ){
            this.directorioSeleccionado = rutaSeleccionada.list();
        }

        return this.directorioSeleccionado;
    }
    

    public void crearPlaylist(String ruta){
        System.out.println(ruta);
        TextInputDialog dialog = new TextInputDialog(); // Por defecto
        dialog.setTitle("Crear playlist");
        dialog.setHeaderText("Playlist");
        dialog.setContentText("Nombre de la playlist:");
        Optional<String> result = dialog.showAndWait(); // Obteniendo el resultado

        if (result.isPresent()){
            
            if(result.get().equals("")){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("No has puesto nada");
                alert.setContentText("No has puesto nada para crear una playlist");
                alert.showAndWait();
            }
            else{
                File directorio = new File(ruta+ "/" + result.get());
                if (!directorio.exists()) {
                    if (directorio.mkdirs()) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Exito");
                        alert.setHeaderText("Exito");
                        alert.setContentText("Exito al crear la playlist " + result.get());
                        alert.showAndWait();
                    } else {
                        System.out.println("Error al crear directorio");
                    }
                }
                else if(directorio.exists()){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("La playlist " + result.get() + " ya existe");
                    alert.showAndWait();
                }

                
            }
        }
        
    }

    public void borrarPlaylist(String ruta){
        this.lugarInicial = new File(ruta);
        this.buscador3 = new DirectoryChooser();
        this.buscador3.setInitialDirectory(this.lugarInicial);
        this.rutaSeleccionada = this.buscador3.showDialog(null);
        
        if(rutaSeleccionada != null){
            this.directorioSeleccionado2 = rutaSeleccionada.getPath();

            File a = new File(this.directorioSeleccionado2);

            if(a.delete()){
                System.out.println("directorio borrado");
            }
        }
        
    }
    
    /* 
    public void AnyadirMusica(String ruta,String musica){
        System.out.println(ruta+"/"+musica);
        System.setProperty("user.dir", this.rutaSeleccionada.getPath()+"/"+musica);
                System.out.println(System.getProperty("user.dir"));
    }*/

    public void PlayMusic(){
        this.mediaPlayer.play();
    }

    public void PauseMusic(){
        this.mediaPlayer.pause();
    }

    //Getters y setters
    public Media getMedia() {
        return media;
    }


    public void setMedia(Media media) {
        this.media = media;
    }


    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }


    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }


    public MediaView getMediaView() {
        return mediaView;
    }


    public void setMediaView(MediaView mediaView) {
        this.mediaView = mediaView;
    }


    public FileChooser getBuscador() {
        return buscador;
    }


    public void setBuscador(FileChooser buscador) {
        this.buscador = buscador;
    }

    public String [] getDirectorioSeleccionado() {
        return directorioSeleccionado;
    }


    public void setDirectorioSeleccionado(String[] directorioSeleccionado) {
        this.directorioSeleccionado = directorioSeleccionado;
    }

    
}
