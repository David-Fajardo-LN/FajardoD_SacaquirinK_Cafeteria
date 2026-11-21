/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modeloDecorador.CafeSencillo;
import modeloDecorador.CanelaEnPolvo;
import modeloDecorador.ComponenteBase;
import modeloDecorador.CremaBatida;
import modeloDecorador.EnsaladaSencilla;
import modeloDecorador.ExtraQueso;
import modeloDecorador.IngredienteAdicional;
import modeloDecorador.Malvaviscos;
import modeloDecorador.PanDeAjo;
import modeloDecorador.PizzaSencilla;
import modeloDecorador.PorcionPapas;
import modeloDecorador.SalsaCesar;
import modeloDecorador.VinagretaBalsamica;
import modeloDecorador.VinagretaItaliana;
import vista.SeleccionPedidoPanel;

/**
 *
 * @author User
 */
public class Controlador {

    private static final String GRUPO_CAFE = "CAFE";
    private static final String GRUPO_ENSALADA = "ENSALADA";
    private static final String GRUPO_PIZZA = "PIZZA";

    private final SeleccionPedidoPanel vista;
    private final JTable tabla;
    private DefaultTableModel modeloFactura;
    private DefaultTableModel modeloConfiguracion;

    private final List<ItemPedido> pedido = new ArrayList<>();
    private final List<FilaTabla> filas = new ArrayList<>();
    private ItemPedido activo = null;
    private boolean actualizando = false;

    public Controlador(SeleccionPedidoPanel vista) {
        this.vista = vista;
        this.tabla = vista.getjTable1();
        configurarTabla();
        configurarBotones();
        llenarTablaConfiguracion();
        actualizarTabla();
    }

    private static class ItemPedido {

        final ComponenteBase base;
        final String grupo;
        final List<Function<ComponenteBase, IngredienteAdicional>> fabricas = new ArrayList<>();
        final List<IngredienteAdicional> cadena = new ArrayList<>();
        ComponenteBase compuesto;

        ItemPedido(ComponenteBase base, String grupo) {
            this.base = base;
            this.grupo = grupo;
            this.compuesto = base;
        }

        void agregar(Function<ComponenteBase, IngredienteAdicional> fabrica) {
            fabricas.add(fabrica);
            reconstruir();
        }

        void quitar(int indice) {
            fabricas.remove(indice);
            reconstruir();
        }

        private void reconstruir() {
            compuesto = base;
            cadena.clear();
            for (Function<ComponenteBase, IngredienteAdicional> f : fabricas) {
                IngredienteAdicional d = f.apply(compuesto);
                cadena.add(d);
                compuesto = d;
            }
        }

        String descripcion() {
            StringBuilder sb = new StringBuilder(base.getNombre());
            for (IngredienteAdicional d : cadena) {
                sb.append(", ").append(d.getNombre());
            }
            return sb.toString();
        }
    }

    private static class FilaTabla {

        final ItemPedido item;
        final int indice;

        FilaTabla(ItemPedido item, int indice) {
            this.item = item;
            this.indice = indice;
        }
    }

    private void configurarTabla() {
        modeloFactura = new DefaultTableModel(new Object[]{"Producto", "Adicional", "Valor", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla.setModel(modeloFactura);
        tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);

        javax.swing.table.DefaultTableCellRenderer render = new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foco, int fila, int col) {
                java.awt.Component c = super.getTableCellRendererComponent(t, v, sel, foco, fila, col);
                boolean negrita = fila >= filas.size() || filas.get(fila).indice == -1;
                c.setFont(c.getFont().deriveFont(negrita ? java.awt.Font.BOLD : java.awt.Font.PLAIN));
                setHorizontalAlignment(col >= 2 ? javax.swing.SwingConstants.RIGHT
                        : javax.swing.SwingConstants.LEFT);
                return c;
            }
        };
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting() || actualizando) {
                return;
            }
            int fila = tabla.getSelectedRow();
            if (fila >= 0 && fila < filas.size()) {
                activo = filas.get(fila).item;
                tabla.setToolTipText(activo.descripcion());
            }
        });
    }

    private void configurarBotones() {
        vista.getBtnEliminar().setText("Eliminar");

        vista.getBtnCafeSencillo().addActionListener(e -> agregarPlato(new CafeSencillo(), GRUPO_CAFE));
        vista.getBtnEnsaladaSencilla().addActionListener(e -> agregarPlato(new EnsaladaSencilla(), GRUPO_ENSALADA));
        vista.getBtnPizzaSencilla().addActionListener(e -> agregarPlato(new PizzaSencilla(), GRUPO_PIZZA));

        vista.getBtnCremaBatida().addActionListener(e -> agregarAdicional(GRUPO_CAFE, c -> new CremaBatida(c)));
        vista.getBtnMalvaviscos().addActionListener(e -> agregarAdicional(GRUPO_CAFE, c -> new Malvaviscos(c)));
        vista.getBtnCanelaEnPolvo().addActionListener(e -> agregarAdicional(GRUPO_CAFE, c -> new CanelaEnPolvo(c)));

        vista.getBtnExtraQueso().addActionListener(e -> agregarAdicional(GRUPO_PIZZA, c -> new ExtraQueso(c)));
        vista.getBtnPorcionPapas().addActionListener(e -> agregarAdicional(GRUPO_PIZZA, c -> new PorcionPapas(c)));
        vista.getBtnPanDeAjo().addActionListener(e -> agregarAdicional(GRUPO_PIZZA, c -> new PanDeAjo(c)));

        vista.getBtnVinagretaItaliana().addActionListener(e -> agregarAdicional(GRUPO_ENSALADA, c -> new VinagretaItaliana(c)));
        vista.getBtnSalsaCesar().addActionListener(e -> agregarAdicional(GRUPO_ENSALADA, c -> new SalsaCesar(c)));
        vista.getBtnVinagretaBalsamica().addActionListener(e -> agregarAdicional(GRUPO_ENSALADA, c -> new VinagretaBalsamica(c)));

        vista.getBtnEliminar().addActionListener(e -> eliminarSeleccion());
    }

    private String dinero(double v) {
        return String.format(Locale.US, "$%.2f", v);
    }

    private void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje);
    }

    private void agregarPlato(ComponenteBase base, String grupo) {
        ItemPedido nuevo = new ItemPedido(base, grupo);
        pedido.add(nuevo);
        activo = nuevo;
        mostrarConfiguracionGrupo(grupo);
        actualizarTabla();
    }

    private void agregarAdicional(String grupoPermitido, Function<ComponenteBase, IngredienteAdicional> fabrica) {
        if (activo == null) {
            mostrar("Primero debe agregar o seleccionar un plato básico");
            return;
        }
        if (!activo.grupo.equals(grupoPermitido)) {
            mostrar("NO puede seleccionar la opción adicional");
            return;
        }
        activo.agregar(fabrica);
        actualizarTabla();
    }

    private void eliminarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || fila >= filas.size()) {
            mostrar("Seleccione el producto o adicional que desea eliminar");
            return;
        }
        FilaTabla f = filas.get(fila);
        if (f.indice >= 0) {
            f.item.quitar(f.indice);
            activo = f.item;
        } else {
            if (!f.item.cadena.isEmpty()) {
                mostrar("No puede eliminar el plato básico mientras tenga ingredientes adicionales. Primero debe eliminarlos");
                return;
            }
            pedido.remove(f.item);
            if (activo == f.item) {
                activo = null;
            }
        }
        actualizarTabla();
    }

    private void actualizarTabla() {
        actualizando = true;
        modeloFactura.setRowCount(0);
        filas.clear();

        double subtotal = 0;
        for (ItemPedido item : pedido) {
            double precioBase = item.base.getPrecioTotal();
            modeloFactura.addRow(new Object[]{item.base.getNombre(), "", dinero(precioBase), dinero(precioBase)});
            filas.add(new FilaTabla(item, -1));

            for (int i = 0; i < item.cadena.size(); i++) {
                IngredienteAdicional d = item.cadena.get(i);
                modeloFactura.addRow(new Object[]{"", d.getNombre(), dinero(d.getPrecioIndividual()), dinero(d.getPrecioTotal())});
                filas.add(new FilaTabla(item, i));
            }
            subtotal += item.compuesto.getPrecioTotal();
        }
        modeloFactura.addRow(new Object[]{"", "", "Subtotal", dinero(subtotal)});

        tabla.clearSelection();
        for (int i = 0; i < filas.size(); i++) {
            if (filas.get(i).item == activo && filas.get(i).indice == -1) {
                tabla.setRowSelectionInterval(i, i);
                break;
            }
        }
        tabla.setToolTipText(activo == null ? null : activo.descripcion());
        actualizando = false;
    }

    private void llenarTablaConfiguracion() {

        JTable t = vista.getTbConfiguraciónPedido();

        modeloConfiguracion = new DefaultTableModel(
                new Object[]{
                    "Plato Básico",
                    "Precio Base",
                    "Ingrediente Adicional",
                    "Precio del Adicional"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        t.setModel(modeloConfiguracion);

        javax.swing.table.DefaultTableCellRenderer derecha
                = new javax.swing.table.DefaultTableCellRenderer();

        derecha.setHorizontalAlignment(
                javax.swing.SwingConstants.RIGHT
        );

        t.getColumnModel().getColumn(1).setCellRenderer(derecha);
        t.getColumnModel().getColumn(3).setCellRenderer(derecha);
    }

    private void agregarMenu(DefaultTableModel m, ComponenteBase base, IngredienteAdicional... ingredientes) {
        for (int i = 0; i < ingredientes.length; i++) {
            m.addRow(new Object[]{
                i == 0 ? base.getNombre() : "",
                i == 0 ? dinero(base.getPrecioTotal()) : "",
                ingredientes[i].getNombre(),
                dinero(ingredientes[i].getPrecioIndividual())});
        }
    }

    private void mostrarConfiguracionGrupo(String grupo) {

        modeloConfiguracion.setRowCount(0);

        if (grupo.equals(GRUPO_CAFE)) {

            ComponenteBase cafe = new CafeSencillo();

            agregarMenu(
                    modeloConfiguracion,
                    cafe,
                    new CremaBatida(cafe),
                    new Malvaviscos(cafe),
                    new CanelaEnPolvo(cafe)
            );

        } else if (grupo.equals(GRUPO_ENSALADA)) {

            ComponenteBase ensalada = new EnsaladaSencilla();

            agregarMenu(
                    modeloConfiguracion,
                    ensalada,
                    new VinagretaItaliana(ensalada),
                    new SalsaCesar(ensalada),
                    new VinagretaBalsamica(ensalada)
            );

        } else if (grupo.equals(GRUPO_PIZZA)) {

            ComponenteBase pizza = new PizzaSencilla();

            agregarMenu(
                    modeloConfiguracion,
                    pizza,
                    new ExtraQueso(pizza),
                    new PorcionPapas(pizza),
                    new PanDeAjo(pizza)
            );
        }
    }
}
