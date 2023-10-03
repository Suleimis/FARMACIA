/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaConexion.Conexion;
import CapaDatos.Categoria;
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
public class CategoriaBD {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();   //conectar viene de la clase conexion
    private String sql;

    public boolean registrarCategoria(Categoria c) { // sale de la capa datos de la clase automoviles

        sql = "INSERT INTO categoria(idcategoria,caNombre) VALUES(0,?)";
        try {

            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, c.getCaNombre());

            pst.executeUpdate(); //pst se pone xq se creo un prepare estatement

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e, "Problema al registrar categoria: ", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public List<Categoria> reportarCategoria() {
        List<Categoria> lista = new ArrayList<>();
        sql = "select idCategoria,caNombre from categoria";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Categoria o_Categoria = new Categoria();

                o_Categoria.setIdcategoria(rs.getInt(1));
                o_Categoria.setCaNombre(rs.getString(2));

                lista.add(o_Categoria);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error al reportar categoria", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return lista;
    }

    public boolean modificarCategoria(Categoria ca) {
        boolean rpta = false;

        sql = "UPDATE  categoria SET caNombre=? WHERE idcategoria=?";

        try {

            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, ca.getCaNombre());
            pst.setInt(2, ca.getIdcategoria());

            rpta = pst.executeUpdate() == 1 ? true : false;
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e, "Problema al modificar Categoria: ", JOptionPane.ERROR_MESSAGE);
            return rpta;
        }
        return rpta;
    }

    public boolean eliminarCategoria(Categoria c) {
        boolean RPTA = false;
        sql = "DELETE FROM categoria WHERE idcategoria=?";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, c.getIdcategoria());
            RPTA = pst.executeUpdate() == 1 ? true : false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Problemas al eliminar", JOptionPane.ERROR_MESSAGE);
            return RPTA;
        }
        return RPTA;
    }

    public DefaultTableModel buscarCategoria(String valor) {
        DefaultTableModel modelo;
        String[] titulos = {"CODIGO", "NOMBRE"};
        String[] registros = new String[2];
        modelo = new DefaultTableModel(null, titulos);

        sql = "SELECT idcategoria,caNombre FROM categoria where caNombre LIKE '%" + valor + "%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("idcategoria");
                registros[1] = rs.getString("caNombre");

                modelo.addRow(registros);

            }
            return modelo;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL BUSCAR marca...", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

}
