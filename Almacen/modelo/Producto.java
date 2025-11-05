package modelo;

public class Producto {
    private final int id;
    private final String nombre;
    private int stock;

    public Producto(int id, String nombre, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.stock = Math.max(0, stock);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getStock() { return stock; }

    public void agregarStock(int cantidad) {
        if (cantidad > 0) stock += cantidad;
    }

    public boolean restarStock(int cantidad) {
        if (cantidad <= 0 || cantidad > stock) return false;
        stock -= cantidad;
        return true;
    }

    @Override
    public String toString() {
        return String.format("ID:%d | %s | stock=%d", id, nombre, stock);
    }
}
