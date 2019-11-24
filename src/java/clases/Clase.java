/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Alejandro
 */

public class Clase {
    String clase;
    String descripcion;
    String horario;
    String monitor;

    public Clase(String clase, String descripcion, String horario, String monitor) {
        this.clase = clase;
        this.descripcion = descripcion;
        this.horario = horario;
        this.monitor = monitor;
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

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    @Override
    public String toString() {
        return "Clase{" + "clase=" + clase + ", descripcion=" + descripcion + ", horario=" + horario + ", monitor=" + monitor + '}';
    }
}
