package modelo;

import java.util.ArrayList;
import java.util.List;

public class Bodega {
    private final List<Producto> productos = new ArrayList<>();
    private int siguienteId = 1;

    public Producto agregarProducto(String nombre, int stockInicial) {
        Producto p = new Producto(siguienteId++, nombre, stockInicial);
        productos.add(p);
        return p;
    }

    public boolean registrarEntrada(int id, int cantidad) {
        Producto p = buscarPorId(id);
        if (p == null) return false;
        p.agregarStock(cantidad);
        return true;
    }

    public boolean registrarSalida(int id, int cantidad) {
        Producto p = buscarPorId(id);
        if (p == null) return false;
        return p.restarStock(cantidad);
    }

    public List<Producto> obtenerProductos() {
        return new ArrayList<>(productos);
    }

    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}
