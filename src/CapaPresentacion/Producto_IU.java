package CapaPresentacion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import CapaDatos.Categoria;
import CapaDatos.Marca;
import CapaDatos.Medida;
import CapaDatos.Producto;
//import capaDatos.Producto;
import CapaNegocio.CategoriaBD;
import CapaNegocio.MarcaBD;
import CapaNegocio.MedidaBD;
import CapaNegocio.ProductoBD;
import static CapaPresentacion.Menu.escritorio;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author labor
 */
public class Producto_IU extends javax.swing.JInternalFrame {

    List<Categoria> lista_Categoria;
    List<Marca> lista_Marca;
    List<Medida> lista_Medida;

    /**
     * Creates new form Producto_IU
     */
    public Producto_IU() {
        initComponents();
        cargarCatgoria();
        cargarMarca();
        cargarMedida();
        limnpiar();
        reportar();
    }

    private void reportar() {
        try {
            setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

            Limpiar_tabla_formulario();
            DefaultTableModel tabla_temporal;
            ProductoBD oProductoBD = new ProductoBD();

            tabla_temporal = oProductoBD.reportarProducto();

            int cantidad_productos_encontrados = tabla_temporal.getRowCount();
            txtCantidad.setText("" + cantidad_productos_encontrados);

            DefaultTableModel tabla_temporal_producto = (DefaultTableModel) this.tabla_reporte_producto.getModel();

            for (int i = 0; i < cantidad_productos_encontrados; i++) {

                String serie = tabla_temporal.getValueAt(i, 0).toString();
                String descripcion = tabla_temporal.getValueAt(i, 1).toString();
                String observacion = tabla_temporal.getValueAt(i, 2).toString();
                String digemi = tabla_temporal.getValueAt(i, 3).toString();
                String condicion = tabla_temporal.getValueAt(i, 4).toString();
                String categoria = tabla_temporal.getValueAt(i, 5).toString();
                String marca = tabla_temporal.getValueAt(i, 6).toString();
                String medida = tabla_temporal.getValueAt(i, 7).toString();

                Object[] data = {serie, descripcion, observacion, digemi, condicion, categoria, marca, medida};
                tabla_temporal_producto.addRow(data);
            }

            tabla_reporte_producto.setModel(tabla_temporal_producto);
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        } catch (Exception ex) {
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            ex.printStackTrace();
        }
    }

    private void limpiar() {
        txtSerie.setText("");
        txtDescripcion.setText("");
        cmbMedida.setSelectedItem("");
        txtDigemi.setText("");
        txtObservacion.setText("");
//        dcFechaNac.setDate();
        cmbMarca.setSelectedItem("");
        cmbCategoria.setSelectedItem("");
        cmbMedida.setSelectedItem("");

    }

    private void habilitar() {
        btnBuscar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnModificar.setEnabled(true);
        btnRegistrar.setEnabled(true);

    }

    private void deshabilitar() {
        btnBuscar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnRegistrar.setEnabled(false);
    }

    private void limnpiar() {
        cmbCategoria.setSelectedIndex(0);
        cmbMarca.setSelectedIndex(0);
        cmbMedida.setSelectedIndex(0);

        txtSerie.setText("");
        txtObservacion.setText("");
        txtDigemi.setText("");
        txtObservacion.setText("");

        txtIdCategoria.setText("");
        txtIdMarca.setText("");
        txtIdMedida.setText("");

    }

    private void Limpiar_tabla_formulario() {
        DefaultTableModel tabla_temporal_productos = (DefaultTableModel) tabla_reporte_producto.getModel();
        tabla_temporal_productos.setRowCount(0);
    }

    private void cargarMarca() {
        try {
            cmbMarca.removeAllItems();

            MarcaBD oMarcaBD = new MarcaBD();
            DefaultTableModel tabla_temporal;
            tabla_temporal = oMarcaBD.reportarMarca();

            lista_Marca = new ArrayList<>();
            cmbMarca.addItem("seleccionar");

            for (int i = 0; i < tabla_temporal.getRowCount(); i++) {

                int codigo = Integer.valueOf(tabla_temporal.getValueAt(i, 0).toString());
                String nombre = String.valueOf(tabla_temporal.getValueAt(i, 1));

                lista_Marca.add(new Marca(codigo, nombre));
                cmbMarca.addItem(nombre);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "error al cargar marca", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCatgoria() {
        try {
            cmbCategoria.removeAllItems();
            CategoriaBD oCategoriaBD = new CategoriaBD();

            lista_Categoria = (List<Categoria>) oCategoriaBD.reportarCategoria();
            cmbCategoria.addItem("seleccionar");

            for (int i = 0; i < lista_Categoria.size(); i++) {
                int idcategoria = lista_Categoria.get(i).getIdcategoria();
                String nombre = lista_Categoria.get(i).getCaNombre();

                cmbCategoria.addItem(nombre);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "error al cargar categoria", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cargarMedida() {
        try {
            cmbMedida.removeAllItems();

            MedidaBD oMedidaBD = new MedidaBD();
            DefaultTableModel tabla_temporal;
            tabla_temporal = oMedidaBD.reportarMedida();

            lista_Medida = new ArrayList<>();

            cmbMedida.addItem("seleccionar");

            for (int i = 0; i < tabla_temporal.getRowCount(); i++) {

                int codigo = Integer.valueOf(tabla_temporal.getValueAt(i, 0).toString());
                String Precentacion = String.valueOf(tabla_temporal.getValueAt(i, 1));
                String Equivalencia = String.valueOf(tabla_temporal.getValueAt(i, 2));

                lista_Medida.add(new Medida(codigo, Precentacion, Equivalencia));
                cmbMedida.addItem(Equivalencia);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "error al cargar Medida", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exito(String mensaje) {
        JOptionPane.showConfirmDialog(this, mensaje, "MENSAJE", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

    }

    private void error(String mensaje) {
        JOptionPane.showConfirmDialog(this, mensaje, "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

    }

    private void advertencia(String mensaje) {
        JOptionPane.showConfirmDialog(this, mensaje, "ADVERTENCIA", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtDgmi = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtIdCategoria = new javax.swing.JTextField();
        txtIdMarca = new javax.swing.JTextField();
        txtObservacion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbMedida = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbMarca = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_reporte_producto = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnRegistrar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cmbCategoria = new javax.swing.JComboBox<>();
        txtIdMedida = new javax.swing.JTextField();
        cmbCondicion = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtDigemi = new javax.swing.JTextField();

        txtDgmi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDgmiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDgmiFocusLost(evt);
            }
        });
        txtDgmi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDgmiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDgmiKeyTyped(evt);
            }
        });

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setText("SERIE");

        jLabel2.setText("DESCRIPCION");

        txtSerie.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSerieFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSerieFocusLost(evt);
            }
        });
        txtSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSerieKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSerieKeyTyped(evt);
            }
        });

        txtDescripcion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescripcionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescripcionFocusLost(evt);
            }
        });
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyPressed(evt);
            }
        });

        txtIdCategoria.setEnabled(false);
        txtIdCategoria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdCategoriaFocusGained(evt);
            }
        });

        txtIdMarca.setEnabled(false);
        txtIdMarca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdMarcaFocusGained(evt);
            }
        });

        txtObservacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtObservacionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtObservacionFocusLost(evt);
            }
        });
        txtObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtObservacionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacionKeyTyped(evt);
            }
        });

        jLabel7.setText("CONDICION");

        jLabel3.setText("MEDIDA");

        cmbMedida.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMedidaItemStateChanged(evt);
            }
        });
        cmbMedida.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbMedidaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbMedidaFocusLost(evt);
            }
        });
        cmbMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMedidaActionPerformed(evt);
            }
        });
        cmbMedida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMedidaKeyPressed(evt);
            }
        });

        jLabel5.setText("DIGEMI");

        jLabel6.setText("OBSERVACION");

        cmbMarca.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMarcaItemStateChanged(evt);
            }
        });
        cmbMarca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbMarcaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbMarcaFocusLost(evt);
            }
        });
        cmbMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMarcaActionPerformed(evt);
            }
        });
        cmbMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMarcaKeyPressed(evt);
            }
        });

        jLabel8.setText("LABORATORIO");

        tabla_reporte_producto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SERIE", "DESCRIPCION", "OBSERVACION", "DIGEMI", "CONDICION", "CATEGORIA", "MARCA", "MEDIDA"
            }
        ));
        tabla_reporte_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tabla_reporte_productoMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_reporte_productoMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_reporte_producto);

        jLabel12.setText("CANTIDAD");

        txtCantidad.setEnabled(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/disk.png"))); // NOI18N
        btnRegistrar.setText("REGISTRAR");
        btnRegistrar.setEnabled(false);
        btnRegistrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegistrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.jpg"))); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.setEnabled(false);
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/report_edit.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.setEnabled(false);
        btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnModificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/bin_empty.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setEnabled(false);
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/cross.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCerrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCerrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.jpg"))); // NOI18N
        btnNuevo.setText("NUEVO");
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        btnNuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(btnRegistrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar)
                .addGap(18, 18, 18)
                .addComponent(btnModificar)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar)
                .addGap(76, 76, 76)
                .addComponent(btnCerrar)
                .addContainerGap())
        );

        jLabel4.setText("CATEGORIA");

        cmbCategoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCategoriaItemStateChanged(evt);
            }
        });
        cmbCategoria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbCategoriaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbCategoriaFocusLost(evt);
            }
        });
        cmbCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoriaActionPerformed(evt);
            }
        });
        cmbCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbCategoriaKeyPressed(evt);
            }
        });

        txtIdMedida.setEnabled(false);
        txtIdMedida.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdMedidaFocusGained(evt);
            }
        });

        cmbCondicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NORMAL", "COMPOCICION" }));
        cmbCondicion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbCondicionKeyPressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Productos"));

        jLabel9.setText("PRODUCTO");

        txtProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProductoFocusLost(evt);
            }
        });
        txtProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProductoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(txtProducto))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("COMPOCICION");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtDigemi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDigemiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDigemiFocusLost(evt);
            }
        });
        txtDigemi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDigemiActionPerformed(evt);
            }
        });
        txtDigemi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDigemiKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cmbCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDigemi, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(cmbCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtObservacion)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(192, 192, 192)
                                .addComponent(txtIdMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtIdMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 951, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(cmbCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDigemi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(cmbMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbMarcaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMarcaItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {

            String textoseleccionado = (String) cmbMarca.getSelectedItem();
            if (textoseleccionado.equals("seleccionar")) {
                txtIdMarca.setText("");
            } else {
                int i = cmbMarca.getSelectedIndex() - 1;

                txtIdMarca.setText("" + lista_Marca.get(i).getIdMarca());
            }
        }
    }//GEN-LAST:event_cmbMarcaItemStateChanged

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:
        if (txtSerie.getText().length() > 0) {
            if (txtDescripcion.getText().length() > 0) {
                if (txtIdCategoria.getText().length() > 0) {
                    if (txtIdMarca.getText().length() > 0) {
                        if (txtIdMedida.getText().length() > 0) {
                            Producto oProducto = new Producto();
                            ProductoBD oProductoBD = new ProductoBD();

                            oProducto.setpSerie(txtSerie.getText().trim());
                            oProducto.setpDescripcion(txtDescripcion.getText().toUpperCase().trim());
                            oProducto.setpObservacion(txtObservacion.getText().toUpperCase());
                            oProducto.setDigemi(txtDigemi.getText().toUpperCase());
                            oProducto.setpCondicion(cmbCondicion.getSelectedItem().toString());
                            oProducto.setIdCategoria(Integer.parseInt(txtIdCategoria.getText()));
                            oProducto.setIdMarca(Integer.parseInt(txtIdMarca.getText()));
                            oProducto.setIdmedida(Integer.parseInt(txtIdMedida.getText()));

                            boolean rpta = oProductoBD.registrarProducto(oProducto);
                            if (rpta) {
                                exito("se registro con exito");
                                reportar();
                                limpiar();
                                deshabilitar();

                            } else {
                            }

                        } else {
                            error("seleccione una medida");
                        }
                    } else {
                        error("seleccione un laboratorio");
                    }
                } else {
                    error("seleccione una categoria ");
                }
            } else {
                error("ingrese la descripcion del producto");
                txtDescripcion.requestFocus();
            }
        } else {
            error("seleccione el codigo");
            txtSerie.requestFocus();
        }

//     
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code her

        if (txtSerie.getText().length() > 0) {
            String serie = txtSerie.getText();

            DefaultTableModel tabla_temporal;
            ProductoBD oProductoBD = new ProductoBD();
            tabla_temporal = oProductoBD.buscarProducto(serie);

            int cantidad_productos_encontrados = tabla_temporal.getRowCount();
            txtCantidad.setText("" + cantidad_productos_encontrados);

            Limpiar_tabla_formulario();

            if (cantidad_productos_encontrados > 0) {

                txtDescripcion.setText(tabla_temporal.getValueAt(0, 1).toString());
                txtObservacion.setText(tabla_temporal.getValueAt(0, 2).toString());
                txtDigemi.setText(tabla_temporal.getValueAt(0, 3).toString());
                cmbCondicion.setSelectedItem(tabla_temporal.getValueAt(0, 4).toString());
                cmbMedida.setSelectedItem(tabla_temporal.getValueAt(0, 7).toString());
                cmbMarca.setSelectedItem(tabla_temporal.getValueAt(0, 6).toString());
                cmbCategoria.setSelectedItem(tabla_temporal.getValueAt(0, 5).toString());
                txtIdMedida.setText(tabla_temporal.getValueAt(0, 10).toString());
                txtIdCategoria.setText(tabla_temporal.getValueAt(0, 8).toString());
                txtIdMarca.setText(tabla_temporal.getValueAt(0, 9).toString());

                DefaultTableModel tabla_temporal_producto = (DefaultTableModel) this.tabla_reporte_producto.getModel();

                for (int i = 0; i < cantidad_productos_encontrados; i++) {

                    serie = tabla_temporal.getValueAt(i, 0).toString();
                    String descripcion = tabla_temporal.getValueAt(i, 1).toString();
                    String observacion = tabla_temporal.getValueAt(i, 2).toString();
                    String digemi = tabla_temporal.getValueAt(i, 3).toString();
                    String condicion = tabla_temporal.getValueAt(i, 4).toString();
                    String categoria = tabla_temporal.getValueAt(i, 5).toString();
                    String marca = tabla_temporal.getValueAt(i, 6).toString();
                    String medida = tabla_temporal.getValueAt(i, 7).toString();

                    Object[] data = {serie, descripcion, observacion, digemi, condicion, categoria, marca, medida};
                    tabla_temporal.addRow(data);
                }
                tabla_reporte_producto.setModel(tabla_temporal_producto);

            } else {
                error("no se encontro el producto buscado");
                txtSerie.requestFocus();
            }

        } else {
            error("ingrese serie...");
            txtSerie.requestFocus();
        }

//       
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
//     
        if (txtSerie.getText().length() > 0) {
            if (txtDescripcion.getText().length() > 0) {
                if (txtIdCategoria.getText().length() > 0) {
                    if (txtIdMarca.getText().length() > 0) {
                        if (txtIdMedida.getText().length() > 0) {
                            Producto oProducto = new Producto();
                            ProductoBD oProductoBD = new ProductoBD();

                            oProducto.setpSerie(txtSerie.getText().trim());
                            oProducto.setpDescripcion(txtDescripcion.getText().toUpperCase().trim());
                            oProducto.setpObservacion(txtObservacion.getText().toUpperCase());
                            oProducto.setDigemi(txtDigemi.getText().toUpperCase());
                            oProducto.setpCondicion(cmbCondicion.getSelectedItem().toString());
                            oProducto.setIdCategoria(Integer.parseInt(txtIdCategoria.getText()));
                            oProducto.setIdMarca(Integer.parseInt(txtIdMarca.getText()));
                            oProducto.setIdmedida(Integer.parseInt(txtIdMedida.getText()));

                            boolean rpta = oProductoBD.modificarProducto(oProducto);
                            if (rpta) {
                                exito("se modifico con exito");
                                reportar();
                                limpiar();
                                reportar();
                                deshabilitar();

                            } else {
                                error("tienes problemas para modificar");
                            }

                        } else {
                            error("Selecione una medida");
                        }
                    } else {
                        error("seleccione un laboratorio");
                    }
                } else {
                    error("seleccione una categoria ");
                }
            } else {
                error("ingrese la descripcion del producto");
                txtDescripcion.requestFocus();
            }
        } else {
            error("seleccione el codigo");
            txtSerie.requestFocus();
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:

        try {
            if (txtSerie.getText().length() > 0) {
                String serie = txtSerie.getText();

                int aviso = JOptionPane.showConfirmDialog(rootPane, "ESTAS SEGURO DE ELIMINAR ?");
                if (aviso == 0) {

                    ProductoBD oProductoBD = new ProductoBD();

                    boolean rpta = oProductoBD.EliminarProducto(serie);

                    if (rpta) {
                        exito("Se elimino el producto");
                        reportar();
                        limpiar();
                        deshabilitar();
                        txtSerie.requestFocus();

                    } else {
                        error("tienes problemas al reportar producto");
                    }
                }
            } else {
                error("Falta codigo del producto a eliminar");
                txtSerie.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void cmbCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCategoriaItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {

            String textoseleccionado = (String) cmbCategoria.getSelectedItem();

            if (textoseleccionado.equals("seleccionar")) {
                txtIdCategoria.setText("");
            } else {
                int i = cmbCategoria.getSelectedIndex() - 1;

                txtIdCategoria.setText("" + lista_Categoria.get(i).getIdcategoria());
            }

        }
    }//GEN-LAST:event_cmbCategoriaItemStateChanged

    private void txtSerieFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSerieFocusGained
        // TODO add your handling code here:
        txtSerie.setBackground(Color.yellow);
    }//GEN-LAST:event_txtSerieFocusGained

    private void txtIdMarcaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdMarcaFocusGained
        // TODO add your handling code here:
        txtIdMarca.setBackground(Color.yellow);
    }//GEN-LAST:event_txtIdMarcaFocusGained

    private void txtIdCategoriaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdCategoriaFocusGained
        // TODO add your handling code here:
        txtIdCategoria.setBackground(Color.yellow);

    }//GEN-LAST:event_txtIdCategoriaFocusGained

    private void cmbMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMedidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMedidaActionPerformed

    private void cmbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCategoriaActionPerformed

    private void cmbMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMarcaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbMarcaActionPerformed

    private void txtSerieKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSerieKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            txtDescripcion.requestFocus();
        }
    }//GEN-LAST:event_txtSerieKeyPressed

    private void txtDescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            txtObservacion.requestFocus();
        }
    }//GEN-LAST:event_txtDescripcionKeyPressed

    private void txtDescripcionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescripcionFocusGained
        // TODO add your handling code here:
        txtDescripcion.setBackground(Color.yellow);

    }//GEN-LAST:event_txtDescripcionFocusGained

    private void txtDescripcionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescripcionFocusLost
        // TODO add your handling code here:
        txtDescripcion.setBackground(Color.white);
    }//GEN-LAST:event_txtDescripcionFocusLost

    private void cmbMedidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMedidaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            btnNuevo.requestFocus();
            btnNuevo.doClick();;

        }
    }//GEN-LAST:event_cmbMedidaKeyPressed

    private void cmbMedidaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbMedidaFocusGained
        // TODO add your handling code here:
        cmbMedida.setBackground(Color.yellow);
    }//GEN-LAST:event_cmbMedidaFocusGained

    private void cmbMedidaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbMedidaFocusLost
        // TODO add your handling code here:
        cmbMedida.setBackground(Color.white);
    }//GEN-LAST:event_cmbMedidaFocusLost

    private void txtDgmiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDgmiFocusGained
        // TODO add your handling code here:
        // txtPrecio.setBackground(Color.yellow);
    }//GEN-LAST:event_txtDgmiFocusGained

    private void txtDgmiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDgmiFocusLost
        // TODO add your handling code here:
        //txtPrecio.setBackground(Color.white);
    }//GEN-LAST:event_txtDgmiFocusLost

    private void txtDgmiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDgmiKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            //  dcFechaVcto.requestFocus();
        }
    }//GEN-LAST:event_txtDgmiKeyPressed

    private void txtObservacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            txtDigemi.requestFocus();
        }
    }//GEN-LAST:event_txtObservacionKeyPressed

    private void txtObservacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtObservacionFocusGained
        // TODO add your handling code here:
        txtObservacion.setBackground(Color.yellow);
    }//GEN-LAST:event_txtObservacionFocusGained

    private void txtObservacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtObservacionFocusLost
        // TODO add your handling code here:
        txtObservacion.setBackground(Color.white);
    }//GEN-LAST:event_txtObservacionFocusLost

    private void dcFechaVctoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dcFechaVctoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            cmbCategoria.requestFocus();
        }
    }//GEN-LAST:event_dcFechaVctoKeyPressed

    private void dcFechaVctoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dcFechaVctoFocusGained
        // TODO add your handling code here:
        //dcFechaVcto.setBackground(Color.yellow);
    }//GEN-LAST:event_dcFechaVctoFocusGained

    private void dcFechaVctoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dcFechaVctoFocusLost
        // TODO add your handling code here:
//        dcFechaVcto.setBackground(Color.white);
    }//GEN-LAST:event_dcFechaVctoFocusLost

    private void cmbMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMarcaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            cmbMedida.requestFocus();
        }
    }//GEN-LAST:event_cmbMarcaKeyPressed

    private void cmbCategoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbCategoriaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            cmbMarca.requestFocus();

        }
    }//GEN-LAST:event_cmbCategoriaKeyPressed

    private void txtSerieFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSerieFocusLost
        // TODO add your handling code here:
        txtSerie.setBackground(Color.white);
    }//GEN-LAST:event_txtSerieFocusLost

    private void tabla_reporte_productoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_reporte_productoMousePressed
        // TODO add your handling code here:

        if (evt.getClickCount() == 2) {

            int fila_seleccionada = tabla_reporte_producto.getSelectedRow();

            txtSerie.setText(tabla_reporte_producto.getValueAt(fila_seleccionada, 0).toString());
            txtDescripcion.setText(tabla_reporte_producto.getValueAt(fila_seleccionada, 1).toString());
            txtObservacion.setText(tabla_reporte_producto.getValueAt(fila_seleccionada, 2).toString());
            txtDgmi.setText(tabla_reporte_producto.getValueAt(fila_seleccionada, 3).toString());
            
            cmbCondicion.setSelectedItem(tabla_reporte_producto.getValueAt(fila_seleccionada, 4).toString());
            cmbCategoria.setSelectedItem(tabla_reporte_producto.getValueAt(fila_seleccionada, 5).toString());
            cmbMarca.setSelectedItem(tabla_reporte_producto.getValueAt(fila_seleccionada, 6).toString());
            cmbMedida.setSelectedItem(tabla_reporte_producto.getValueAt(fila_seleccionada, 5).toString());
            txtIdCategoria.setText(tabla_reporte_producto.getValueAt(fila_seleccionada, 8).toString());
            txtIdMarca.setText(tabla_reporte_producto.getValueAt(fila_seleccionada, 9).toString());
            txtIdMedida.setText(tabla_reporte_producto.getValueAt(fila_seleccionada, 10).toString());
                    

            txtSerie.requestFocus();
            btnRegistrar.setEnabled(false);
            btnEliminar.setEnabled(true);
            btnModificar.setEnabled(true);
            reportar();
            
        }

    }//GEN-LAST:event_tabla_reporte_productoMousePressed

    private void txtSerieKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSerieKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) || txtSerie.getText().length() >= 13) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSerieKeyTyped

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtDgmiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDgmiKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) || txtSerie.getText().length() >= 1300000) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDgmiKeyTyped

    private void txtObservacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isDigit(c) || txtSerie.getText().length() >= 13000000) {
            evt.consume();
        }
    }//GEN-LAST:event_txtObservacionKeyTyped

    private void cmbMarcaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbMarcaFocusGained
        // TODO add your handling code here:
        cmbMarca.setBackground(Color.yellow);
    }//GEN-LAST:event_cmbMarcaFocusGained

    private void cmbMarcaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbMarcaFocusLost
        // TODO add your handling code here:
        cmbMarca.setBackground(Color.white);
    }//GEN-LAST:event_cmbMarcaFocusLost

    private void cmbCategoriaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbCategoriaFocusGained
        // TODO add your handling code here:
        cmbCategoria.setBackground(Color.yellow);
    }//GEN-LAST:event_cmbCategoriaFocusGained

    private void cmbCategoriaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbCategoriaFocusLost
        // TODO add your handling code here:
        cmbCategoria.setBackground(Color.white);
    }//GEN-LAST:event_cmbCategoriaFocusLost

    private void txtIdMedidaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdMedidaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdMedidaFocusGained

    private void cmbMedidaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMedidaItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String textoseleccionado = (String) cmbMedida.getSelectedItem();

            if (textoseleccionado.equals("seleccionar")) {
                txtIdMedida.setText("");

            } else {
                int i = cmbMedida.getSelectedIndex() - 1;

                txtIdMedida.setText("" + lista_Medida.get(i).getIdmedida());
            }
        }
    }//GEN-LAST:event_cmbMedidaItemStateChanged

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        habilitar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnNuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            btnRegistrar.requestFocus();
            btnRegistrar.doClick();;

        }
    }//GEN-LAST:event_btnNuevoKeyPressed

    private void txtDigemiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDigemiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDigemiActionPerformed

    private void txtDigemiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDigemiFocusGained
        // TODO add your handling code here:
        txtDigemi.setBackground(Color.yellow);
    }//GEN-LAST:event_txtDigemiFocusGained

    private void txtDigemiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDigemiFocusLost
        // TODO add your handling code here:
        txtDigemi.setBackground(Color.white);
    }//GEN-LAST:event_txtDigemiFocusLost

    private void txtProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductoFocusGained
        // TODO add your handling code here:
        txtProducto.setBackground(Color.yellow);
    }//GEN-LAST:event_txtProductoFocusGained

    private void txtProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductoFocusLost
        // TODO add your handling code here:
        txtProducto.setBackground(Color.white);
    }//GEN-LAST:event_txtProductoFocusLost

    private void txtProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductoKeyPressed
        // TODO add your handling code here:
        Limpiar_tabla_formulario();

        String descripcion = txtProducto.getText();
        DefaultTableModel tabla_temporal;
        DefaultTableModel tabla_temporal_producto = (DefaultTableModel) tabla_reporte_producto.getModel();
        ProductoBD oProductoBD = new ProductoBD();
        tabla_temporal = oProductoBD.buscarProductoDescripcion(descripcion);
        int cant = tabla_temporal.getRowCount();

        for (int i = 0; i < cant; i++) {
            String serie = tabla_temporal.getValueAt(i, 0).toString();
            descripcion = tabla_temporal.getValueAt(i, 1).toString();
            String observacion = tabla_temporal.getValueAt(i, 2).toString();
            String digemi = tabla_temporal.getValueAt(i, 3).toString();
            String condicion = tabla_temporal.getValueAt(i, 4).toString();
            String categoria = tabla_temporal.getValueAt(i, 5).toString();
            String marca = tabla_temporal.getValueAt(i, 6).toString();
            String medida = tabla_temporal.getValueAt(i, 7).toString();

            Object[] data = {serie, descripcion, observacion, digemi, condicion, categoria, marca, medida};
            tabla_temporal_producto.addRow(data);
        }
        tabla_reporte_producto.setModel(tabla_temporal_producto);
        txtCantidad.setText("" + cant);
    }//GEN-LAST:event_txtProductoKeyPressed

    private void txtDigemiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDigemiKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            cmbCondicion.requestFocus();
        }
    }//GEN-LAST:event_txtDigemiKeyPressed

    private void cmbCondicionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbCondicionKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            cmbCategoria.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCondicionKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Compocicion_IU frame = new Compocicion_IU();
        escritorio.add(frame);
        Dimension desktopSize = escritorio.getSize();
        Dimension FrameSize = frame.getSize();
        frame.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);

        frame.show();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabla_reporte_productoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_reporte_productoMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_tabla_reporte_productoMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbCondicion;
    private javax.swing.JComboBox<String> cmbMarca;
    private javax.swing.JComboBox<String> cmbMedida;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_reporte_producto;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDgmi;
    private javax.swing.JTextField txtDigemi;
    private javax.swing.JTextField txtIdCategoria;
    private javax.swing.JTextField txtIdMarca;
    private javax.swing.JTextField txtIdMedida;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JTextField txtProducto;
    private javax.swing.JTextField txtSerie;
    // End of variables declaration//GEN-END:variables
}
