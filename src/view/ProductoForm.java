package view;

import controller.ProductoController;
import model.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoForm extends JFrame {
    private JTextField txtCodigo, txtNombre, txtDescripcion, txtPrecioBase, txtPrecioVenta, txtCategoria, txtCantidad;
    private JButton btnBuscar, btnActualizar, btnEliminar, btnVerInventario, btnAgregar;
    private JTable tblInventario;
    private DefaultTableModel tableModel;
    
    private ProductoController controller = new ProductoController();

    public ProductoForm() {
        setTitle("Gestión de Productos");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Inicialización de componentes (labels, text fields, buttons)
        initComponents();

        // Acciones de los botones
        setupActions();

        setVisible(true);
    }

    private void initComponents() {
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(30, 20, 80, 25);
        add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(120, 20, 200, 25);
        add(txtCodigo);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(120, 50, 90, 25);
        add(btnBuscar);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 90, 80, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(120, 90, 200, 25);
        add(txtNombre);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(30, 120, 80, 25);
        add(lblDescripcion);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(120, 120, 200, 25);
        add(txtDescripcion);

        JLabel lblPrecioBase = new JLabel("Precio Base:");
        lblPrecioBase.setBounds(30, 150, 80, 25);
        add(lblPrecioBase);

        txtPrecioBase = new JTextField();
        txtPrecioBase.setBounds(120, 150, 200, 25);
        add(txtPrecioBase);

        JLabel lblPrecioVenta = new JLabel("Precio Venta:");
        lblPrecioVenta.setBounds(30, 180, 80, 25);
        add(lblPrecioVenta);

        txtPrecioVenta = new JTextField();
        txtPrecioVenta.setBounds(120, 180, 200, 25);
        add(txtPrecioVenta);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(30, 210, 80, 25);
        add(lblCategoria);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(120, 210, 200, 25);
        add(txtCategoria);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(30, 240, 80, 25);
        add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(120, 240, 200, 25);
        add(txtCantidad);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(30, 280, 130, 25);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(190, 280, 130, 25);
        add(btnEliminar);

        // Crear el botón Agregar
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(30, 310, 130, 25);
        add(btnAgregar);
        
        btnVerInventario = new JButton("Ver Inventario");
        btnVerInventario.setBounds(190, 310, 160, 25); // Ajustar las coordenadas y tamaño
    
        add(btnVerInventario);

        // Tabla para mostrar el inventario
        tblInventario = new JTable();
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Código", "Nombre", "Descripción", "Precio Base", "Precio Venta", "Categoría", "Cantidad"});
        tblInventario.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblInventario);
        scrollPane.setBounds(30, 360, 500, 100);  // Posicionamos la tabla
        add(scrollPane);
    }

    private void setupActions() {
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(txtCodigo.getText());
                    Producto p = controller.buscarProducto(codigo);
                    if (p != null) {
                        txtNombre.setText(p.getNombre());
                        txtDescripcion.setText(p.getDescripcion());
                        txtPrecioBase.setText(String.valueOf(p.getPrecioBase()));
                        txtPrecioVenta.setText(String.valueOf(p.getPrecioVenta()));
                        txtCategoria.setText(p.getCategoria());
                        txtCantidad.setText(String.valueOf(p.getCantidad()));
                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un código válido.");
                }
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Producto p = new Producto();
                    p.setCodigo(Integer.parseInt(txtCodigo.getText()));
                    p.setNombre(txtNombre.getText());
                    p.setDescripcion(txtDescripcion.getText());
                    p.setPrecioBase(Double.parseDouble(txtPrecioBase.getText()));
                    p.setPrecioVenta(Double.parseDouble(txtPrecioVenta.getText()));
                    p.setCategoria(txtCategoria.getText());
                    p.setCantidad(Integer.parseInt(txtCantidad.getText()));

                    controller.actualizarProducto(p);
                    JOptionPane.showMessageDialog(null, "Producto actualizado.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese valores válidos.");
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(txtCodigo.getText());
                    controller.eliminarProducto(codigo);
                    JOptionPane.showMessageDialog(null, "Producto eliminado.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un código válido.");
                }
            }
        });

        // Acción para el botón Agregar
            btnAgregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // No es necesario pedir el código si es autoincremental
                        String nombre = txtNombre.getText();
                        String descripcion = txtDescripcion.getText();
                        double precioBase = Double.parseDouble(txtPrecioBase.getText());
                        double precioVenta = Double.parseDouble(txtPrecioVenta.getText());
                        String categoria = txtCategoria.getText();
                        int cantidad = Integer.parseInt(txtCantidad.getText());

                        // Crear un nuevo producto sin especificar el código
                        Producto nuevoProducto = new Producto(nombre, descripcion, precioBase, precioVenta, categoria, cantidad);

                        // Llamar al controlador para agregar el producto
                        controller.agregarProducto(nuevoProducto);

                        // Mostrar mensaje de éxito
                        JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.");

                        // Limpiar los campos
                        limpiarCampos();

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Por favor ingrese valores válidos.");
                    }
                }
            });

             btnVerInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cargar el inventario en la tabla
                List<Producto> productos = controller.obtenerInventario();
                tableModel.setRowCount(0);  // Limpiar la tabla antes de llenarla con nuevos datos

                for (Producto p : productos) {
                    System.out.println(p.getCodigo() + " " + p.getNombre());  // Para depurar
                    tableModel.addRow(new Object[]{
                            p.getCodigo(),
                            p.getNombre(),
                            p.getDescripcion(),
                            p.getPrecioBase(),
                            p.getPrecioVenta(),
                            p.getCategoria(),
                            p.getCantidad()
                    });
                }
            }
        });
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecioBase.setText("");
        txtPrecioVenta.setText("");
        txtCategoria.setText("");
        txtCantidad.setText("");
    }

    public static void main(String[] args) {
        new ProductoForm();
    }
}
