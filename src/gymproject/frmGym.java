/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gymproject;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author xdand
 */
public class frmGym extends javax.swing.JFrame {

    /**
     * Creates new form frmGym
     */
  

    ArrayList<Cliente> registros;
    ArrayList<Membresia> membresias;
    String[] actividad={"Cedula","Nombre","Asistencia"};
    String[] periodoDepago={"Fecha","Pago"};
    String[] informacion={"Nombre","Direcion","Cedula","Telefono","Membresia","precio","Asistencia","Estado","indefinido"};
    public frmGym(int idUser, int profile, String username) {
        initComponents();
        registros=new ArrayList();
        membresias=new ArrayList();
        
        membresias.add(new Membresia("Mensual",70000));
        membresias.add(new Membresia("Anual",800000));
        
        txtNombre.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
        txtDirecion.setText("");
        llenarCombo();
        mostrarInfo();
    }
   
    public void llenarCombo()
    {
        
        comboMembresias.removeAllItems();
        comboMembresias.addItem("Seleccione una membresia");
        for (int i = 0; i < membresias.size(); i++) {
            comboMembresias.addItem(membresias.get(i).getMembresia());
            
        }
        
    }
    
    /*Para registrar los usuarios bro*/
    public void guardarInfo()
    {
        if(!txtNombre.getText().isEmpty() && !txtCedula.getText().isEmpty()
                && !txtTelefono.getText().isEmpty() && !txtDirecion.getText().isEmpty()
                && comboMembresias.getSelectedIndex()>0 && (rbPago.isSelected() || rbEspera.isSelected()))
        {
            int ItemSelecionado=comboMembresias.getSelectedIndex()-1;
            String nombre=txtNombre.getText().toLowerCase();
            String direcion=txtDirecion.getText().toLowerCase();
            int telefono=Integer.parseInt(txtTelefono.getText());
            int cedula=Integer.parseInt(txtCedula.getText());
            String membresia=membresias.get(ItemSelecionado).getMembresia();
            int valorMembresia=membresias.get(ItemSelecionado).getPrecioMembresia();
            String estado="";
            String asistencia="";
            
            if(rbPago.isSelected()){
                estado="Pago";
            }
            else
            {
                estado="Pendiente";
            }
            if(rbActivo.isSelected())
            {
                asistencia="Activo";
            }
            else
            {
                asistencia="Inactivo";
            }
            
            registros.add(new Cliente(nombre,direcion,cedula,telefono,membresia,valorMembresia,asistencia,estado));
            mostrarInfo();
            txtNombre.setText("");
            txtCedula.setText("");
            txtTelefono.setText("");
            txtDirecion.setText("");

        }
    }
    
 
    
    
    /*Para mostrar la informacion de cuando se registran por si no sabes*/
    public void mostrarInfo()
    {
        DefaultTableModel modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(informacion);
        for (int i = 0; i < registros.size(); i++) {
            modelo.addRow(new Object[]
            {
                registros.get(i).getNombre(),
                registros.get(i).getCedula(),
                registros.get(i).getDireccion(),
                registros.get(i).getTelefono(),
                registros.get(i).getMembresia(),
                registros.get(i).getValorMembresia(),
                registros.get(i).getEstadodePago(),
                registros.get(i).getEstado(),
                
            });
            
            
        }
        tableRegistro.setModel(modelo);
    }
    public void mostrarMiembrosActivos()
    {
        DefaultTableModel modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(actividad);
        for (int i = 0; i < registros.size(); i++) {
            modelo.addRow(new Object[]
            {
                registros.get(i).getCedula(),
                registros.get(i).getNombre(),
                registros.get(i).getEstado()
            });
            
        }
        tableRegistro.setModel(modelo);
    }
    
    public void periodoDepago()
    {
        DefaultTableModel modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(periodoDepago);
        for (int i = 0; i < registros.size(); i++) {
            modelo.addRow(new Object[]
            {
                registros.get(i).getFecha(),
                registros.get(i).getValorMembresia()
            });
            
        }
        tableRegistro.setModel(modelo);
        
    }
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDirecion = new javax.swing.JTextField();
        comboMembresias = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rbPago = new javax.swing.JRadioButton();
        rbEspera = new javax.swing.JRadioButton();
        rbActivo = new javax.swing.JRadioButton();
        rbInactivo = new javax.swing.JRadioButton();
        btnRegistrar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableRegistro = new javax.swing.JTable();
        btnAsistencia = new javax.swing.JButton();
        btnPeriodo = new javax.swing.JButton();

        jButton1.setText("jButton1");

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jCheckBox3.setText("jCheckBox3");

        jCheckBox1.setText("jCheckBox1");

        jCheckBox2.setText("jCheckBox2");

        jToggleButton1.setText("jToggleButton1");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Cedula:");

        txtCedula.setText("jTextField1");

        jLabel2.setText("Nombre");

        txtNombre.setText("jTextField2");

        jLabel3.setText("Telefono:");

        txtTelefono.setText("jTextField3");

        jLabel4.setText("Direccion:");

        txtDirecion.setText("jTextField4");

        comboMembresias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Estado de pago");

        jLabel6.setText("Asistencia");

        buttonGroup1.add(rbPago);
        rbPago.setText("Pago");

        buttonGroup1.add(rbEspera);
        rbEspera.setText("Espera");

        buttonGroup2.add(rbActivo);
        rbActivo.setText("Activo");

        buttonGroup2.add(rbInactivo);
        rbInactivo.setText("Inactivo");

        btnRegistrar.setText("Registrar ");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        tableRegistro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableRegistro);

        btnAsistencia.setText("Activos/Inactivos");
        btnAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsistenciaActionPerformed(evt);
            }
        });

        btnPeriodo.setText("periodo de pago");
        btnPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeriodoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addComponent(txtCedula, javax.swing.GroupLayout.Alignment.LEADING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboMembresias, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbActivo)
                                    .addComponent(jLabel6)
                                    .addComponent(rbInactivo)))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(47, 47, 47)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDirecion, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addGap(30, 30, 30)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(rbPago)
                                .addComponent(rbEspera)))))
                .addGap(6, 6, 6))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboMembresias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDirecion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbActivo)
                            .addComponent(rbPago))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbInactivo)
                            .addComponent(rbEspera))))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(btnAsistencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPeriodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:
        guardarInfo();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsistenciaActionPerformed
        // TODO add your handling code here:
        mostrarMiembrosActivos();
    }//GEN-LAST:event_btnAsistenciaActionPerformed

    private void btnPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeriodoActionPerformed
        // TODO add your handling code here:
        periodoDepago();
    }//GEN-LAST:event_btnPeriodoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmGym.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmGym.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmGym.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmGym.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmGym(1,2,"").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsistencia;
    private javax.swing.JButton btnPeriodo;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> comboMembresias;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JRadioButton rbActivo;
    private javax.swing.JRadioButton rbEspera;
    private javax.swing.JRadioButton rbInactivo;
    private javax.swing.JRadioButton rbPago;
    private javax.swing.JTable tableRegistro;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDirecion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
