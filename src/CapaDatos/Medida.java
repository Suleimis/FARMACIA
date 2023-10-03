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
public class Medida {
    
    private int idmedida;
    private String mPresentacion;
    private String mEquivalencia;

    public Medida() {
    }

    public Medida(int idmedida, String mPrecentacion, String mEquivalencia) {
        this.idmedida = idmedida;
        this.mPresentacion = mPrecentacion;
        this.mEquivalencia = mEquivalencia;
    }

    public int getIdmedida() {
        return idmedida;
    }

    public void setIdmedida(int idmedida) {
        this.idmedida = idmedida;
    }

    public String getmPrecentacion() {
        return mPresentacion;
    }

    public void setmPrecentacion(String mPrecentacion) {
        this.mPresentacion = mPrecentacion;
    }

    public String getmEquivalencia() {
        return mEquivalencia;
    }

    public void setmEquivalencia(String mEquivalencia) {
        this.mEquivalencia = mEquivalencia;
    }
    
    
    
}
