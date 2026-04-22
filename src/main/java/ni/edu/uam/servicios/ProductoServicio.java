package ni.edu.uam.servicios;

import ni.edu.uam.interfaces.ProductoInterface;
import ni.edu.uam.modelos.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoServicio implements ProductoInterface {

    private List<Producto> productos;

    public ProductoServicio() {
        this.productos = new ArrayList<>();
    }

    @Override
    public void agregarProducto(String nombre, double precio, int cantidad) {
        this.productos.add(new Producto(nombre, precio, cantidad));
    }

    @Override
    public List<Producto> getProductos() {
        return productos;
    }

    public double getMonto(){
        double total = 0, monto = 0;
        for(Producto producto : productos) {
            total = producto.getCantidad() * producto.getPrecio();
            monto += total;
        }
        return monto;
    }

}
