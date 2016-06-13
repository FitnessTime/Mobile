package com.fitnesstime.fitnesstime.DTOs;

import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.Rutina;

import java.util.Date;
import java.util.List;

/**
 * Created by julian on 03/05/16.
 */
public class RutinaDTO {

    private Long idWeb;
    private Long idMobile;
    private String inicio;
    private String fin;
    private String descripcion;
    private String aclaracion;
    private Boolean inicioCambio;
    private Boolean finCambio;
    private Boolean descripcionCambio;
    private Boolean aclaracionCambio;
    private Integer versionMobile;
    private Integer versionWeb;
    private boolean estaSincronizado;
    private boolean esDeCarga;
    private boolean eliminada;
    private String idUsuario;
    private List<EjercicioDTO> ejercicios;

    public Long getIdWeb() {
        return idWeb;
    }

    public void setIdWeb(Long idWeb) {
        this.idWeb = idWeb;
    }

    public Long getIdMobile() {
        return idMobile;
    }

    public void setIdMobile(Long idMobile) {
        this.idMobile = idMobile;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAclaracion() {
        return aclaracion;
    }

    public void setAclaracion(String aclaracion) {
        this.aclaracion = aclaracion;
    }

    public Integer getVersionMobile() {
        return versionMobile;
    }

    public void setVersionMobile(Integer versionMobile) {
        this.versionMobile = versionMobile;
    }

    public Integer getVersionWeb() {
        return versionWeb;
    }

    public void setVersionWeb(Integer versionWeb) {
        this.versionWeb = versionWeb;
    }

    public boolean isEstaSincronizado() {
        return estaSincronizado;
    }

    public void setEstaSincronizado(boolean estaSincronizado) {
        this.estaSincronizado = estaSincronizado;
    }

    public boolean isRutinaDeCarga() {
        return esDeCarga;
    }

    public void setRutinaDeCarga(boolean rutinaDeCarga) {
        this.esDeCarga = rutinaDeCarga;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String usuario) {
        this.idUsuario = usuario;
    }

    public boolean isEliminada() {
        return eliminada;
    }

    public void setEliminada(boolean eliminada) {
        this.eliminada = eliminada;
    }

    public Boolean getInicioCambio() {
        return inicioCambio;
    }

    public void setInicioCambio(Boolean inicioCambio) {
        this.inicioCambio = inicioCambio;
    }

    public Boolean getFinCambio() {
        return finCambio;
    }

    public void setFinCambio(Boolean finCambio) {
        this.finCambio = finCambio;
    }

    public Boolean getDescripcionCambio() {
        return descripcionCambio;
    }

    public void setDescripcionCambio(Boolean descripcionCambio) {
        this.descripcionCambio = descripcionCambio;
    }

    public Boolean getAclaracionCambio() {
        return aclaracionCambio;
    }

    public void setAclaracionCambio(Boolean aclaracionCambio) {
        this.aclaracionCambio = aclaracionCambio;
    }

    public boolean isEsDeCarga() {
        return esDeCarga;
    }

    public void setEsDeCarga(boolean esDeCarga) {
        this.esDeCarga = esDeCarga;
    }

    public List<EjercicioDTO> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<EjercicioDTO> ejercicios) {
        this.ejercicios = ejercicios;
    }
}
