package CapaNegocio;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import CapaConexion.Conexion;
import CapaDatos.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class ProductoBD {

    private Conexion msql = new Conexion();
    private Connection cn = msql.conectar();
    private String sql;

    public DefaultTableModel reportarProducto() {
        DefaultTableModel modelo;
        String[] cabecera = {"SERIE", "DESCRIPCION", "OBSERVACION", "DIGEMI", "CONDICION", "CATEGORIA", "MARCA", "PRESENTACION", "ID_CAT", "ID_MAT", "ID_ME"};
        String[] registros = new String[11];
        modelo = new DefaultTableModel(null, cabecera);

        sql = "SELECT pSerie,pDescripcion,pObservacion,digemi,pCondicion,caNombre,maNombre,mPresentacion,c.idcategoria,m.idMarca,me.idmedida FROM producto as p "
                + "INNER JOIN marca AS m ON p.idMarca=m.idMarca "
                + "  INNER JOIN medida AS me ON p.idmedida=me.idmedida "
                + "  INNER JOIN categoria AS c ON p.idcategoria= c.idcategoria LIMIT 0,100";
        //sql = "SELECT pSerie,pDescripcion,pStock,pPrecio,pFechaVcto,idcategoria,idmarca,idmedida FROM producto";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("pSerie");
                registros[1] = rs.getString("pDescripcion");
                registros[2] = rs.getString("pObservacion");
                registros[3] = rs.getString("digemi");
                registros[4] = rs.getString("pCondicion");
                registros[5] = rs.getString("caNombre");
                registros[6] = rs.getString("maNombre");
                registros[7] = rs.getString("mPresentacion");
                registros[8] = rs.getString("idcategoria");
                registros[9] = rs.getString("idMarca");
                registros[10] = rs.getString("idmedida");

                modelo.addRow(registros);

            }
            return modelo;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL REPORTAR PRODUCTOBD...", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public boolean registrarProducto(Producto p) {
        boolean rpta = false;
        sql = "INSERT INTO producto(pSerie,pDescripcion,pObservacion,digemi,pCondicion,idcategoria,idMarca,idmedida) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, p.getpSerie());
            pst.setString(2, p.getpDescripcion());
            pst.setString(3, p.getpObservacion());
            pst.setString(4, p.getDigemi());
            pst.setString(5, p.getpCondicion());
            pst.setInt(6, p.getIdCategoria());
            pst.setInt(7, p.getIdMarca());
            pst.setInt(8, p.getIdmedida());

            //  pst.executeUpdate();
            rpta = pst.executeUpdate() == 1 ? true : false;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Problemas al registrar ", JOptionPane.ERROR_MESSAGE);
            return rpta;
        }
        return rpta;
    }

    public boolean modificarProducto(Producto p) {
        sql = "UPDATE producto SET pDescripcion=?,pObservacion=?,digemi=?,pCondicion=?,idCategoria=?,idMarca=?,idmedida=? WHERE pSerie=?";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, p.getpDescripcion());
            pst.setString(2, p.getpObservacion());
            pst.setString(3, p.getDigemi());
            pst.setString(4, p.getpCondicion());
            pst.setInt(5, p.getIdCategoria());
            pst.setInt(6, p.getIdMarca());
            pst.setInt(7, p.getIdmedida());
            pst.setString(8, p.getpSerie());

            pst.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
        return true;
    }

    public boolean EliminarProducto(String serie) {
        boolean rpta = false;
        sql = "DELETE FROM producto WHERE pSerie=?";

        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, serie);

            rpta = pst.executeUpdate() == 1 ? true : false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Problemas al eliminar", JOptionPane.ERROR_MESSAGE);
            return rpta;
        }
        return rpta;
    }

    public DefaultTableModel buscarProducto1(String valor) {
        DefaultTableModel tabla_temporal;
        String[] titulos = {"SERIE", "DESCRIPCION", "OBSERVACION", "DIGEMI", "CONDICION", "CATEGORIA", "MARCA", "PRESENTACION", "ID_CAT", "ID_MAT", "ID_ME"};
        String[] registros = new String[11];
        tabla_temporal = new DefaultTableModel(null, titulos);

        sql = "SELECT pSerie,pDescripcion,pObservacion,digemi,pCondicion,caNombre,maNombre,mPresentacion,c.idcategoria,m.idMarca,me.idmedida FROM producto as p "
                + "  INNER JOIN marca AS m ON p.idMarca=m.idMarca  "
                + "  INNER JOIN medida AS me ON p.idmedida=me.idmedida  "
                + "  INNER JOIN categoria AS c ON p.idcategoria= c.idcategoria "
                + "WHERE pDescripcion LIKE ? LIMIT 0,20";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("pSerie");
                registros[1] = rs.getString("pDescripcion");
                registros[2] = rs.getString("pObservacion");
                registros[3] = rs.getString("digemi");
                registros[4] = rs.getString("pCondicion");
                registros[5] = rs.getString("caNombre");
                registros[6] = rs.getString("maNombre");
                registros[7] = rs.getString("mPresentacion");
                registros[8] = rs.getString("idcategoria");
                registros[9] = rs.getString("idMarca");
                registros[10] = rs.getString("idmedida");

                tabla_temporal.addRow(registros);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL BUSCAR MARCA...", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return tabla_temporal;
    }

    public DefaultTableModel buscarProductoDescripcion(String descripcion) {
        DefaultTableModel tabla_temporal;
        String[] titulos = {"SERIE", "DESCRIPCION", "OBSERVACION", "DIGEMI", "CONDICION", "CATEGORIA", "MARCA", "PRESENTACION", "ID_CAT", "ID_MA", "ID_ME"};
        String[] registros = new String[11];
        tabla_temporal = new DefaultTableModel(null, titulos);
        sql = "SELECT pSerie,pDescripcion,pObservacion,digemi,pCondicion,caNombre,maNombre,mPresentacion,c.idcategoria,m.idMarca,me.idmedida FROM producto AS p \n"
                + "INNER JOIN marca AS m ON p.idmarca=m.idMarca \n"
                + "INNER JOIN medida AS me ON p.idmedida=me.idmedida \n"
                + "INNER JOIN categoria AS c ON p.idcategoria=c.idcategoria "
                + "WHERE pDescripcion LIKE? LIMIT 0,20";

        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, "%" + descripcion + "%");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                registros[0] = rs.getString("pSerie");
                registros[1] = rs.getString("pDescripcion");
                registros[2] = rs.getString("pObservacion");
                registros[3] = rs.getString("digemi");
                registros[4] = rs.getString("pCondicion");
                registros[5] = rs.getString("caNombre");
                registros[6] = rs.getString("maNombre");
                registros[7] = rs.getString("mPresentacion");
                registros[8] = rs.getString("idcategoria");
                registros[9] = rs.getString("idmarca");
                registros[10] = rs.getString("idmedida");

                tabla_temporal.addRow(registros);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL BUSCAR PRODUCTO", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        return tabla_temporal;

    }

   public DefaultTableModel buscarProducto(String serie) {
        DefaultTableModel tabla_temporal;
        String[] titulos = {"SERIE", "DESCRIPCION", "OBSERVACION", "DIGEMI", "CONDICION", "CATEGORIA", "MARCA", "PRESENTACION", "ID_CAT", "ID_MAT", "ID_ME"};
        String[] registros = new String[11];
        tabla_temporal = new DefaultTableModel(null, titulos);

        sql = "SELECT pSerie,pDescripcion,pObservacion,digemi,pCondicion,caNombre,maNombre,mPresentacion,c.idcategoria,m.idMarca,me.idmedida FROM producto as p "
                + "  INNER JOIN marca AS m ON p.idMarca=m.idMarca  "
                + "  INNER JOIN medida AS me ON p.idmedida=me.idmedida  "
                + "  INNER JOIN categoria AS c ON p.idcategoria= c.idcategoria WHERE pSerie=?";

        try {
            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, serie);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registros[0] = rs.getString("pSerie");
                registros[1] = rs.getString("pDescripcion");
                registros[2] = rs.getString("pObservacion");
                registros[3] = rs.getString("digemi");
                registros[4] = rs.getString("pCondicion");
                registros[5] = rs.getString("caNombre");
                registros[6] = rs.getString("maNombre");
                registros[7] = rs.getString("mPresentacion");
                registros[8] = rs.getString("idcategoria");
                registros[9] = rs.getString("idMarca");
                registros[10] = rs.getString("idmedida");


                tabla_temporal.addRow(registros);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "ERROR AL BUSCAR PRODUCTO", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return tabla_temporal;
    }
}
