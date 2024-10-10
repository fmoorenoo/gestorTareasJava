package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.Modelo;
import vistas.Vista;


public class Controlador implements ActionListener {
    private Vista vista;
    private Modelo modelo;

    public Controlador(Vista vista, Modelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        this.vista.setTitle("Administrador de procesos");
        this.vista.btn_actualizar.addActionListener(this);
        this.vista.btn_nuevo.addActionListener(this);
        this.vista.btn_matar.addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btn_actualizar) {
            List<List<String>> matrizProcesos = modelo.ListarProcesos();
            
            DefaultTableModel model = new DefaultTableModel();
            
            for (int j=0; j<3; j++) {
                model.addColumn(matrizProcesos.get(0).get(j));
            }
            for (int i=1; i < matrizProcesos.size(); i++) {
                model.addRow(new Object[]{matrizProcesos.get(i).get(0), matrizProcesos.get(i).get(1), matrizProcesos.get(i).get(2)});
            }
            vista.tabla_procesos.setModel(model);
            vista.numero_procesos.setText(Integer.toString(matrizProcesos.size()) + " procesos");
            
        } else if (e.getSource() == vista.btn_nuevo) {
            modelo.LanzarProcesos();
            vista.btn_actualizar.doClick();
            
        } else if (e.getSource() == vista.btn_matar) {
            List<List<String>> tablaProcesos = modelo.ListarProcesos();

            String pid = JOptionPane.showInputDialog(null, "Mata el proceso con PID:");
            if (pid != null) {
                modelo.MatarProcesos(pid);
                vista.btn_actualizar.doClick();
            } else {
                JOptionPane.showMessageDialog(null, "PID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
