package model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel migales puertas
 *
 */
public class DataBaseConnection {

    //datos de la base de datos 
    private static Connection connection;
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String BD = "chat_users";
    private static final String TABLE1 = "users";

    //ENCENDER XAMPP Y CREAR LA BASE DE DATOS chat_users PARA PROCEDER CON EL REGISTRO Y LOGIN
    //constructor de la conexion
    public DataBaseConnection() {

        connection = null;
        try {
            Class.forName(DRIVER);
            connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("**********************************************************************");
            System.out.println("RECUERDE ACTIVAR SU SERVIDOR XAMPP Y CREAR LA BASE DE DATOS chat_users");
            System.out.println("**********************************************************************");
        }
    }

    //funcion de desconectar de la bd
    public void disconnect() {
        connection = null;
    }  

    //registro de usuarios
    public void register(String email, String username, String password) {

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connection = (Connection) DriverManager.getConnection(URL + BD, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        PreparedStatement ps = null;

        try {
            ps = (PreparedStatement) connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + TABLE1
                    + "(userId int NOT NULL AUTO_INCREMENT PRIMARY KEY, email VARCHAR(100), "
                    + "username VARCHAR (50), password VARCHAR (50))");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("******************************************");
        System.out.println("Tabla " + TABLE1 + " creada o actualizada.");
        System.out.println("******************************************");

        String sql = "INSERT INTO " + TABLE1 + "(email, username, password) " + "values ('" + email + "', '" + username
                + "', '" + password + "')";
        //System.out.println(sql);

        try (Statement st = connection.createStatement()) {
            st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println("Datos añadidos a la tabla.");
            try (ResultSet rs = st.getGeneratedKeys()) {
                rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User login(String username, String password) {

        User user = null;

        Statement st = null;
        String sql = "SELECT * FROM " + TABLE1 + " WHERE username = " + "'" + username + "'"
                + " AND password = " + "'" + password + "';";
        //System.out.println(sql);

        try {
            connection = (Connection) DriverManager.getConnection(URL + BD, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("********************************************************");
            System.out.println("RECUERDE QUE HA DE ESTAR REGISTRADO PARA PODER LOGUEARSE");
            System.out.println("********************************************************");
        }

        try {
            st = connection.createStatement();
            try (ResultSet rs = st.executeQuery(sql)) {
                int results = 0;

                while (rs.next()) {

                    String name = rs.getString("username");
                    String pass = rs.getString("password");

                    user = new User(name, pass);
                    results++;
                }
                if (results == 0) {
                    System.out.println("*******************************************************************");
                    System.out.println("No se ha encontrado ningun resultado.USTED NO DEBE ESTAR REGISTRADO");
                    System.out.println("*******************************************************************");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return user;
    }

}
