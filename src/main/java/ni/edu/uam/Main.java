package ni.edu.uam;
import ni.edu.uam.modelos.Producto;
import ni.edu.uam.servicios.ProductoServicio;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductoServicio productos = new ProductoServicio();


        productos.agregarProducto( "cafe", 40, 3);
        productos.agregarProducto("tajadas con queso", 60, 3);

        System.out.println("Factura");
        System.out.println(productos.getProductos());
        System.out.println("Total a pagar" + productos.getMonto());

    }
}