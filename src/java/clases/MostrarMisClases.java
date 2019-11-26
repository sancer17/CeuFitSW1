/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author enrique
 */
public class MostrarMisClases extends HttpServlet {

    DataSource datasource;
    private final Statement statement = null;
    private final Connection connection = null;

    @Override
    public void init() throws ServletException {

        try {
            InitialContext initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("jdbc/CEUFIT01");
            Connection connection = datasource.getConnection();
            Statement createStatement = connection.createStatement();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
//        ServletContext contexto = request.getServletContext();
//        HttpSession sesion = request.getSession();
//        String query1 = "SELECT ID_CLASE FROM APUNTADOS WHERE (ID_USUARIO='" + sesion.getAttribute("id_usuario") + "');";
//        System.out.println(query1);
//        ResultSet resultSet1 = null;
//        ResultSet resultSet2 = null;
//        Statement statement = null;
//        Connection connection = null;
//
//        try {
//            connection = datasource.getConnection();
//            statement = connection.createStatement();
//            resultSet1 = statement.executeQuery(query1);
//            ArrayList arrayMisClases = new ArrayList();
//            while (resultSet1.next()) {
//                String id_clase = resultSet1.getString(1);
//                String query2 = "SELECT CLASE, HORARIO, MONITOR FROM CLASES WHERE (ID_CLASE='" + id_clase + "');";
//                resultSet2 = statement.executeQuery(query2);
//                TablaDeClases clases = new TablaDeClases();
//                while (resultSet2.next()) {
//                    clases.setClase(resultSet2.getString("CLASE"));
//                    clases.setHorario(resultSet2.getString("HORARIO"));
//                    clases.setMonitor(resultSet2.getString("MONITOR"));
//                    arrayMisClases.add(clases);
//                }
//        ServletContext contexto = request.getServletContext();
//        HttpSession sesion = request.getSession();
//        String query1 = "SELECT ID_CLASE FROM APUNTADOS WHERE (ID_USUARIO='" + sesion.getAttribute("id_usuario") + "');";
//        System.out.println(query1);
//        ResultSet resultSet1 = null;
//        Statement statement = null;
//        Connection connection = null;
//        ArrayList id_clases = new ArrayList();
//        ArrayList misClases = new ArrayList();
//
//        try {
//            connection = datasource.getConnection();
//            statement = connection.createStatement();
//            resultSet1 = statement.executeQuery(query1);
//            while (resultSet1.next()) {
//                id_clases.add(resultSet1.getString("ID_CLASE"));
//            }
//            int i;
//            for (i = 0; i < id_clases.size(); i++) {
//                String query2 = "SELECT CLASE, HORARIO, MONITOR FROM CLASES WHERE (ID_CLASE='" + id_clases.get(i) + "');";
//                ResultSet resultSet2 = null;
//                resultSet2 = statement.executeQuery(query2);
//                resultSet2.next();
//                TablaDeClases clases = new TablaDeClases();
//
//                clases.setClase(resultSet2.getString("CLASE"));
//                clases.setHorario(resultSet2.getString("HORARIO"));
//                clases.setMonitor(resultSet2.getString("MONITOR"));
//                misClases.add(clases);
//
//            }

        ServletContext contexto = request.getServletContext();
        HttpSession sesion = request.getSession();
        String query1 = "SELECT CLASE, HORARIO, MONITOR FROM CLASES WHERE "
                + "(ID_CLASE IN (SELECT ID_CLASE FROM APUNTADOS WHERE (ID_USUARIO='" + sesion.getAttribute("id_usuario") + "')));";
        String query2 = "SELECT DISTINCT CLASE FROM CLASES WHERE "
                + "(ID_CLASE IN (SELECT ID_CLASE FROM APUNTADOS WHERE (ID_USUARIO='" + sesion.getAttribute("id_usuario") + "')));";
        System.out.println(query1);
        ResultSet resultSet1 = null;
        ResultSet rs = null;
        Statement statement = null;
        Connection connection = null;
        ArrayList misClases = new ArrayList();
        ArrayList clasesSinRepetir = new ArrayList();

        try {
            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet1 = statement.executeQuery(query1);
            while (resultSet1.next()) {
                TablaDeClases clases = new TablaDeClases();
                clases.setClase(resultSet1.getString("CLASE"));
                clases.setHorario(resultSet1.getString("HORARIO"));
                clases.setMonitor(resultSet1.getString("MONITOR"));
                misClases.add(clases);
            }

            request.setAttribute("MisClases", misClases);

            rs = statement.executeQuery(query2);
            while (rs.next()) {
                TablaDeClases clases = new TablaDeClases();
                clases.setClase("CLASE");
                clasesSinRepetir.add(clases);
            }
            request.setAttribute("clasesSinRepetir", clasesSinRepetir);

            RequestDispatcher rd = contexto.getRequestDispatcher("/horariosSocio.xhtml");
            rd.forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE,
                    "Falló la consulta", ex);
        } finally {
            if (resultSet1 != null) {
                try {
                    resultSet1.close();
                } catch (SQLException ex) {
                    Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE,
                            "No se pudo cerrar el Resulset", ex);
                }
            }
//            if (resultSet2 != null) {
//                try {
//                    resultSet2.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE,
//                            "No se pudo cerrar el Resulset", ex);
//                }
//            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void destroy() {
        try {
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
