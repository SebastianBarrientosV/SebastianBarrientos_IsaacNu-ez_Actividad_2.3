package controlador;

import modelo.*;
import vista.VistaAlmacen;

import javax.swing.*;
import java.util.List;

public class ControladorAlmacen {
    private final Bodega bodega;
    private final VistaAlmacen vista;

    public ControladorAlmacen(Bodega bodega, VistaAlmacen vista) {
        this.bodega = bodega;
        this.vista = vista;
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.btnCrear.addActionListener(e -> crearProducto());
        vista.btnEntrada.addActionListener(e -> registrarEntrada());
        vista.btnSalida.addActionListener(e -> registrarSalida());
        vista.btnInventario.addActionListener(e -> reporteInventarioTotal());
        vista.btnCritico.addActionListener(e -> reporteStockCritico());
        vista.btnRefrescar.addActionListener(e -> refrescarLista());
    }

    private void crearProducto() {
    String nombre = vista.pedirDato("Nombre del producto:", "Crear producto");
    if (nombre == null || nombre.trim().isEmpty()) return;
    nombre = nombre.trim();

    // Verificar si ya existe un producto con ese nombre
    Producto existente = null;
    for (Producto p : bodega.obtenerProductos()) {
        if (p.getNombre().equalsIgnoreCase(nombre)) {
            existente = p;
            break;
        }
    }

    if (existente != null) {
        int opcion = JOptionPane.showConfirmDialog(
                vista,
                "⚠️ El producto \"" + nombre + "\" ya existe.\n¿Desea agregarle stock?",
                "Producto existente",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            String sCant = vista.pedirDato("Cantidad a agregar:", "Agregar stock");
            int cantidad = parseIntSafe(sCant, 0);
            if (cantidad > 0) {
                bodega.registrarEntrada(existente.getId(), cantidad);
                refrescarLista();
                vista.mostrarMensaje("Se agregaron " + cantidad + " unidades a " + nombre + ".", "Stock actualizado", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("[STOCK AUMENTADO] " + existente.getNombre() + " (+" + cantidad + ")");
            } else {
                vista.mostrarMensaje("Cantidad inválida. No se modificó el stock.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return; // Salimos sin crear un nuevo producto
    }

    // Si no existe, crear nuevo producto
    String sStock = vista.pedirDato("Stock inicial (entero):", "Crear producto");
    int stock = parseIntSafe(sStock, 0);

    Producto p = bodega.agregarProducto(nombre, stock);
    vista.listModel.addElement(p);
    vista.listaProductos.setSelectedValue(p, true);

    System.out.println("[CREADO] " + p);
}

    private void registrarEntrada() {
        Producto seleccionado = vista.listaProductos.getSelectedValue();
        if (seleccionado == null) {
            vista.mostrarMensaje("Seleccione un producto.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sCant = vista.pedirDato("Cantidad a ingresar:", "Registrar entrada");
        int cantidad = parseIntSafe(sCant, 0);
        if (cantidad <= 0) {vista.mostrarMensaje("Cantidad inválida. No se modificó el stock.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
        }
        if (bodega.registrarEntrada(seleccionado.getId(), cantidad)) {
            refrescarLista();
            vista.mostrarMensaje("Entrada registrada.", "OK", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("[ENTRADA] " + seleccionado.getNombre() + " (+" + cantidad + ")");
        }
    }

    private void registrarSalida() {
        Producto seleccionado = vista.listaProductos.getSelectedValue();
        if (seleccionado == null) {
            vista.mostrarMensaje("Seleccione un producto.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sCant = vista.pedirDato("Cantidad a vender/sacar:", "Registrar salida");
        int cantidad = parseIntSafe(sCant, 0);
        if (cantidad <= 0) return;

        if (bodega.registrarSalida(seleccionado.getId(), cantidad)) {
            refrescarLista();
            vista.mostrarMensaje("Salida registrada.", "OK", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("[SALIDA] " + seleccionado.getNombre() + " (-" + cantidad + ")");
        } else {
            vista.mostrarMensaje("Stock insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reporteInventarioTotal() {
        StringBuilder sb = new StringBuilder();
        List<Producto> productos = bodega.obtenerProductos();
        for (Producto p : productos) sb.append(p).append("\n");
        if (productos.isEmpty()) 
        JOptionPane.showMessageDialog(
                vista,
                "✅No hay productos en el inventario.✅",
                "Inventario",
                JOptionPane.INFORMATION_MESSAGE
        );
        else{
        vista.mostrarTextoLargo("Inventario total", sb.toString());
        System.out.println("\n=== REPORTE: INVENTARIO TOTAL ===\n" + sb);
    }
}

    private void reporteStockCritico() {
        String sUmbral = vista.pedirDato("Umbral (entero):", "Stock crítico");
        int umbral = parseIntSafe(sUmbral, 5);
        StringBuilder sb = new StringBuilder();
        for (Producto p : bodega.obtenerProductos()) {
            if (p.getStock() <= umbral) {
                sb.append(p).append("\n");
            }
        }
    if (sb.length() == 0) {
        JOptionPane.showMessageDialog(
                vista,
                "✅No hay productos en stock crítico.✅",
                "Stock crítico",
                JOptionPane.INFORMATION_MESSAGE
        );
    } else {
        vista.mostrarTextoCritico(
                "Stock crítico (umbral = " + umbral + ")",
                sb.toString()
        );
    }
    System.out.println("\n=== REPORTE: STOCK CRÍTICO (umbral = " + umbral + ") ===\n" + sb);
}

    private void refrescarLista() {
        vista.listModel.clear();
        for (Producto p : bodega.obtenerProductos()) {
            vista.listModel.addElement(p);
        }
    }

    private int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }
}


