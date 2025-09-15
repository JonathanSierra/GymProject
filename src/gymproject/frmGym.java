/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gymproject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author xdand & jonat
 */
public class frmGym extends javax.swing.JFrame {

    /**
     * Creates new form frmGym
     */
  
    ArrayList<Estado> estados;
    ArrayList<Cliente> registros;
    ArrayList<Membresia> membresias;
    ArrayList<Asistencia> listaAsistencias;
    private int idContador = 0;
    String[] actividad={"ID", "Cedula","Nombre","Estado de la Membresia"};
    String[] asistencias={"ID","Cedula","Nombre","Dias","Horas"};
    String[] periodoDepago={"Fecha","Pago"};
    String[] informacion={"ID","Cedula","Nombre","Direccion","Telefono","Membresia","precio","Pago","Estado de la Membresia",};
    public frmGym(int idUser, int profile, String username) {
        initComponents();
        registros=new ArrayList();
        membresias=new ArrayList();
        listaAsistencias = new ArrayList();
        estados=new ArrayList();
        
        membresias.add(new Membresia("Mensual",70000));
        membresias.add(new Membresia("Anual",800000));
        
        txtNombre.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
        txtDirecion.setText("");
        txtDia.setText("");
        txtHora.setText("");
        llenarCombo();
        llenarMiembros();
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
            
            idContador ++;
            int id = idContador;
            String nombre=txtNombre.getText().toLowerCase();
            String direccion=txtDirecion.getText().toLowerCase();
            int telefono=Integer.parseInt(txtTelefono.getText());
            int cedula=Integer.parseInt(txtCedula.getText());
            String membresia=membresias.get(ItemSelecionado).getMembresia();
            int valorMembresia=membresias.get(ItemSelecionado).getPrecioMembresia();
            String estado="";
           
           
            
            if(rbPago.isSelected()){
                estado="Pago";
            }
            else
            {
                estado="Pendiente";
            }
         
            
            
            registros.add(new Cliente(id, cedula,nombre,direccion,telefono,membresia,valorMembresia,estado));
            JOptionPane.showMessageDialog(rootPane, 
                    "Miembro registrado con exito");
            mostrarInfo();
            llenarMiembros();
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
    }
    
 
    
    
    /*Para mostrar la informacion de cuando se registran por si no sabes*/
    public void mostrarInfo()
    {
        DefaultTableModel modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(informacion);
        for (int i = 0; i < registros.size(); i++) {
            modelo.addRow(new Object[]
            {
                registros.get(i).getId(),
                registros.get(i).getCedula(),
                registros.get(i).getNombre(),
                registros.get(i).getDireccion(),
                registros.get(i).getTelefono(),
                registros.get(i).getMembresia(),
                registros.get(i).getValorMembresia(),
                registros.get(i).getEstadodePago(),
              
                
            });
            
            
        }
        tableRegistro.setModel(modelo);
    }
    
    // inicio de los reportes de miembros activos/inactivos
    
    public void miembrosActivos(){
        String estado="";
        if(comboMiembros2.getSelectedIndex()>0 && (rbActivo.isSelected() || rbInactivo.isSelected()))
        {
            int itemSelecionado=comboMiembros2.getSelectedIndex()-1;
            if(rbActivo.isSelected())
            {
                estado="Activo";
            }
            else
            {
                estado="Inactivo";
            }
            Cliente cliente=registros.get(itemSelecionado);
            estados.add(new Estado(estado,cliente));
            mostrarMiembrosActivos();
            
        }
        
    }
    public void mostrarMiembrosActivos()
    {
        
        DefaultTableModel modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(actividad);
        
        for (int i = 0; i < estados.size(); i++) {
            Estado e=estados.get(i);
            modelo.addRow(new Object[]
            {
                e.getCliente().getId(),
                e.getCliente().getCedula(),
                e.getCliente().getNombre(),
                e.getEstado()
            });
            
        }
        tableRegistro.setModel(modelo);
    }
    
    // fin de los reportes de miembors activos/inactivos
    
    // funcion para mostrar los periodos de pago
    public void periodoDepago()
    {
        DefaultTableModel modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(periodoDepago);
        for (int i = 0; i < registros.size(); i++) {
            if(registros.get(i).getEstadodePago().equalsIgnoreCase("pago"))
            {
                modelo.addRow(new Object[]
                {
                    registros.get(i).getFecha(),
                    registros.get(i).getValorMembresia()
                });
                
            }
           
            
        }
        tableRegistro.setModel(modelo);
        
    }
    // fin de la funcion 
    
   
    
    // funcion que llena los dos comboBox
    public void llenarMiembros()
    {
        comboMiembros.removeAllItems();
        comboMiembros.addItem("Seleccione un miembro");
        comboMiembros2.removeAllItems();
        comboMiembros2.addItem("Seleccione un miembro");
        
        for (int i = 0; i < registros.size(); i++) {
            comboMiembros.addItem(registros.get(i).getNombre());
            comboMiembros2.addItem(registros.get(i).getNombre());
        }
    }
    // fin de la funcion
    
    
    //inicio para reporte de asistencias
    public void Asistencia()
    {
        if(!txtDia.getText().isEmpty() && !txtHora.getText().isEmpty()
                && comboMiembros.getSelectedIndex()>0)
        {
            int itemSelecionado=comboMiembros.getSelectedIndex()-1;
            String dia=txtDia.getText();
            int hora=Integer.parseInt(txtHora.getText());
             Cliente cliente = registros.get(itemSelecionado);
            
            listaAsistencias.add(new Asistencia(dia,hora,cliente));
            mostrarAsistencias();
        }
        
        
    }
    public void mostrarAsistencias()
    {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(asistencias);
        for (int i = 0; i < listaAsistencias.size(); i++) {
            Asistencia a=listaAsistencias.get(i);
            modelo.addRow(new Object[]
            {
                a.getCliente().getId(),
                a.getCliente().getCedula(),
                a.getCliente().getNombre(),
                a.getDia(),
                a.getHora()

            });
            
        }
        tableRegistro.setModel(modelo);
    }
    //fin para mostrar asistencias
   
    
    
    

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
        buttonGroup3 = new javax.swing.ButtonGroup();
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
        btnCitas = new javax.swing.JButton();
        btnAsistencias = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        comboMiembros = new javax.swing.JComboBox<>();
        txtDia = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        comboMiembros2 = new javax.swing.JComboBox<>();

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

        jLabel6.setText("Estado de la membresia");

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

        btnPeriodo.setText("Periodo de pago");
        btnPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeriodoActionPerformed(evt);
            }
        });

        btnCitas.setText("Tipo de membresia");

        btnAsistencias.setText("Asistencias");
        btnAsistencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsistenciasActionPerformed(evt);
            }
        });

        jLabel8.setText("Reportes:");

        comboMiembros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtDia.setText("jTextField1");

        txtHora.setText("jTextField2");

        jLabel9.setText("Dia:");

        jLabel10.setText("Hora:");

        comboMiembros2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(58, 58, 58)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel10)))
                                .addComponent(comboMiembros, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(rbActivo)
                                .addComponent(rbInactivo))
                            .addComponent(btnAsistencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboMembresias, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtDirecion, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbPago)
                                    .addComponent(rbEspera))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboMiembros2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAsistencias, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPeriodo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCitas)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(comboMiembros2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbInactivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbActivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDirecion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(comboMembresias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbPago)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbEspera)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(comboMiembros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAsistencias, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
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
         miembrosActivos();
    }//GEN-LAST:event_btnAsistenciaActionPerformed

    private void btnPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeriodoActionPerformed
        // TODO add your handling code here:
        periodoDepago();
    }//GEN-LAST:event_btnPeriodoActionPerformed

    private void btnAsistenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsistenciasActionPerformed
        // TODO add your handling code here:
        Asistencia();
      
    }//GEN-LAST:event_btnAsistenciasActionPerformed

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
    private javax.swing.JButton btnAsistencias;
    private javax.swing.JButton btnCitas;
    private javax.swing.JButton btnPeriodo;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> comboMembresias;
    private javax.swing.JComboBox<String> comboMiembros;
    private javax.swing.JComboBox<String> comboMiembros2;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JTextField txtDia;
    private javax.swing.JTextField txtDirecion;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
