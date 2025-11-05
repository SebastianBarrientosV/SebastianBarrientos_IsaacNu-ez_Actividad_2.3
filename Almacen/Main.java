import modelo.Bodega;
import vista.VistaAlmacen;
import controlador.ControladorAlmacen;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Bodega modelo = new Bodega();
            VistaAlmacen vista = new VistaAlmacen();
            new ControladorAlmacen(modelo, vista);
            vista.setVisible(true);
        });
    }
}