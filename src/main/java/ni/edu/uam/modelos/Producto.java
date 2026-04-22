package ni.edu.uam.modelos;

import ni.edu.uam.servicios.ProductoServicio;

import javax.swing.*;
import java.util.List;

public class Producto {
    private String nombre;
    private int cantidad;
    private double precio;
    
    public Producto() {
    }

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public static class Main {
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
                    case 0:
                        agregarNuevoProducto(servicio);
                        break;

                    case 1:
                        mostrarFactura(servicio);
                        break;

                    case 2:
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
            String cantidadStr = JOptionPane.showInputDialog("¿Cuántas unidades de '" + nombre + "' desea?");

            try {
                double precio = Double.parseDouble(precioStr);
                int cantidad = Integer.parseInt(cantidadStr);
                servicio.agregarProducto(nombre,precio, cantidad);
                JOptionPane.showMessageDialog(null, "Producto agregado con éxito.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Datos numéricos no válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private static void mostrarFactura(ProductoServicio servicio) {
            List<Producto> productosComprados = servicio.getProductos();

            if (productosComprados.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El carrito está vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            StringBuilder factura = new StringBuilder("**** PULPERÍA JAGUAR ****\n\n");
            for (Producto p : productosComprados) {
                factura.append(p.getNombre())
                        .append(" - Cant: ").append(p.getCantidad())
                        .append(" - Precio: C$").append(p.getPrecio())
                        .append(" - Subtotal: C$").append(p.getCantidad() * p.getPrecio())
                        .append("\n");
            }
            factura.append("\nTotal a pagar: C$").append(servicio.getMonto());

            // ventana
            JOptionPane.showMessageDialog(null, factura.toString(), "Factura Actual", JOptionPane.INFORMATION_MESSAGE);

            // consola
            System.out.println("\n***** FACTURA GENERADA *****");
            System.out.println(factura.toString());
        }
    }
}