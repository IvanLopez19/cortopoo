/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import dao.ProductoDao;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;

/**
 *
 * @author LN710Q
 */
public class Consulta extends JFrame{
    public JLabel lblCodigo, lblPrecio, lblCantidad, lblNombre,lblDisponibilidad;
    
    public JTextField codigo, precio, cantidad,nombre;
    public JComboBox tipo;
    
    ButtonGroup existencia= new ButtonGroup();
    public JRadioButton no;
    public JRadioButton si;
    public JTable resultados;
    
    public JPanel table;
    
    public JButton buscar, eliminar, insertar, limpiar, actualizar;
    
    private static final int ANCHOC= 130, ALTOC=30;
    
    DefaultTableModel tm;
    
    public Consulta(){
        super("Inventario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        agregarLabels();
        formulario();
        llenarTabla();
        Container container = getContentPane();
        container.add(lblCodigo);
        container.add(lblPrecio);
        container.add(lblCantidad);
        container.add(lblDisponibilidad);
        container.add(lblNombre);
        container.add(codigo);
        container.add(precio);
        container.add(cantidad);
        container.add(si);
        container.add(no);
        container.add(buscar);
        container.add(insertar);
        container.add(actualizar);
        container.add(eliminar);
        container.add(limpiar);
        container.add(table);
        setSize(600, 600);
        eventos();
    }
    
    public final void agregarLabels(){
        lblCodigo=new JLabel("Codigo");
        lblNombre= new JLabel("Nombre");
        lblCantidad= new JLabel("Cantidad");
        lblPrecio= new JLabel("Precio");
        lblDisponibilidad= new JLabel("Disponibilidad");
        lblCodigo.setBounds(10,10,ANCHOC,ALTOC);
        lblNombre.setBounds(10,60,ANCHOC,ALTOC);
        lblCantidad.setBounds(10,100,ANCHOC,ALTOC);
        lblDisponibilidad.setBounds(10,140,ANCHOC,ALTOC);
    }
    
    public final void formulario(){
        codigo=new JTextField();
        tipo=new JComboBox();
        cantidad= new JTextField();
        si= new JRadioButton("si",true);
        no= new JRadioButton("no");
        resultados= new JTable();
        buscar= new JButton("Buscar");
        insertar= new JButton("insertar");
        eliminar= new JButton("Eliminar");
        actualizar= new JButton("Actualizar");
        limpiar = new JButton("Limpiar");
        
        table= new JPanel();
        
        
        existencia= new ButtonGroup();
        existencia.add(si);
        existencia.add(no);
        
        codigo.setBounds(140,10,ANCHOC,ALTOC);
        tipo.setBounds(140,60,ANCHOC,ALTOC);
        si.setBounds(140,10,50,ALTOC);
        no.setBounds(140,10,50,ALTOC);
        
        buscar.setBounds(300,10,ANCHOC,ALTOC);
        insertar.setBounds(10,210,ANCHOC,ALTOC);
        actualizar.setBounds(150,210,ANCHOC,ALTOC);
        eliminar.setBounds(300,210,ANCHOC,ALTOC);
        limpiar.setBounds(450,210,ANCHOC,ALTOC);
        resultados= new JTable();
        table.setBounds(10, 250, 500, 200);
        table.add(new JScrollPane(resultados));
    }
    
    public void llenarTabla(){
        
        tm= new DefaultTableModel(){
            public Class<?> getColmnClass(int column){
                switch (column){
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        
        tm.addColumn("Codigo");
        tm.addColumn("Marca");
        tm.addColumn("Stock");
        tm.addColumn("Sotck en Sucursal");
        
        ProductoDao fd=new ProductoDao();
        ArrayList<Producto> productos= fd.readAll();
        
        for(Producto f1:productos){
            tm.addRow(new Object[]{f1.getCodigo(), f1.getTipo(), f1.getPrecio(), f1.getCantidad(),f1.getDisponibilidad()});
        }
        
        resultados.setModel(tm);
    }
    
    public void eventos(){
        insertar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd= new ProductoDao();
                Producto f= new Producto(nombre.getText(),codigo.getText(),Integer.parseInt(precio.getText()),Integer.parseInt(cantidad.getText()),true);
                
                if(no.isSelected()){
                    f.setDisponibilidad(false);
                }
                if(fd.create(f)){
                    JOptionPane.showMessageDialog(null,"Filtro registrado con exito");
                    limpiarCampos();
                    llenarTabla();
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un problem al momento de crear el filtro");
                }
            }
        
        });
        actualizar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd= new ProductoDao();
                Producto f = new Producto(nombre.getText(),codigo.getText(),Integer.parseInt(precio.getText()),Integer.parseInt(cantidad.getText()),true);
                
                if(no.isSelected()){
                    f.setDisponibilidad(false);
                }
                
                if(fd.update(f)){
                    JOptionPane.showMessageDialog(null, "Filtro Modificado con exito");
                    limpiarCampos();
                    llenarTabla();
                } else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento ");
                }
            }
        });
        eliminar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd= new ProductoDao();
                
                if(fd.delete(codigo.getText())){
                    JOptionPane.showMessageDialog(null, "Filtro Eliminado con exito");
                    limpiarCampos();
                    llenarTabla();
                } else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento de eliminar el filtro");
                }
            }
        });
        eliminar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd= new ProductoDao();
                
                if(fd.delete(codigo.getText())){
                    JOptionPane.showMessageDialog(null, "Filtro Eliminado con exito");
                    limpiarCampos();
                    llenarTabla();
                } else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento de eliminar el filtro");
                }
            }
        });
        buscar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd= new ProductoDao();
                Producto f= fd.read(codigo.getText());
                
                if(f==null){
                    JOptionPane.showMessageDialog(null, "Filtro Eliminado con exito");
                } else{
                    codigo.setText(f.getCodigo());
                    tipo.setSelectedItem(f.getTipo());
                    cantidad.setText(Integer.toString(f.getCantidad()));
                    
                    if (f.getDisponibilidad()){
                        si.setSelected(true);
                    } else {
                        no.setSelected(true);
                    }
                }
            }
        });
        
        limpiar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }   
        });
        
    }
    
    public void limpiarCampos() {
        codigo.setText("");
        tipo.setSelectedItem("FRAM");
        cantidad.setText("");
    }
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable(){
        @Override
        public void run(){
            new Consulta().setVisible(true);
        }
    });
    }
        
}
