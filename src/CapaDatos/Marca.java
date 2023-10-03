/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDatos;

/**
 *
 * @author HP
 */
public class Marca {

    private int idMarca;
    private String maNombre;

    public Marca() {
    }

    public Marca(int idMarca, String maNombre) {
        this.idMarca = idMarca;
        this.maNombre = maNombre;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getMaNombre() {
        return maNombre;
    }

    public void setMaNombre(String maNombre) {
        this.maNombre = maNombre;
    }
    
    
    
}
