package com.fitnesstime.fitnesstime.ModelosFlujo;

import java.util.Date;

/**
 * Created by julian on 07/02/16.
 */
public class Registro {

    private String nombre;
    private String email;
    private String password;
    private String confirmPassword;
    private int peso;
    private int cantidadMinimaPasos;
    private String fecha;

    public Registro()
    {
        nombre = "";
        email = "";
        password = "";
        confirmPassword = "";
        peso = 0;
        cantidadMinimaPasos = 0;
        fecha = "";
    }

    public Registro(String nombre, String email, String fecha, int peso, int cantidadMinimaPasos)
    {
        this.nombre = nombre;
        this.email = email;
        this.fecha = fecha;
        this.peso = peso;
        this.cantidadMinimaPasos = cantidadMinimaPasos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidadMinimaPasos() {
        return cantidadMinimaPasos;
    }

    public void setCantidadMinimaPasos(int cantidadMinimaPasos) {
        this.cantidadMinimaPasos = cantidadMinimaPasos;
    }
}
