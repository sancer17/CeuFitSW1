/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import clases.TablaDeClases;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author enrique
 */
public class DBManager {

    DataSource datasource;
    Statement st = null;
    HttpSession sesion = null;
    ResultSet rs = null;
    HttpServletRequest request;
    private Connection connection = null;

    protected void conectar() {
        try {
            InitialContext initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("jdbc/CEUFIT01");
            connection = datasource.getConnection();
            st = connection.createStatement();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void desconectar() {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE,
                        "No se pudo cerrar el Resulset", ex);
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void verClasesParaApuntarse() throws NamingException {

        try {
            this.conectar();
            rs = st.executeQuery("SELECT CLASE, HORARIO, MONITOR, ID_CLASE FROM CLASES WHERE (ID_CLASE NOT IN (SELECT ID_CLASE FROM APUNTADOS WHERE (ID_USUARIO=' " + sesion.getAttribute("id_usuario") + "')));");
            ArrayList arrayClases = new ArrayList();

            while (rs.next()) {
                TablaDeClases clases = new TablaDeClases();
                clases.setClase(rs.getString("CLASE"));
                clases.setHorario(rs.getString("HORARIO"));
                clases.setMonitor(rs.getString("MONITOR"));
                clases.setId_clase(rs.getString("ID_CLASE"));
                arrayClases.add(clases);
            }
            request.setAttribute("TablaDeClases", arrayClases);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar();
        }
    }
}
