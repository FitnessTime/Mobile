package com.fitnesstime.fitnesstime.DTOs;

import java.util.List;

/**
 * Created by julian on 08/05/16.
 */
public class EjercicioDTO {

    private Long idWeb;
    private Long idMobile;
    private String nombre;
    private Integer series;
    private Integer repeticiones;
    private Integer tiempoActivo;
    private Integer tiempoDescanso;
    private String diaDeLaSemana;
    private Integer versionMobile;
    private Integer versionWeb;
    private boolean estaSincronizado;
    private boolean esDeCarga;
    private boolean eliminada;
    private boolean nombreCambio;
    private boolean diaDeLaSemanaCambio;
    private boolean seriesCambio;
    private boolean repeticionesCambio;
    private boolean tiempoActivoCambio;
    private boolean tiempoDescansoCambio;
    private long idRutina;
    private List<MarcaDTO> marcas;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public Integer getTiempoActivo() {
        return tiempoActivo;
    }

    public void setTiempoActivo(Integer tiempoActivo) {
        this.tiempoActivo = tiempoActivo;
    }

    public Integer getTiempoDescanso() {
        return tiempoDescanso;
    }

    public void setTiempoDescanso(Integer tiempoDescanso) {
        this.tiempoDescanso = tiempoDescanso;
    }

    public String getDiaDeLaSemana() {
        return diaDeLaSemana;
    }

    public void setDiaDeLaSemana(String diaDeLaSemana) {
        this.diaDeLaSemana = diaDeLaSemana;
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

    public boolean isEsDeCarga() {
        return esDeCarga;
    }

    public void setEsDeCarga(boolean esDeCarga) {
        this.esDeCarga = esDeCarga;
    }

    public boolean isEliminada() {
        return eliminada;
    }

    public void setEliminada(boolean eliminada) {
        this.eliminada = eliminada;
    }

    public long getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(long idRutina) {
        this.idRutina = idRutina;
    }

    public boolean isNombreCambio() {
        return nombreCambio;
    }

    public void setNombreCambio(boolean nombreCambio) {
        this.nombreCambio = nombreCambio;
    }

    public boolean isDiaDeLaSemanaCambio() {
        return diaDeLaSemanaCambio;
    }

    public void setDiaDeLaSemanaCambio(boolean diaDeLaSemanaCambio) {
        this.diaDeLaSemanaCambio = diaDeLaSemanaCambio;
    }

    public boolean isSeriesCambio() {
        return seriesCambio;
    }

    public void setSeriesCambio(boolean seriesCambio) {
        this.seriesCambio = seriesCambio;
    }

    public boolean isRepeticionesCambio() {
        return repeticionesCambio;
    }

    public void setRepeticionesCambio(boolean repeticionesCambio) {
        this.repeticionesCambio = repeticionesCambio;
    }

    public boolean isTiempoActivoCambio() {
        return tiempoActivoCambio;
    }

    public void setTiempoActivoCambio(boolean tiempoActivoCambio) {
        this.tiempoActivoCambio = tiempoActivoCambio;
    }

    public boolean isTiempoDescansoCambio() {
        return tiempoDescansoCambio;
    }

    public void setTiempoDescansoCambio(boolean tiempoDescansoCambio) {
        this.tiempoDescansoCambio = tiempoDescansoCambio;
    }

    public List<MarcaDTO> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<MarcaDTO> marcas) {
        this.marcas = marcas;
    }
}
