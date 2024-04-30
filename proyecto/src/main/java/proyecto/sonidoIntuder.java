package proyecto;

import java.io.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class sonidoIntuder {
    File ruta=null;
    Media mediaIntruder = null;
    MediaPlayer mediaPlayerIntruder = null;
    
    

    sonidoIntuder(){}

    public void ArrancarIntruder(String rutaCancion){
        this.ruta = new File(rutaCancion);
        this.mediaIntruder = new Media(this.ruta.toURI().toString());
        this.mediaPlayerIntruder = new MediaPlayer(this.mediaIntruder);
        this.mediaPlayerIntruder.setVolume(1.0);
        this.mediaPlayerIntruder.setCycleCount(MediaPlayer.INDEFINITE);
        this.mediaPlayerIntruder.play();
    }



    //getters y setters
    public File getRuta() {
        return ruta;
    }

    public void setRuta(File ruta) {
        this.ruta = ruta;
    }

    public Media getMediaIntruder() {
        return mediaIntruder;
    }

    public void setMediaIntruder(Media mediaIntruder) {
        this.mediaIntruder = mediaIntruder;
    }

    public MediaPlayer getMediaPlayerIntruder() {
        return mediaPlayerIntruder;
    }

    public void setMediaPlayerIntruder(MediaPlayer mediaPlayerIntruder) {
        this.mediaPlayerIntruder = mediaPlayerIntruder;
    }
}
