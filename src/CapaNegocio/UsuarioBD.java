/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaNegocio;

import CapaConexion.Conexion;
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
 * @author USUARIO
 */
public class UsuarioBD {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sql;

    public DefaultTableModel reportarUsuarios() {

        DefaultTableModel tabla_temporal;
        String[] titulos = {"DNI", "NOMBRE", "APELLIDOS", "DIRECCION", "CLAVE", "CELULAR", "TIPO_USUARIO", "TIENDA"};
        String[] registros = new String[8];
        tabla_temporal = new DefaultTableModel(null, titulos);
        sql = "SELECT uDni,uNombre,uApellidos,uDireccion,uClave,uCelular,tuNombre,idtipousario,tienda FROM usario AS u "
                + " INNER JOIN tipousuario AS tp ON u.idtipousario=tp.idtipousuario";

        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registros[0] = rs.getString("uDni");
                registros[1] = rs.getString("uNombre");
                registros[2] = rs.getString("uApellidos");
                registros[3] = rs.getString("uDireccion");
                registros[4] = rs.getString("uClave");
                registros[5] = rs.getString("uCelular");
                registros[6] = rs.getString("tuNombre");
                registros[7] = rs.getString("tienda");

                tabla_temporal.addRow(registros);

            }
            return tabla_temporal;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL REPORTAR USUARIOS BD", JOptionPane.ERROR_MESSAGE);;
            return null;
        }
    }

    public boolean registrarUsuario(Usuario u) {
        boolean rpta = false;
        sql = "INSERT INTO usario(uDni,uNombre,uApellidos,uDireccion,uClave,uCelular,idtipousario,tienda) VALUES(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, u.getuDni());
            pst.setString(2, u.getuNombre());
            pst.setString(3, u.getuApellidos());
            pst.setString(4, u.getuDireccion());
            pst.setString(5, u.getuClave());
            pst.setString(6, u.getuCelular());
            pst.setInt(7, u.getuTipo());
            pst.setString(8, u.getTienda());

            rpta = pst.executeUpdate() == 1 ? true : false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "PROBLEMAS AL REGISTRAR USUSARIO BD", JOptionPane.ERROR_MESSAGE);
            return rpta;

        }
        return rpta;
    }

    public boolean modificarUsuario(Usuario u) {
        sql = "UPDATE usario SET uNombre=?,uApellidos=?,uDireccion=?,uClave=?,uCelular=?,idtipousario=?,tienda=? WHERE uDni=?";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, u.getuNombre());
            pst.setString(2, u.getuApellidos());
            pst.setString(3, u.getuDireccion());
            pst.setString(4, u.getuClave());
            pst.setString(5, u.getuCelular());
            pst.setInt(6, u.getuTipo());
            pst.setString(7, u.getTienda());
            pst.setString(8, u.getuDni());

            pst.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
        return true;
    }

    public boolean EliminarUsuario(String dni) {

        try {
            sql = "DELETE FROM usario WHERE uDni='" + dni + "'";
            Statement st = cn.createStatement();
            st.executeUpdate(sql);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Problemas al eliminar", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public DefaultTableModel buscarUsuario(String apellidos) {
        DefaultTableModel tabla_temporal;
        String[] titulos = {"DNI", "NOMBRES", "APELLIDOS", "DIRECCION", "CLAVE", "CELULAR", "TIPO_USUARIO", "TIENDA"};
        String[] registros = new String[8];
        tabla_temporal = new DefaultTableModel(null, titulos);

        sql = "SELECT uDni,uNombre,uApellidos,uDireccion,uClave,uCelular,tuNombre,idtipousario,tienda FROM usario AS u "
                + "INNER JOIN tipousuario AS tp ON u.idtipousario=tp.idtipousuario "
                + "WHERE uApellidos LIKE ? OR uNombre LIKE ? LIMIT 0,15";
        try {
//            Statement st = cn.createStatement();
//            ResultSet rs = st.executeQuery(sql);
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, "%" + apellidos + "%");
            pst.setString(2, "%" + apellidos + "%");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                registros[0] = rs.getString("uDni");
                registros[1] = rs.getString("uNombre");
                registros[2] = rs.getString("uApellidos");
                registros[3] = rs.getString("uDireccion");
                registros[4] = rs.getString("uClave");
                registros[5] = rs.getString("uCelular");
                registros[6] = rs.getString("tuNombre");
                registros[7] = rs.getString("tienda");

                tabla_temporal.addRow(registros);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL BUSCAR USUARIO...", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return tabla_temporal;
    }

    public DefaultTableModel buscarUsuarioXdni(String dni) {
        DefaultTableModel tabla_temporal;
        String[] titulos = {"DNI", "NOMBRES", "APELLIDOS", "DIRECCION", "CLAVE", "CELULAR", "TIPO_USUARIO", "TIENDA"};
        String[] registros = new String[8];
        tabla_temporal = new DefaultTableModel(null, titulos);

        sql = "SELECT uDni,uNombre,uApellidos,uDireccion,uClave,uCelular,tuNombre,tienda FROM usario AS u  "
                + "                INNER JOIN tipousuario AS tp ON u.idtipousario=tp.idtipousuario    "
                + "                WHERE uDni=?";
        try {
//            Statement st = cn.createStatement();
//            ResultSet rs = st.executeQuery(sql);
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, dni);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                registros[0] = rs.getString("uDni");
                registros[1] = rs.getString("uNombre");
                registros[2] = rs.getString("uApellidos");
                registros[3] = rs.getString("uDireccion");
                registros[4] = rs.getString("uClave");
                registros[5] = rs.getString("uCelular");
                registros[6] = rs.getString("tuNombre");
                registros[7] = rs.getString("tienda");

                tabla_temporal.addRow(registros);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL BUSCAR USUARIO...", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return tabla_temporal;
    }

    public List<Usuario> login(String dni, String clave) {
        List<Usuario> lista = new ArrayList<>();
        sql = " select uDni,uNombre,uApellidos,uDireccion,uClave,uCelular,idtipousario,tienda from usario\n"
                + "where uDni=? and uClave=?";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, dni);
            pst.setString(2, clave);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Usuario o_Usuario = new Usuario();

                o_Usuario.setuDni(rs.getString(1));
                o_Usuario.setuNombre(rs.getString(2));
                o_Usuario.setuApellidos(rs.getString(3));
                o_Usuario.setuDireccion(rs.getString(4));
                o_Usuario.setuClave(rs.getString(5));
                o_Usuario.setuCelular(rs.getString(6));
                o_Usuario.setuTipo(rs.getInt(7));
                o_Usuario.setTienda(rs.getString(8));

                lista.add(o_Usuario);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "error en el login", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
}
