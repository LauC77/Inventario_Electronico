package controller;

import database.Conexion;
import model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoController {

    // Método para agregar un nuevo producto al inventario (Create)
 public void agregarProducto(Producto producto) {
    // No incluimos el campo 'codigo' en el SQL, porque es generado automáticamente
    String sql = "INSERT INTO producto (nombre, descripcion, precio_base, precio_venta, categoria, cantidad, eliminado) VALUES (?, ?, ?, ?, ?, ?, FALSE)";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        // No seteamos el 'codigo' ya que es generado automáticamente
        ps.setString(1, producto.getNombre());
        ps.setString(2, producto.getDescripcion());
        ps.setDouble(3, producto.getPrecioBase());
        ps.setDouble(4, producto.getPrecioVenta());
        ps.setString(5, producto.getCategoria());
        ps.setInt(6, producto.getCantidad());

        int rowsAffected = ps.executeUpdate();  // Verificar si se agregó el producto

        if (rowsAffected > 0) {
            System.out.println("Producto agregado correctamente.");
        } else {
            System.out.println("No se pudo agregar el producto.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    // Método para buscar un producto por su código (Read)
    public Producto buscarProducto(int codigo) {
        String sql = "SELECT * FROM producto WHERE codigo = ? AND eliminado = FALSE";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigo(rs.getInt("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecioBase(rs.getDouble("precio_base"));
                producto.setPrecioVenta(rs.getDouble("precio_venta"));
                producto.setCategoria(rs.getString("categoria"));
                producto.setCantidad(rs.getInt("cantidad"));
                System.out.println("Producto encontrado: " + producto.getNombre());
                return producto;
            }else {
            System.out.println("No se encontró el producto con código: " + codigo);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para actualizar los datos de un producto (excepto el código)
    public void actualizarProducto(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, descripcion = ?, precio_base = ?, precio_venta = ?, categoria = ?, cantidad = ? WHERE codigo = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecioBase());
            ps.setDouble(4, producto.getPrecioVenta());
            ps.setString(5, producto.getCategoria());
            ps.setInt(6, producto.getCantidad());
            ps.setInt(7, producto.getCodigo());

          int rowsAffected = ps.executeUpdate();  // Verificar si se actualizó el producto

        if (rowsAffected > 0) {
            System.out.println("Producto actualizado correctamente.");
        } else {
            System.out.println("No se encontró el producto para actualizar.");
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminación lógica de un producto (Delete lógico)
 public void eliminarProducto(int codigo) {
    String sql = "DELETE FROM producto WHERE codigo = ?";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, codigo);
        
        // Ejecutar la eliminación
        int rowsAffected = ps.executeUpdate();
        
        // Verificar si se eliminó algo
        if (rowsAffected > 0) {
            System.out.println("Producto eliminado correctamente");
            conn.setAutoCommit(false); 
        } else {
            System.out.println("No se encontró el producto para eliminar.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public List<Producto> obtenerInventario() {
 List<Producto> productos = new ArrayList<>();
    String sql = "SELECT * FROM producto WHERE eliminado = FALSE"; // Considerando que 'eliminado' es un campo booleano
    try (Connection conn = Conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
         
         while (rs.next()) {
             Producto producto = new Producto(
                 rs.getInt("codigo"),
                 rs.getString("nombre"),
                 rs.getString("descripcion"),
                 rs.getDouble("precio_base"),
                 rs.getDouble("precio_venta"),
                 rs.getString("categoria"),
                 rs.getInt("cantidad")
             );
             productos.add(producto);
         } System.out.println("Se han recuperado " + productos.size() + " productos del inventario.");
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return productos;
}

}
