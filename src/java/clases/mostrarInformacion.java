/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;
//hola
//hola2
//hola3
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.sql.DataSource;

/**
 *
 * @author enrique
 */
public class mostrarInformacion extends HttpServlet {

    DataSource datasource;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet mostrarInformacion</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet mostrarInformacion at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext contexto = request.getServletContext();
        String query1 = null;
        String clase = request.getParameter("clase");
        query1 = "select DESCRIPCION from CLASES where (CLASE ='" + clase + "');";
        String query2 = "select COMENTARIO from COMENTARIOS where (CLASE ='" + clase + "');";
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = datasource.getConnection();
            System.out.println(query1);
            statement = connection.createStatement();
            
            resultSet2 = statement.executeQuery(query2);

            while (resultSet2.next()) {
                request.setAttribute("comentarios", resultSet2.getString(1));
            }
//            response.setContentType("text/html;charset=UTF-8");
//            try (PrintWriter out = response.getWriter()) {
//                out.println(resultSet.getString(1));
//            }
            resultSet1 = statement.executeQuery(query1);
            resultSet1.next();
            request.setAttribute("descripcionClase", resultSet1.getString(1));
            RequestDispatcher mostrarDescripcion = contexto.getRequestDispatcher("/claseConcreta.xhtml");
            mostrarDescripcion.forward(request, response);

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
            if (resultSet2 != null) {
                try {
                    resultSet2.close();
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
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
