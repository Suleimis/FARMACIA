/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaDatos;

/**
 *
 * @author sgarc
 */
public class Asistencia {
    private int idasistencia;
    private String aFechaE;
    private String aHoraE;
    private String aHoraS;
    private String usuario_uDni;
    private String aTurno;
    private String aEstado;
    private String aTienda;

    public Asistencia() {
    }

    public Asistencia(int idasistencia, String aFechaE, String aHoraE, String aHoraS, String usuario_uDni, String aTurno, String aEstado, String aTienda) {
        this.idasistencia = idasistencia;
        this.aFechaE = aFechaE;
        this.aHoraE = aHoraE;
        this.aHoraS = aHoraS;
        this.usuario_uDni = usuario_uDni;
        this.aTurno = aTurno;
        this.aEstado = aEstado;
        this.aTienda = aTienda;
    }

    public int getIdasistencia() {
        return idasistencia;
    }

    public void setIdasistencia(int idasistencia) {
        this.idasistencia = idasistencia;
    }

    public String getaFechaE() {
        return aFechaE;
    }

    public void setaFechaE(String aFechaE) {
        this.aFechaE = aFechaE;
    }

    public String getaHoraE() {
        return aHoraE;
    }

    public void setaHoraE(String aHoraE) {
        this.aHoraE = aHoraE;
    }

    public String getaHoraS() {
        return aHoraS;
    }

    public void setaHoraS(String aHoraS) {
        this.aHoraS = aHoraS;
    }

    public String getUsuario_uDni() {
        return usuario_uDni;
    }

    public void setUsuario_uDni(String usuario_uDni) {
        this.usuario_uDni = usuario_uDni;
    }

    public String getaTurno() {
        return aTurno;
    }

    public void setaTurno(String aTurno) {
        this.aTurno = aTurno;
    }

    public String getaEstado() {
        return aEstado;
    }

    public void setaEstado(String aEstado) {
        this.aEstado = aEstado;
    }

    public String getaTienda() {
        return aTienda;
    }

    public void setaTienda(String aTienda) {
        this.aTienda = aTienda;
    }
    
    
}
