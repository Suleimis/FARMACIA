package CapaNegocio;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import CapaConexion.Conexion;
import CapaDatos.Compocicion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class CompocicionBD {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();   //conectar viene de la clase conexion
    private String sql;

    public List<Compocicion> reportarcompocicion() {
        List<Compocicion> lista = new ArrayList<>();
        sql = "SELECT idcomposicion,coNombre,pSerie From composicion ORDER BY idcomposicion ASC";
        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Compocicion ocorrelativo = new Compocicion();

                ocorrelativo.setIdcomposicion(rs.getInt(1));
                ocorrelativo.setCoNombre(rs.getString(2));
                ocorrelativo.setpSerie(rs.getString(3));

                lista.add(ocorrelativo);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL REPORTAR CORRELATIVO EN COMPOCICION BD", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return lista;
    }

    public boolean registrarCompocicion(Compocicion co) {
        boolean rpta = false;
        sql = "INSERT INTO composicion(idcomposicion,coNombre,pSerie) VALUES(0,?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, co.getCoNombre());
            pst.setString(2, co.getpSerie());

            rpta = pst.executeUpdate() == 1 ? true : false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "PROBLEMAS AL REGISTRAR COMPOCICION BD", JOptionPane.ERROR_MESSAGE);
            return rpta;
        }
        return rpta;
    }
    
    public boolean  eliminarComposicion(int idComposicion){
        boolean rpta=false;
        sql="DELETE FROM Composicion WHERE idComposicion=?";
        try {
            PreparedStatement pst =cn.prepareStatement(sql);
            pst.setInt(1, idComposicion);
            rpta=pst.executeUpdate()==1 ? true:false;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e,"Problemas al eliminar ComposicionBD",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return rpta;
        }
        return rpta;
    }
}
