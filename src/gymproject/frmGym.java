/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gymproject;

import Objetos.Membresia;
import Objetos.Estado;
import Objetos.Asistencia;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
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
    String[] asistencias={"Fecha/Hora","ID","Cedula","Nombre","Asistió"};
    String[] periodoDepago={"Fecha","Pago"};
    String[] tiposDeMembresia ={"ID", "Cedula", "Nombre", "Membresia"};
    String[] informacion={"ID","Cedula","Nombre","Direccion","Telefono","Membresia","precio","Pago","Estado de la Membresia",};
    public frmGym(int idUser, int profile, String username) {
        initComponents();
        registros=new ArrayList();
        membresias=new ArrayList();
        listaAsistencias = new ArrayList();
        estados=new ArrayList();
        
        //Cliente cliente1 = new Cliente(1, 1084451180 , "Jonathan", "Calle 6 13-82", 3024267778 ,"Mensual" , 70000 , "Pago", "Activo");
        
        tabPestañas.setTitleAt(0, "Inicio");
        tabPestañas.setTitleAt(1, "Reportes");
        
        membresias.add(new Membresia("Mensual",70000));
        membresias.add(new Membresia("Anual",800000));
        
        txtNombre.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
        txtDirecion.setText("");
        llenarCombo();
        llenarMembresias();
        llenarEstadoMembresias();
        llenarFiltros();
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
    
    public void llenarFiltros()
    {
        comboFiltros.removeAllItems();
        comboFiltros.addItem("Sin filtros");
        comboFiltros.addItem("Id");
        comboFiltros.addItem("Cedula");
        comboFiltros.addItem("Nombre");
    }
    
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
            String estadoDeMembresia="";
            String asistio = "";
           
            
            if(rbPago.isSelected()){
                estadoDePago="Pago";
            }
            else
            {
                estadoDePago="Pendiente";
            }
            if(rbActivo.isSelected()){
                estadoDeMembresia = "Activo";
            }
            else
            {
                estadoDeMembresia = "Inactivo";
            }
            if(rbSi.isSelected()){
                asistio = "Si";
            }
            else
            {
                asistio = "No";
            }
            
            listaAsistencias.add(new Asistencia(id, asistio));
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
        tableRegistro2.setModel(modelo);
    }
    
    // inicio de los reportes de miembros activos/inactivos
    
    /*public void miembrosActivos(){
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
        
    }*/
    public void mostrarMiembrosActivos()
    {
        DefaultTableModel modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(actividad);
        
        for (int i = 0; i < registros.size(); i++) {
            if(comboEstadoMembresia.getSelectedIndex() == 1)
            {
            if(registros.get(i).getEstadoMembresia().equalsIgnoreCase("Activo"))
            {
            modelo.addRow(new Object[]
            {
                registros.get(i).getId(),
                registros.get(i).getCedula(),
                registros.get(i).getNombre(),
                registros.get(i).getEstadoMembresia()
            });
            }
            }
            else if(comboEstadoMembresia.getSelectedIndex() == 2)
            {
                if(registros.get(i).getEstadoMembresia().equalsIgnoreCase("Inactivo"))
            {
            modelo.addRow(new Object[]
            {
                registros.get(i).getId(),
                registros.get(i).getCedula(),
                registros.get(i).getNombre(),
                registros.get(i).getEstadoMembresia()
            });
            }
            }
        }
        tableRegistro2.setModel(modelo);
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
        tableRegistro2.setModel(modelo);
        
    }
    // fin de la funcion 
    
   
    
    // funcion que llena los dos comboBox
    /*public void llenarMiembros()
    {
        comboMiembros.removeAllItems();
        comboMiembros.addItem("Seleccione un miembro");
        comboMiembros2.removeAllItems();
        comboMiembros2.addItem("Seleccione un miembro");
        
        for (int i = 0; i < registros.size(); i++) {
            comboMiembros.addItem(registros.get(i).getNombre());
            comboMiembros2.addItem(registros.get(i).getNombre());
        }
    }*/
    // fin de la funcion
    
    public void llenarMembresias()
    {
        comboMembresia.removeAllItems();
        comboMembresia.addItem("Seleccione una membresia");
        
        for (int i = 0; i < membresias.size(); i++) {
            comboMembresia.addItem(membresias.get(i).getMembresia());
        }
    }
    
    public void llenarEstadoMembresias(){
        comboEstadoMembresia.removeAllItems();
        comboEstadoMembresia.addItem("Seleccione un estado");
        comboEstadoMembresia.addItem("Activo");
        comboEstadoMembresia.addItem("Inactivo");
    }
    
    //inicio para reporte de asistencias
    public void mostrarAsistencias(){
        DefaultTableModel modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(asistencias);
        for (int i = 0; i < registros.size(); i++) {
            modelo.addRow(new Object[]
            {
                registros.get(i).getFecha(),
                registros.get(i).getId(),
                registros.get(i).getCedula(),
                registros.get(i).getNombre(),
                listaAsistencias.get(i).getAsistio()
            });
            
        }
        tableRegistro2.setModel(modelo);
    }
        
    //fin para mostrar asistencias
    
    public void tiposDeMembresia()
    {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(tiposDeMembresia);
        for (int i = 0; i < registros.size(); i++) {
            
        if(comboMembresia.getSelectedIndex()== 1)
        {
        if(registros.get(i).getMembresia().equalsIgnoreCase("Mensual"))
            {    
            modelo.addRow(new Object[]
            {
                registros.get(i).getId(),
                registros.get(i).getCedula(),
                registros.get(i).getNombre(),
                registros.get(i).getMembresia()
            });
            
        }
        tableRegistro2.setModel(modelo);
        }
        else if(comboMembresia.getSelectedIndex()== 2)
        {
        {
        if(registros.get(i).getMembresia().equalsIgnoreCase("Anual"))
            {    
            modelo.addRow(new Object[]
            {
                registros.get(i).getId(),
                registros.get(i).getCedula(),
                registros.get(i).getNombre(),
                registros.get(i).getMembresia()
            });
            
        }
        tableRegistro2.setModel(modelo);
        }
        }
        }
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
        buttonGroup3 = new javax.swing.ButtonGroup();
        tabPestañas = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        comboMembresias = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        rbPago = new javax.swing.JRadioButton();
        rbEspera = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        rbSi = new javax.swing.JRadioButton();
        rbNo = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        rbActivo = new javax.swing.JRadioButton();
        rbInactivo = new javax.swing.JRadioButton();
        btnRegistrar = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDirecion = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableRegistro = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnPeriodo = new javax.swing.JButton();
        btnMembresias = new javax.swing.JButton();
        btnAsistencias = new javax.swing.JButton();
        btnMiembrosActivos = new javax.swing.JButton();
        comboMembresia = new javax.swing.JComboBox<>();
        comboEstadoMembresia = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableRegistro2 = new javax.swing.JTable();
        btnMostrarMiembros = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        comboFiltros = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();

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

        jLabel3.setText("Telefono:");

        txtTelefono.setText("jTextField3");

        comboMembresias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Estado de pago");

        buttonGroup1.add(rbPago);
        rbPago.setText("Pago");

        buttonGroup1.add(rbEspera);
        rbEspera.setText("Espera");

        jLabel7.setText("Asistencia");

        buttonGroup3.add(rbSi);
        rbSi.setText("Si");

        buttonGroup3.add(rbNo);
        rbNo.setText("No");

        jLabel6.setText("Estado de la membresia");

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

        txtNombre.setText("jTextField2");

        jLabel2.setText("Nombre");

        jLabel4.setText("Direccion:");

        txtDirecion.setText("jTextField4");

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
        jScrollPane3.setViewportView(tableRegistro);

        jLabel9.setText("Membresia:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDirecion, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(rbPago)
                                    .addComponent(rbEspera))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(rbSi)
                                    .addComponent(rbNo))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rbInactivo)
                                            .addComponent(rbActivo))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(53, 53, 53))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboMembresias, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboMembresias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbPago)
                            .addComponent(rbSi)
                            .addComponent(rbActivo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbEspera)
                            .addComponent(rbNo)
                            .addComponent(rbInactivo)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDirecion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPestañas.addTab("tab1", jPanel1);

        jLabel8.setText("Reportes:");

        btnPeriodo.setText("Periodo de pago");
        btnPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeriodoActionPerformed(evt);
            }
        });

        btnMembresias.setText("Tipo de membresia");
        btnMembresias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMembresiasActionPerformed(evt);
            }
        });

        btnAsistencias.setText("Asistencias");
        btnAsistencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsistenciasActionPerformed(evt);
            }
        });

        btnMiembrosActivos.setText("Activos/Inactivos");
        btnMiembrosActivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMiembrosActivosActionPerformed(evt);
            }
        });

        comboMembresia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboMembresia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMembresiaActionPerformed(evt);
            }
        });

        comboEstadoMembresia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboEstadoMembresia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEstadoMembresiaActionPerformed(evt);
            }
        });

        tableRegistro2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tableRegistro2);

        btnMostrarMiembros.setText("Mostrar miembros");
        btnMostrarMiembros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarMiembrosActionPerformed(evt);
            }
        });

        jLabel10.setText("Filtro:");

        comboFiltros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnMostrarMiembros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboFiltros, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addComponent(btnPeriodo)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnMembresias)
                                .addGap(18, 18, 18)
                                .addComponent(btnAsistencias, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboMembresia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMiembrosActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboEstadoMembresia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
            .addComponent(jSeparator2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMembresias, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAsistencias, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMiembrosActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMostrarMiembros, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboMembresia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboEstadoMembresia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(comboFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPestañas.addTab("tab2", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPestañas)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPestañas, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:
        guardarInfo();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnMiembrosActivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMiembrosActivosActionPerformed
        // TODO add your handling code here:
         mostrarMiembrosActivos();
    }//GEN-LAST:event_btnMiembrosActivosActionPerformed

    private void btnPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeriodoActionPerformed
        // TODO add your handling code here:
        periodoDepago();
    }//GEN-LAST:event_btnPeriodoActionPerformed

    private void btnAsistenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsistenciasActionPerformed
        // TODO add your handling code here:
        mostrarAsistencias();
      
    }//GEN-LAST:event_btnAsistenciasActionPerformed

    private void comboMembresiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMembresiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMembresiaActionPerformed

    private void btnMembresiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMembresiasActionPerformed
        // TODO add your handling code here:
        tiposDeMembresia();
    }//GEN-LAST:event_btnMembresiasActionPerformed

    private void comboEstadoMembresiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEstadoMembresiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboEstadoMembresiaActionPerformed

    private void btnMostrarMiembrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarMiembrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMostrarMiembrosActionPerformed

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
    private javax.swing.JButton btnAsistencias;
    private javax.swing.JButton btnMembresias;
    private javax.swing.JButton btnMiembrosActivos;
    private javax.swing.JButton btnMostrarMiembros;
    private javax.swing.JButton btnPeriodo;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> comboEstadoMembresia;
    private javax.swing.JComboBox<String> comboFiltros;
    private javax.swing.JComboBox<String> comboMembresia;
    private javax.swing.JComboBox<String> comboMembresias;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JRadioButton rbActivo;
    private javax.swing.JRadioButton rbEspera;
    private javax.swing.JRadioButton rbInactivo;
    private javax.swing.JRadioButton rbNo;
    private javax.swing.JRadioButton rbPago;
    private javax.swing.JRadioButton rbSi;
    private javax.swing.JTabbedPane tabPestañas;
    private javax.swing.JTable tableRegistro;
    private javax.swing.JTable tableRegistro2;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDirecion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
