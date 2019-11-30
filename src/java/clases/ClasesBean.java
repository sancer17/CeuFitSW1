/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Alejandro
 */

@Named
@RequestScoped
public class ClasesBean implements Serializable {

    private final ArrayList<Clase> clases;

    public ClasesBean() {
        System.out.println("Cargando clases ...");
        this.clases = new ArrayList<>();

        try {
            InitialContext initialContext = new InitialContext();
            DataSource datasource = (DataSource) initialContext.lookup("jdbc/CEUFIT01");
            String query = null;
            query = "SELECT *" + "FROM CLASES";

            Connection connection = datasource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Clase clase = new Clase(resultSet.getString("clase"), resultSet.getString("descripcion"),
                        resultSet.getString("horario"), resultSet.getString("monitor"));
                clases.add(clase);
                System.out.println("Clase: " + clase);
            }
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(ClasesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Clase> getClases() {
        return clases;
    }
}
