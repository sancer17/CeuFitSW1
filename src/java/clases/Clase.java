/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.ArrayList;

/**
 *
 * @author Alejandro
 */

public class Clase {
    String clase;
    String descripcion;
    String horario;
    String monitor;
    ArrayList<String> comentarios;


    public Clase(String clase, String descripcion, String horario, String monitor, ArrayList<String> comentarios) {
        this.clase = clase;
        this.descripcion = descripcion;
        this.horario = horario;
        this.monitor = monitor;
        this.comentarios = comentarios;
    }
///Este constructor lo meto pq saltaba un error en la clase ClaseBean
    Clase(String string, String string0, String string1, String string2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public ArrayList<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<String> comentarios) {
        this.comentarios = comentarios;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    @Override
    public String toString() {
        return "Clase{" + "clase=" + clase + ", descripcion=" + descripcion + ", horario=" + horario + ", monitor=" + monitor + ", comentarios=" + comentarios + '}';
    }

 
}