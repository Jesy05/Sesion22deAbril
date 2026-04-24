package ni.edu.uam;
import ni.edu.uam.modelos.Producto;
import ni.edu.uam.servicios.ProductoServicio;

import javax.swing.JOptionPane;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ProductoServicio servicio = new ProductoServicio();
        String[] opciones = {"1.Agregar Producto", "2.Modificar Producto", "3.Eliminar Producto", "4.Ver Factura", "5.Salir"};
        boolean ejecutando = true;

        while (ejecutando) {
            int seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido a Tiendita Jaguar\nSeleccione una opción:",
                    "Tiendita Jaguar",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (seleccion) {
                case 0: // agregar
                    agregarNuevoProducto(servicio);
                    break;
                
                case 1: // modificar
                    modificarProductoExistente(servicio);
                    break;

                case 2: // eliminar
                    eliminarProductoExistente(servicio);
                    break;

                case 3: // ver factura
                    mostrarFactura(servicio);
                    break;

                case 4: // salir
                case -1:
                    JOptionPane.showMessageDialog(null, "¡Gracias por visitar Pulpería Jaguar!");
                    ejecutando = false;
                    break;
            }
        }
    }

    private static void agregarNuevoProducto(ProductoServicio servicio) {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        String precioStr = JOptionPane.showInputDialog("Ingrese el precio de '" + nombre + "':");
        if (precioStr == null) return;
        
        String cantidadStr = JOptionPane.showInputDialog("¿Cuántas unidades de '" + nombre + "' desea?");
        if (cantidadStr == null) return;

        try {
            double precio = Double.parseDouble(precioStr);
            int cantidad = Integer.parseInt(cantidadStr);
            servicio.agregarProducto(nombre, precio, cantidad);
            JOptionPane.showMessageDialog(null, "Producto agregado con éxito.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Datos numéricos no válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void modificarProductoExistente(ProductoServicio servicio) {
        if (servicio.getProductos().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombreOriginal = JOptionPane.showInputDialog("Ingrese el nombre del producto que desea modificar:");
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) return;

        boolean existe = false;
        for (Producto p : servicio.getProductos()) {
            if (p.getNombre().equalsIgnoreCase(nombreOriginal)) {
                existe = true;
                break;
            }
        }

        if (!existe) {
            JOptionPane.showMessageDialog(null, "Producto no encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del producto:");
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) return;

        String precioStr = JOptionPane.showInputDialog("Ingrese el nuevo precio de '" + nuevoNombre + "':");
        if (precioStr == null) return;
        
        String cantidadStr = JOptionPane.showInputDialog("Ingrese la nueva cantidad de '" + nuevoNombre + "':");
        if (cantidadStr == null) return;

        try {
            double nuevoPrecio = Double.parseDouble(precioStr);
            int nuevaCantidad = Integer.parseInt(cantidadStr);
            servicio.modificarProducto(nombreOriginal, nuevoNombre, nuevoPrecio, nuevaCantidad);
            JOptionPane.showMessageDialog(null, "Producto modificado con éxito.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Datos numéricos no válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void eliminarProductoExistente(ProductoServicio servicio) {
        if (servicio.getProductos().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto que desea eliminar:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        int sizeAnterior = servicio.getProductos().size();
        servicio.eliminarProducto(nombre);
        int sizeActual = servicio.getProductos().size();

        if (sizeAnterior > sizeActual) {
            JOptionPane.showMessageDialog(null, "Producto eliminado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ningún producto con ese nombre.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void mostrarFactura(ProductoServicio servicio) {
        List<Producto> productosComprados = servicio.getProductos();
        if (!productosComprados.isEmpty()) {
            StringBuilder factura = new StringBuilder("*** FACTURA ***\n\n");
            for (Producto p : productosComprados) {
                factura.append(p.getNombre())
                       .append(" - Cantidad: ").append(p.getCantidad())
                       .append(" - Precio unitario: C$").append(p.getPrecio())
                       .append(" - Subtotal: C$").append(p.getCantidad() * p.getPrecio())
                       .append("\n");
            }
            factura.append("\nTotal a pagar: C$").append(servicio.getMonto());
            
            JOptionPane.showMessageDialog(null, factura.toString(), "Factura de Compra", JOptionPane.INFORMATION_MESSAGE);
            
            // en consola también
            System.out.println("Factura");
            for (Producto p : productosComprados) {
                System.out.println(p.getNombre() + " - Cant: " + p.getCantidad() + " - Precio: C$" + p.getPrecio());
            }
            System.out.println("Total a pagar: " + servicio.getMonto());
        } else {
            JOptionPane.showMessageDialog(null, "No se agregaron productos a la compra.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}
