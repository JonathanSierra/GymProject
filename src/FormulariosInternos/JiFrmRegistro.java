/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package FormulariosInternos;

import Objetos.Asistencia;
import Objetos.Cliente;
import Objetos.Estado;
import Objetos.Membresia;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author xdand
 */
public class JiFrmRegistro extends javax.swing.JInternalFrame {

    /**
     * Creates new form JiFrmRegistro
     */
    
   
    ArrayList<Cliente> registros;
    ArrayList<Membresia> membresias;
    private int idContador = 0;
    String[] informacion={"ID","Cedula","Nombre","Direccion","Telefono","Membresia","precio","Pago","Estado de la Membresia",};
    public JiFrmRegistro(ArrayList<Cliente> registros) {
        initComponents();
        this.registros=registros;
        membresias=new ArrayList();
        //Cliente cliente1 = new Cliente(1, 1084451180 , "Jonathan", "Calle 6 13-82", 3024267778 ,"Mensual" , 70000 , "Pago", "Activo");
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
    
    /*public void llenarFiltros()
    {
        comboFiltros.removeAllItems();
        comboFiltros.addItem("Sin filtros");
        comboFiltros.addItem("Id");
        comboFiltros.addItem("Cedula");
        comboFiltros.addItem("Nombre");
    }*/
    
    /*Para registrar los usuarios bro*/
    public void guardarInfo()
    {
        try
        {
        if(!txtNombre.getText().isEmpty() && !txtCedula.getText().isEmpty()
                && !txtTelefono.getText().isEmpty() && !txtDirecion.getText().isEmpty()
                && comboMembresias.getSelectedIndex()>0 && (rbPago.isSelected() || rbEspera.isSelected()))
        {
            int ItemSelecionado=comboMembresias.getSelectedIndex()-1;
            
            idContador ++;
            int id = idContador;
            String nombre=txtNombre.getText().toLowerCase();
            String direccion=txtDirecion.getText().toLowerCase();
            int telefono=Integer.parseInt(txtTelefono.getText());
            int cedula=Integer.parseInt(txtCedula.getText());
            String membresia=membresias.get(ItemSelecionado).getMembresia();
            int valorMembresia=membresias.get(ItemSelecionado).getPrecioMembresia();
            String estadoDePago="";
            String estadoDeMembresia="Activo";
            String asistio = "";
           
            
            if(rbPago.isSelected()){
                estadoDePago="Pago";
            }
            else
            {
                estadoDePago="Pendiente";
            }
         
            
            registros.add(new Cliente(id, cedula,nombre,direccion,telefono,membresia,valorMembresia,estadoDeMembresia,estadoDePago));
            JOptionPane.showMessageDialog(rootPane, 
                    "Miembro registrado con exito");
            mostrarInfo();
            txtNombre.setText("");
            txtCedula.setText("");
            txtTelefono.setText("");
            txtDirecion.setText("");
            
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane, 
                    "Faltan campos por completar");
        }
        } catch(Exception e){
                        JOptionPane.showMessageDialog(rootPane, 
                    "Error al ingresar el miembro, Intenta de nuevo");
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
                //{"ID","Cedula","Nombre","Direccion","Telefono","Membresia","precio","Pago","Estado de la Membresia",};
                registros.get(i).getId(),
                registros.get(i).getCedula(),
                registros.get(i).getNombre(),
                registros.get(i).getDireccion(),
                registros.get(i).getTelefono(),
                registros.get(i).getMembresia(),
                registros.get(i).getValorMembresia(),
                registros.get(i).getEstadodePago(),
                registros.get(i).getEstadoMembresia()
              
                
            });
            
            
        }
        tableRegistro.setModel(modelo);
       
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        rbEspera = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableRegistro = new javax.swing.JTable();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        txtCedula = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        comboMembresias = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtDirecion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        rbPago = new javax.swing.JRadioButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        buttonGroup1.add(rbEspera);
        rbEspera.setText("Espera");

        jLabel1.setText("Nombre:");

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

        txtNombre.setText("jTextField1");

        jLabel2.setText("Cedula:");

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        txtCedula.setText("jTextField1");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Estado de pago:");

        comboMembresias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Direccion:");

        txtDirecion.setText("jTextField1");

        jLabel4.setText("Telefono:");

        txtTelefono.setText("jTextField1");

        buttonGroup1.add(rbPago);
        rbPago.setText("Pago");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)))
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(txtDirecion))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboMembresias, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(rbEspera)
                    .addComponent(rbPago))
                .addGap(59, 59, 59))
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDirecion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboMembresias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbPago)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rbEspera))))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:
        guardarInfo();
    }//GEN-LAST:event_btnRegistrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboMembresias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbEspera;
    private javax.swing.JRadioButton rbPago;
    private javax.swing.JTable tableRegistro;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDirecion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
