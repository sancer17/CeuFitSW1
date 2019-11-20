package login;

import clases.mostrarInformacion;
import java.io.IOException;
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "ProcesarServletFacelets", urlPatterns = {"/ProcesarServletFacelets"})
public class ProcesarServletFacelets extends HttpServlet {

    String passwordBuena;
    DataSource datasource;
    String tipoUsuario;

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
        ServletContext contexto = request.getServletContext();
        Integer puntuacion = 0;
        String usuario = request.getParameter("usuario");
        String query1 = null;
        query1 = "select PASSWORD from USUARIOS where (DNI ='" + usuario + "');";
        ResultSet resultSet1 = null;
        String query2 = "select TIPO from USUARIOS where (DNI ='" + usuario + "');";
        ResultSet resultSet2 = null;
        Statement statement = null;
        Connection connection = null;
        try {
            String password = request.getParameter("password");
            try {
                connection = datasource.getConnection();
                statement = connection.createStatement();
                resultSet1 = statement.executeQuery(query1);
                resultSet1.next();
                passwordBuena = resultSet1.getString(1);
                System.out.println(passwordBuena);
                resultSet2 = statement.executeQuery(query2);
                resultSet2.next();
                tipoUsuario = resultSet2.getString(1);
                System.out.println(this.tipoUsuario);
                System.out.println(usuario);
                System.out.println(password);
            } catch (SQLException ex) {
                Logger.getLogger(ProcesarServletFacelets.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (usuario != null && password.equals(passwordBuena)) {
//                request.setAttribute("autenticado", true);
                if (tipoUsuario.equals("SOCIO")) {
                    RequestDispatcher anhadirServlet
                            = contexto.getRequestDispatcher("/vistaSocio.xhtml");
                    anhadirServlet.forward(request, response);
                }

                if (tipoUsuario.equals("ADMIN")) {
                    RequestDispatcher anhadirServlet
                            = contexto.getRequestDispatcher("/sociosAdmin.xhtml");
                    anhadirServlet.forward(request, response);
                }

            } else {
                request.setAttribute("errorMessage", ""
                        + "DNI o contraseña incorrectos");
                RequestDispatcher paginaError
                        = contexto.getRequestDispatcher("/registro.xhtml");
                paginaError.forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "No has introducido una contraseña válida");
            RequestDispatcher paginaError
                    = contexto.getRequestDispatcher("/registro.xhtml");
            paginaError.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
