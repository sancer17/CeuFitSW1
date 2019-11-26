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
import javax.servlet.ServletConfig;
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
public class mostrarClasesDelHorario extends HttpServlet {

    DataSource datasource;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param config
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void init(ServletConfig config) throws ServletException {

        try {
            InitialContext initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup(config.getServletContext().getInitParameter("datasource"));
            Connection connection = datasource.getConnection();
            Statement createStatement = connection.createStatement();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet mostrarClasesDelHorario</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet mostrarClasesDelHorario at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        ServletContext contexto = request.getServletContext();
        HttpSession sesion = request.getSession();
//        String query = "SELECT CLASE, HORARIO, MONITOR, ID_CLASE FROM CLASES;";
        String query = "SELECT CLASE, HORARIO, MONITOR, ID_CLASE FROM CLASES WHERE (ID_CLASE NOT IN (SELECT ID_CLASE FROM APUNTADOS WHERE (ID_USUARIO=' " + sesion.getAttribute("id_usuario") + "')));";
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;

        try {
            connection = datasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            ArrayList arrayClases = new ArrayList();

            while (resultSet.next()) {
                TablaDeClases clases = new TablaDeClases();
                clases.setClase(resultSet.getString("CLASE"));
                clases.setHorario(resultSet.getString("HORARIO"));
                clases.setMonitor(resultSet.getString("MONITOR"));
                clases.setId_clase(resultSet.getString("ID_CLASE"));
                arrayClases.add(clases);
            }

            request.setAttribute("TablaDeClases", arrayClases);
            RequestDispatcher rd = contexto.getRequestDispatcher("/clasesSocio.xhtml");
            rd.forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE,
                    "Falló la consulta", ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE,
                            "No se pudo cerrar el Resulset", ex);
                }
            }
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
     * @throws IOException if an I/O error occurs EN EL MÉTODO DOPOST, VA A
     * COGER EL ID_CLASE Y SE LO VA A ASIGNAR A UN USUARIO DE UNA TABLA
     * ID_USUARIO-ID_CLASE. PARA SABER EL USUARIO, TIENE QUE GUARDAR LA SESIÓN Y
     * EL ID_USUARIO SERÁ UN ATRIBUTO DE LA SESIÓN.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);

        HttpSession sesion = request.getSession();
//        String id_usuario = (String) sesion.getAttribute("id_usuario");
        ServletContext contexto = request.getServletContext();
        String query = "INSERT INTO APUNTADOS(ID_USUARIO, ID_CLASE) VALUE('" + sesion.getAttribute("id_usuario")+ "', '" + request.getParameter("id_clase") + "');";
        System.out.println(query);
        Statement statement = null;
        Connection connection = null;
        try {
            connection = datasource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
                        request.setAttribute("Apuntado", "Te has apuntado correctamente");
            RequestDispatcher rd = contexto.getRequestDispatcher("/mostrarClasesDelHorario");
            rd.forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(mostrarInformacion.class.getName()).log(Level.SEVERE,
                    "Falló la consulta", ex);
        } finally {

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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
