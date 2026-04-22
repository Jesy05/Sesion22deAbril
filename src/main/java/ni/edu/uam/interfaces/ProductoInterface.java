package ni.edu.uam.interfaces;

import ni.edu.uam.modelos.Producto;

import java.util.List;

public interface ProductoInterface {

    public void agregarProducto(String nombre, double precio, int cantidad);
    public List<Producto> getProductos();



}
