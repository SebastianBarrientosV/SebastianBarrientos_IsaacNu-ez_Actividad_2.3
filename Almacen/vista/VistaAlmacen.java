package vista;

import modelo.Producto;

import javax.swing.*;
import java.awt.*;

public class VistaAlmacen extends JFrame {
    public final DefaultListModel<Producto> listModel = new DefaultListModel<>();
    public final JList<Producto> listaProductos = new JList<>(listModel);

    public final JButton btnCrear = new JButton("Crear producto");
    public final JButton btnEntrada = new JButton("Reponoer stock");
    public final JButton btnSalida = new JButton("Vender Producto");
    public final JButton btnInventario = new JButton("Reporte: Inventario total");
    public final JButton btnCritico = new JButton("Reporte: Stock crítico");
    public final JButton btnRefrescar = new JButton("Refrescar lista");

    public VistaAlmacen() {
        super("Almacén");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(listaProductos);

        JPanel panelBotones = new JPanel(new GridLayout(0, 1, 5, 5));
        panelBotones.add(btnCrear);
        panelBotones.add(btnEntrada);
        panelBotones.add(btnSalida);
        panelBotones.add(btnInventario);
        panelBotones.add(btnCritico);
        panelBotones.add(btnRefrescar);

        setLayout(new BorderLayout(6, 6));
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.EAST);
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public String pedirDato(String mensaje, String titulo) {
        return JOptionPane.showInputDialog(this, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
    }

    public void mostrarTextoLargo(String titulo, String contenido) {
        JTextArea area = new JTextArea(contenido);
        area.setEditable(false);
        JScrollPane sp = new JScrollPane(area);
        sp.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, sp, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    public void mostrarTextoCritico(String titulo, String contenido) {
        JTextArea area = new JTextArea(contenido);
        area.setEditable(false);
        area.setForeground(Color.RED);
        JScrollPane sp = new JScrollPane(area);
        sp.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, sp, titulo, JOptionPane.WARNING_MESSAGE);
    }
}
