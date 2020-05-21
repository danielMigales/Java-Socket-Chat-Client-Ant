package modelo;

import java.io.Serializable;

/**
 *
 * @author daniel migales puertas
 *
 *
 */
public class DataPaquete implements Serializable {

    private String nombreUsuario;
    private String direccionIP;
    private String mensaje;

    public DataPaquete(String nombreUsuario, String direccionIP, String mensaje) {
        
        this.nombreUsuario = nombreUsuario;
        this.direccionIP = direccionIP;
        this.mensaje = mensaje;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
