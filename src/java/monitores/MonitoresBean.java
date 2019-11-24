/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitores;

import clases.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Alejandro
 */

@Named
@SessionScoped
public class MonitoresBean implements Serializable {

    private final ArrayList<Monitor> monitores;

    public MonitoresBean() {
        System.out.println("Cargando clases ...");
        this.monitores = new ArrayList<>();

        try {
            InitialContext initialContext = new InitialContext();
            DataSource datasource = (DataSource) initialContext.lookup("jdbc/CEUFIT01");
            String query = null;
            query = "SELECT * FROM MONITOR;";
            System.out.println(query);
            Connection connection = datasource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                Monitor monitor = new Monitor(resultSet.getString("DNI"), resultSet.getString("NombreCompleto"),
                        resultSet.getString("Email"), Integer.parseInt(resultSet.getString("numeroSS")), Integer.parseInt(resultSet.getString("telefono")));
                monitores.add(monitor);
                System.out.println("Monitor: " + monitor);
            }
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(MonitoresBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Monitor> getClases() {
        return monitores;
    }
}
