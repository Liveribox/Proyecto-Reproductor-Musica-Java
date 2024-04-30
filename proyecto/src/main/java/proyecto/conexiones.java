package proyecto;

import java.sql.*;
import java.io.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class conexiones {

    private String url="";
    private String usuario="";
    private String contrasenya="";
    private Connection con=null;
    private Statement st=null;
    private PreparedStatement ps=null;
    private ResultSet rs=null;
    private String consulta="";
    private File crearDirectorio=null;
    private File borrarDirectorio=null;
    private int contrasenyaRepetida=0;
    private int usuarioRepetido=0;
    private int cancionExistente=0;
    private String usuarioNombre="";

    conexiones(){}

    public void ConectarseA(String url,String usuario,String contrasenya){
        this.url = url;
        this.usuario = usuario;
        this.contrasenya = contrasenya;

        try {
            this.con = DriverManager.getConnection(url, usuario, contrasenya);
            System.out.println("Conexion exitosa");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Metodo para crear usuarios
    public void CrearUsuario(String consulta,String nombre,String contrasenya){
        this.consulta = consulta;
        String consultaUsuario="SELECT count(nick) FROM usuarios WHERE nick=?";

        try {
            this.ps= con.prepareStatement(consultaUsuario);
            this.ps.setString(1, nombre);
            this.rs = ps.executeQuery();
            
            if(rs.next()){
                int usuarioRepetido = this.rs.getInt(1);
                
                if(usuarioRepetido >= 1){
                    throw new SQLException();
                }

            }
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("¡¡ Nick existente !!");
            alert.setHeaderText("Nick elegido");
            alert.setContentText("El nick "+ nombre +" ya está cogido");
            alert.showAndWait();
            return;
        }

         
        try {
            this.ps= con.prepareStatement(consulta);
            this.ps.setString(1, nombre);
            this.ps.setString(2, contrasenya);
            this.rs = ps.executeQuery();

            if(this.rs.next()){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("¡¡ Cuidado !!");
                alert.setHeaderText("Usuario existente");
                alert.setContentText("Ese usuario "+ nombre +" ya existe");
                alert.showAndWait();
            }
            else{
                this.crearDirectorio = new File("src/main/resources/usuarios/"+nombre);
                if(this.crearDirectorio.mkdir()){
                    System.out.println("Se creo el usuario " + nombre);
                }
                String insertarUsuarios = "INSERT INTO usuarios (nick, contrasenya) VALUES (?, ?)";
                this.ps= con.prepareStatement(insertarUsuarios);
                this.ps.setString(1, nombre);
                this.ps.setString(2, contrasenya);
                this.ps.executeUpdate();
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("¡¡ Exito !!");
                alert.setHeaderText("Exito al crear usuario");
                alert.setContentText("Se ha creado el usuario: "+ nombre);
                alert.showAndWait();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    //Metodos para borrar usuarios     
    public void BorrarUsuario(String consulta,String nombre,String contrasenya){
        this.consulta = consulta;
        String consultaUsuario="SELECT count(nick) FROM usuarios WHERE nick=?";

        try {
            this.ps= con.prepareStatement(consultaUsuario);
            this.ps.setString(1, nombre);
            this.rs = ps.executeQuery();
            
            if(rs.next()){
                int usuarioRepetido = this.rs.getInt(1);
                
                if(usuarioRepetido <= 0){
                    throw new SQLException();
                }

            }
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("¡¡ Nick inexistente !!");
            alert.setHeaderText("Nick inexistente");
            alert.setContentText("El nick "+ nombre +" no existe");
            alert.showAndWait();
            return;
        }

        String consultaContrasenya="SELECT count(contrasenya) FROM usuarios WHERE contrasenya=?";
        try {
            this.ps= con.prepareStatement(consultaContrasenya);
            this.ps.setString(1, contrasenya);
            this.rs = ps.executeQuery();

            if(rs.next()){
                int contrasenyaRepetida = this.rs.getInt(1);
                
                if(contrasenyaRepetida <= 0){
                    throw new SQLException();
                }

            }
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("¡¡ Contraseña inexistente !!");
            alert.setHeaderText("Contraseña incorrecta");
            alert.setContentText("La contraseña es oncorrecta");
            alert.showAndWait();
            return;
        }

        try {
            this.ps= con.prepareStatement(consulta);
            this.ps.setString(1, nombre);
            this.ps.setString(2, contrasenya);
            this.rs = ps.executeQuery();

            if(this.rs.next()){
                this.borrarDirectorio = new File("src/main/resources/usuarios/"+nombre);
                 
                if(this.borrarDirectorio.delete()){
                    System.out.println("Se borro el directorio" + nombre);
                }
                else{
                    System.out.println("No se borró nada");
                    return;
                }
                
                String BorrarUsuarios="DELETE FROM usuarios WHERE nick=? AND contrasenya=?";
                this.ps= con.prepareStatement(BorrarUsuarios);
                this.ps.setString(1, nombre);
                this.ps.setString(2, contrasenya);
                this.ps.executeUpdate();
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("¡¡ Borrarando usuario !!");
                alert.setHeaderText("Borrando usuario");
                alert.setContentText("Se borrará el usuario "+ nombre);
                alert.showAndWait();
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

     
    //Metodo para poder iniciar sesion
    
    public void IniciarSesion(String consulta,String nombre,String contrasenya){
        this.consulta = consulta;
        String consultaUsuario="SELECT count(nick) FROM usuarios WHERE nick=?";
        try {
            this.ps= con.prepareStatement(consultaUsuario);
            this.ps.setString(1, nombre);
            this.rs = ps.executeQuery();
            
            if(rs.next()){
                this.usuarioRepetido = this.rs.getInt(1);
                
                if(usuarioRepetido <= 0){
                    throw new SQLException();
                }

            }
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("¡¡ Nick inexistente !!");
            alert.setHeaderText("Nick inexistente");
            alert.setContentText("El nick "+ nombre +" no existe");
            alert.showAndWait();
            
        }

        String consultaContrasenya="SELECT count(contrasenya) FROM usuarios WHERE contrasenya=?";
        try {
            this.ps= con.prepareStatement(consultaContrasenya);
            this.ps.setString(1, contrasenya);
            this.rs = ps.executeQuery();

            if(rs.next()){
                this.contrasenyaRepetida = this.rs.getInt(1);
                
                if(contrasenyaRepetida <= 0){
                    throw new SQLException();
                }

            }
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("¡¡ Contraseña incorrecta !!");
            alert.setHeaderText("Contraseña incorrecta");
            alert.setContentText("La contraseña es incorrecta");
            alert.showAndWait();
            
        }

        String NombreUsuario="SELECT nick FROM usuarios WHERE nick=?";
        try {
            this.ps= con.prepareStatement(NombreUsuario);
            this.ps.setString(1, nombre);
            this.rs = ps.executeQuery();
            
            if(rs.next()){
                this.usuarioNombre = this.rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        
    }


    //Buscador cancion
    public void buscaCanciones(String ruta){
        String consultarCancion="SELECT count(titulo) FROM canciones WHERE titulo=?";
        try {
            this.ps= con.prepareStatement(consultarCancion);
            this.ps.setString(1, ruta);
            this.rs = ps.executeQuery();
            
            if(rs.next()){
                this.cancionExistente = this.rs.getInt(1);
                
                if(cancionExistente <= 0){
                    throw new SQLException();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }
    }

    //getters y setters
    public int getContrasenyaRepetida() {
        return contrasenyaRepetida;
    }

    public void setContrasenyaRepetida(int contrasenyaRepetida) {
        this.contrasenyaRepetida = contrasenyaRepetida;
    }

    public int getUsuarioRepetido() {
        return usuarioRepetido;
    }

    public void setUsuarioRepetido(int usuarioRepetido) {
        this.usuarioRepetido = usuarioRepetido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public int getCancionExistente() {
        return cancionExistente;
    }

    public void setCancionExistente(int cancionExistente) {
        this.cancionExistente = cancionExistente;
    }

   
    
}
