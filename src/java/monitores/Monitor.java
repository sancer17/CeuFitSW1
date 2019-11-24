/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitores;

/**
 *
 * @author rootjsn
 */
public class Monitor {
    
    String dni;
    String nombreCompleto;
    String email;
    int telefono;
    int numeroSS;
    
    public Monitor(String dni, String nombreCompleto, String email, int telefono, int numeroSS) {
        this.dni=dni;
        this.email=email;
        this.nombreCompleto=nombreCompleto;
        this.numeroSS=numeroSS;
        this.telefono=telefono;
    }
    
    
    

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getNumeroSS() {
        return numeroSS;
    }

    public void setNumeroSS(int numeroSS) {
        this.numeroSS = numeroSS;
    }

    @Override
    public String toString() {
        return "Monitor{" + "dni=" + dni + ", nombreCompleto=" + nombreCompleto + ", email=" + email + ", telefono=" + telefono + ", numeroSS=" + numeroSS + '}';
    }
    
    
}
