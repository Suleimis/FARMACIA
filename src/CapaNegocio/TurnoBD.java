/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaConexion.Conexion;
import CapaDatos.Turno;
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
public class TurnoBD {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sql;

    public DefaultTableModel reportarTurno() {

        DefaultTableModel modelo;
        String[] titulos = {"ID", "DESCRIPCION", "APELLIDOS", "INICIO", "FIN", "USUARIO"};
        String[] registros = new String[5];
        modelo = new DefaultTableModel(null, titulos);
        sql = "SELECT idturno,descripcion,inicio,fin,CONCAT (uApellidos,'',uNombre) AS usario FROM turno AS t  "
                + "INNER JOIN usario AS u ON t.uDni=u.uDni";

        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registros[0] = rs.getString("idturno");
                registros[1] = rs.getString("descripcion");
                registros[2] = rs.getString("inicio");
                registros[3] = rs.getString("fin");
                registros[4] = rs.getString("usario");

                modelo.addRow(registros);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "error al reportar BD", JOptionPane.ERROR_MESSAGE);;
            return null;
        }
        return modelo;
    }

    public boolean RegistrarTurno(Turno t) {
        boolean rpta = false;
        sql = " INSERT INTO turno(idturno,descripcion,inicio,fin,uDni) VALUES(0,?,?,?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, t.getDescripcion());
            pst.setString(2, t.getInicio());
            pst.setString(3, t.getFin());
            pst.setString(4, t.getuDni());

            rpta = pst.executeUpdate() == 1 ? true : false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Problemas al registrar turno BD...", JOptionPane.ERROR_MESSAGE);
            return rpta;
        }
        return rpta;
    }

    public boolean eliminarUsuario(int idturno) {
        boolean rpta = false;
        try {

            sql = "DELETE FROM turno WHERE idturno=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, idturno);

            rpta = pst.executeUpdate() == 1 ? true : false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Problemas al eliminar turnoBD", JOptionPane.ERROR_MESSAGE);
            return rpta;
        }
        return rpta;
    }

    public List<Turno> buscarTurno(String inicio, String fin, String uDni) {
        List<Turno> lista = new ArrayList<>();
        sql = "select idturno,descripcion,inicio,fin,uDni from turno where (inicio<? and fin>?) and uDni=?";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, inicio);
            pst.setString(2, fin);
            pst.setString(3, uDni);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Turno o_Turno = new Turno();

                o_Turno.setIdturno(rs.getInt(1));
                o_Turno.setDescripcion(rs.getString(2));
                o_Turno.setInicio(rs.getString(3));
                o_Turno.setFin(rs.getString(4));
                o_Turno.setuDni(rs.getString(5));

                lista.add(o_Turno);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "error al buscar turno", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

}
