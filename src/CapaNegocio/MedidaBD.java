package CapaNegocio;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import CapaConexion.Conexion;
import CapaDatos.Medida;
import CapaDatos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class MedidaBD {

    private Conexion msql = new Conexion();
    private Connection cn = msql.conectar();
    private String sql;

    public boolean registrarMedida(Medida m) {

        sql = "INSERT INTO medida(idmedida,mPresentacion,mEquivalencia)VALUES(0,?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);

            
            pst.setString(1, m.getmPrecentacion());
            pst.setString(2, m.getmEquivalencia());

            pst.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Problemas al registrar ", JOptionPane.ERROR_MESSAGE);

            return false;
        }
        return true;
    }

    public boolean modificarMedida(Medida m) {
        sql = " UPDATE medida SET mPresentacion=?,mEquivalencia=? WHERE idmedida=?";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, m.getmPrecentacion());
            pst.setString(2, m.getmEquivalencia());
            pst.setInt(3, m.getIdmedida());
            pst.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
        return true;
    }

    public DefaultTableModel reportarMedida() {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "PRECENTACION", "EQUIVALENCIA"};
        String[] registros = new String[3];
        modelo = new DefaultTableModel(null, titulos);

        sql = "SELECT idmedida,mPresentacion,mEquivalencia FROM medida";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("idmedida");
                registros[1] = rs.getString("mPresentacion");
                registros[2] = rs.getString("mEquivalencia");

                modelo.addRow(registros);

            }
            return modelo;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL REPORTAR MEDIDA...", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public boolean eliminarMedida(int codigo) {
        try {
            sql = "DELETE FROM medida WHERE idmedida='" + codigo + "'";
            Statement st = cn.createStatement();
            st.executeUpdate(sql);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Problemas al eliminar", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    
}
