package CapaDatos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author HP
 */
public class Compocicion {

    private int idcomposicion;
    private String coNombre;
    private String pSerie;

    public Compocicion() {
    }

    public Compocicion(int idcomposicion, String coNombre, String pSerie) {
        this.idcomposicion = idcomposicion;
        this.coNombre = coNombre;
        this.pSerie = pSerie;
    }

    public int getIdcomposicion() {
        return idcomposicion;
    }

    public void setIdcomposicion(int idcomposicion) {
        this.idcomposicion = idcomposicion;
    }

    public String getCoNombre() {
        return coNombre;
    }

    public void setCoNombre(String coNombre) {
        this.coNombre = coNombre;
    }

    public String getpSerie() {
        return pSerie;
    }

    public void setpSerie(String pSerie) {
        this.pSerie = pSerie;
    }

    
    

}
