package test;

import modelo.Producto;
import modelo.Bodega;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class BodegaProductoTest {

    private Bodega bodega;
    private Producto producto1;
    private Producto producto2;

    @Before
    public void setUp() {
        bodega = new Bodega();
        producto1 = bodega.agregarProducto("Manzanas", 10);
        producto2 = bodega.agregarProducto("Peras", 5);
    }

    @Test
    public void testAgregarStock() {
        producto1.agregarStock(5);
        assertEquals(15, producto1.getStock());
    }

    @Test
    public void testAgregarStockNegativo() {
        int stockOriginal = producto1.getStock();
        producto1.agregarStock(-5);
        assertEquals(stockOriginal, producto1.getStock());
    }

    @Test
    public void testRestarStock() {
        assertTrue(producto1.restarStock(3));
        assertEquals(7, producto1.getStock());
    }

    @Test
    public void testRestarStockMayorQueDisponible() {
        assertFalse(producto2.restarStock(10));
        assertEquals(5, producto2.getStock());
    }

    @Test
    public void testRestarStockNegativo() {
        assertFalse(producto1.restarStock(-2));
        assertEquals(10, producto1.getStock());
    }

    @Test
    public void testRegistrarEntrada() {
        assertTrue(bodega.registrarEntrada(producto1.getId(), 10));
        assertEquals(20, producto1.getStock());
    }

    @Test
    public void testRegistrarEntradaConIdInexistente() {
        assertFalse(bodega.registrarEntrada(999, 5));
    }

    @Test
    public void testRegistrarSalidaExitosa() {
        assertTrue(bodega.registrarSalida(producto2.getId(), 3));
        assertEquals(2, producto2.getStock());
    }

    @Test
    public void testRegistrarSalidaStockInsuficiente() {
        assertFalse(bodega.registrarSalida(producto2.getId(), 10));
        assertEquals(5, producto2.getStock());
    }

    @Test
    public void testObtenerProductos() {
        List<Producto> lista = bodega.obtenerProductos();
        assertEquals(2, lista.size());
        assertTrue(lista.contains(producto1));
        assertTrue(lista.contains(producto2));
    }

    @Test
    public void testToStringProducto() {
        String texto = producto1.toString();
        assertTrue(texto.contains("Manzanas"));
        assertTrue(texto.contains("stock"));
    }
}
