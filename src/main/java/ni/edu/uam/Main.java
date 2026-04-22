package ni.edu.uam;
import ni.edu.uam.modelos.Producto;
import ni.edu.uam.servicios.ProductoServicio;

import javax.swing.JOptionPane;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ProductoServicio servicio = new ProductoServicio();
        String[] opciones = {"1.Agregar Producto", "2.Ver Factura", "3.Salir"};
        boolean ejecutando = true;

        while (ejecutando) {
            int seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido a Pulpería Jaguar\nSeleccione una opción:",
                    "Pulpería Jaguar",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (seleccion) {
                case 0: // AGREGAR
                    agregarNuevoProducto(servicio);
                    break;

                case 1: // VER FACTURA
                    mostrarFactura(servicio);
                    break;

                case 2: // SALIR
                case -1: // Si cierran la ventana con la X
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
        String cantidadStr = JOptionPane.showInputDialog("¿Cuántas unidades de '" + nombre + "' desea?");

        try {
            double precio = Double.parseDouble(precioStr);
            int cantidad = Integer.parseInt(cantidadStr);
            servicio.agregarProducto(nombre, precio, cantidad);
            JOptionPane.showMessageDialog(null, "Producto agregado con éxito.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Datos numéricos no válidos.", "Error", JOptionPane.ERROR_MESSAGE);
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
