/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import dataBase.DBManager;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author enrique
 */
public class MostrarMisClases extends HttpServlet {

    DBManager db = new DBManager();

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
        String id_usuario = (String) sesion.getAttribute("id_usuario");

        ArrayList misClases = db.mostrarMisClases(id_usuario);
        request.setAttribute("MisClases", misClases);

        ArrayList clasesSinRepetir = db.mostrarClasesSinRepetir(id_usuario);
        request.setAttribute("clasesSinRepetir", clasesSinRepetir);

        RequestDispatcher rd = contexto.getRequestDispatcher("/horariosSocio.xhtml");
        rd.forward(request, response);

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

//    @Override
//    public void destroy() {
//        try {
//            statement.close();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(mostrarInformacion.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                connection.close();
//
//            } catch (SQLException ex) {
//                Logger.getLogger(mostrarInformacion.class
//                        .getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

}
