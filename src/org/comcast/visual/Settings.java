/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.comcast.visual;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.comcast.builder.Client;
import org.comcast.builder.ClientBuilder;
import org.comcast.builder.Mail;
import org.comcast.builder.MailBuilder;
import org.comcast.exceptions.FTPConectionRefusedException;
import org.comcast.exceptions.InformationRequiredException;
import org.comcast.logic.ServerConfig;
import org.comcast.logic.Validator;
import org.comcast.router.RouterRetrieve;
import org.comcast.xml.Loader;
import org.comcast.xml.LoaderProvider;
import org.xml.sax.SAXException;

/**
 *
 * @author Quality of Service
 */
public class Settings extends javax.swing.JDialog {

    private ResourceBundle settings_es_ES;
    private Loader loader = LoaderProvider.getInstance();
    private Client c;
    private ServerConfig config;
    private Mail m;

    /**
     * Creates new form Settings
     */
    public Settings() {
        locale();
        initComponents();
        setImageIconFrame();
        centrarPantalla();
        initObjects();
        initAll();
    }

    private void locale() {
        try {
            Client cc = LoaderProvider.getInstance().getClientConfiguration();

            switch (cc.getLocalization()) {
                case "Español":
                    settings_es_ES = ResourceBundle.getBundle("org/comcast/locale/Settings_es_ES");
                    break;
                case "Ingles":
                    settings_es_ES = ResourceBundle.getBundle("org/comcast/locale/Settings_en_US");
                    break;
                default:
                    settings_es_ES = ResourceBundle.getBundle("org/comcast/locale/Settings_en_US");
                    break;
            }
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException | URISyntaxException | InformationRequiredException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void centrarPantalla() {
        Dimension tamFrame = this.getSize();//para obtener las dimensiones del frame
        Dimension tamPantalla = Toolkit.getDefaultToolkit().getScreenSize();//para obtener el tamaño de la pantalla
        setLocation((tamPantalla.width - tamFrame.width) / 2, (tamPantalla.height - tamFrame.height) / 2);//para posicionar
        this.setModalityType(ModalityType.APPLICATION_MODAL);
    }

    private void setImageIconFrame() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../images/Computer-32.png"))); //NOI18N
    }

    private void initObjects() {
        try {
            config = loader.getServerConfiguration();
            this.c = loader.getClientConfiguration();
            m = loader.getMail();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initAll() {
        try {
            this.txtNombreServidor.setText(config.getHostName());
            this.txtNombreUsuario.setText(config.getUserLogin());
            this.txtPasswordServidor.setText(config.getPassLogin());
            this.txtIPServidor.setText(config.getIpAddress());

            this.txtHost.setText(m.getProperties().getProperty("mail.smtp.host"));
            this.txtTLS.setText(m.getProperties().getProperty("mail.smtp.starttls.enable"));
            this.txtPuerto.setText(m.getProperties().getProperty("mail.smtp.port"));
            this.txtUsuario.setText(m.getProperties().getProperty("mail.smtp.user"));
            this.txtAutenticacion.setText(m.getProperties().getProperty("mail.smtp.auth"));

            this.txtFrom.setText(m.getFrom());
            this.txtRecipient.setText(m.getRecipient());
            this.txtSubject.setText(m.getSubject());
            this.txtProtocolo.setText(m.getSendProtocol());
            this.txtAreaCuerpo.setText(m.getMailText());
            this.txtUsuarioMail.setText(m.getMailUserName());
            this.txtContraseniaMail.setText(m.getMailUserPassword());
            this.txtContraseniaMail.setEnabled(false);

            this.txtNombreCliente.setText(c.getClientName());
            this.txtApellidoCliente.setText(c.getClientLastName());
            this.txtOrganizacion.setText(c.getOrganization());
            this.txtDescarga.setText(c.getDownloadPath());
            this.txtPublica.setText(c.getPublicStorage());
            this.txtPrivada.setText(c.getPrivateStorage());
            this.comboLocale.setSelectedItem(c.getLocalization());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedSettings = new javax.swing.JTabbedPane();
        panelConfiguracionServidor = new javax.swing.JPanel();
        contenedorServidor = new javax.swing.JPanel();
        lblNombreServidor = new javax.swing.JLabel();
        lblNombreUsuario = new javax.swing.JLabel();
        lblContraseniaUsuario = new javax.swing.JLabel();
        lblIPServidor = new javax.swing.JLabel();
        txtNombreServidor = new javax.swing.JTextField();
        txtNombreUsuario = new javax.swing.JTextField();
        txtIPServidor = new javax.swing.JTextField();
        btnGuardarServidor = new javax.swing.JButton();
        txtPasswordServidor = new javax.swing.JPasswordField();
        checkCServidor = new javax.swing.JCheckBox();
        btnConexion = new javax.swing.JButton();
        panelMail = new javax.swing.JPanel();
        contenedorMail = new javax.swing.JPanel();
        contenedorMailProps = new javax.swing.JPanel();
        lblHost = new javax.swing.JLabel();
        lblTLS = new javax.swing.JLabel();
        lblPuerto = new javax.swing.JLabel();
        txtHost = new javax.swing.JTextField();
        txtTLS = new javax.swing.JTextField();
        txtPuerto = new javax.swing.JTextField();
        lblUsuario = new javax.swing.JLabel();
        lblAutenticacion = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtAutenticacion = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblFrom = new javax.swing.JLabel();
        lblRecipient = new javax.swing.JLabel();
        lblSubject = new javax.swing.JLabel();
        lblProtocolo = new javax.swing.JLabel();
        lblUsuarioMail = new javax.swing.JLabel();
        lblContraseniaMail = new javax.swing.JLabel();
        txtFrom = new javax.swing.JTextField();
        txtRecipient = new javax.swing.JTextField();
        txtSubject = new javax.swing.JTextField();
        txtProtocolo = new javax.swing.JTextField();
        txtUsuarioMail = new javax.swing.JTextField();
        txtContraseniaMail = new javax.swing.JPasswordField();
        lblTextoContenido = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaCuerpo = new javax.swing.JTextArea();
        btnGuardarMail = new javax.swing.JButton();
        checkCMail = new javax.swing.JCheckBox();
        panelConfiguracionCliente = new javax.swing.JPanel();
        contenedorCliente = new javax.swing.JPanel();
        lblNombreCliente = new javax.swing.JLabel();
        lblApellidoCliente = new javax.swing.JLabel();
        lblOrganizacion = new javax.swing.JLabel();
        lblLocale = new javax.swing.JLabel();
        lblDescarga = new javax.swing.JLabel();
        lblPublico = new javax.swing.JLabel();
        lblPrivado = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        txtApellidoCliente = new javax.swing.JTextField();
        txtOrganizacion = new javax.swing.JTextField();
        comboLocale = new javax.swing.JComboBox();
        txtDescarga = new javax.swing.JTextField();
        btnDescarga = new javax.swing.JButton();
        txtPublica = new javax.swing.JTextField();
        txtPrivada = new javax.swing.JTextField();
        btnKeys = new javax.swing.JButton();
        btnGuardarCliente = new javax.swing.JButton();

        setTitle(settings_es_ES.getString("CONFIGURACION")); // NOI18N

        contenedorServidor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, settings_es_ES.getString("Servidor"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11), new java.awt.Color(255, 51, 51))); // NOI18N

        lblNombreServidor.setText(settings_es_ES.getString("NOMBRE DEL SERVIDOR")); // NOI18N

        lblNombreUsuario.setText(settings_es_ES.getString("NOMBRE DEL USUARIO")); // NOI18N

        lblContraseniaUsuario.setText(settings_es_ES.getString("CONTRASEÑA DEL USUARIO")); // NOI18N

        lblIPServidor.setText(settings_es_ES.getString("IP DEL SERVIDOR FTP")); // NOI18N

        txtIPServidor.setEnabled(false);

        btnGuardarServidor.setText(settings_es_ES.getString("GUARDAR CONFIGURACION")); // NOI18N
        btnGuardarServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarServidorActionPerformed(evt);
            }
        });

        txtPasswordServidor.setEnabled(false);

        checkCServidor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkCServidorStateChanged(evt);
            }
        });

        btnConexion.setText(settings_es_ES.getString("COMPROBAR CONEXION")); // NOI18N
        btnConexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConexionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contenedorServidorLayout = new javax.swing.GroupLayout(contenedorServidor);
        contenedorServidor.setLayout(contenedorServidorLayout);
        contenedorServidorLayout.setHorizontalGroup(
            contenedorServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorServidorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(contenedorServidorLayout.createSequentialGroup()
                        .addComponent(btnConexion)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarServidor))
                    .addGroup(contenedorServidorLayout.createSequentialGroup()
                        .addGroup(contenedorServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreServidor)
                            .addComponent(lblNombreUsuario)
                            .addComponent(lblContraseniaUsuario)
                            .addComponent(lblIPServidor))
                        .addGap(126, 126, 126)
                        .addGroup(contenedorServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombreServidor)
                            .addComponent(txtNombreUsuario)
                            .addComponent(txtIPServidor, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                            .addComponent(txtPasswordServidor))))
                .addGap(18, 18, 18)
                .addComponent(checkCServidor)
                .addContainerGap(7, Short.MAX_VALUE))
        );
        contenedorServidorLayout.setVerticalGroup(
            contenedorServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorServidorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreServidor)
                    .addComponent(txtNombreServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(contenedorServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreUsuario)
                    .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(contenedorServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenedorServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblContraseniaUsuario)
                        .addComponent(txtPasswordServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(checkCServidor))
                .addGap(18, 18, 18)
                .addGroup(contenedorServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIPServidor)
                    .addComponent(txtIPServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(contenedorServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarServidor)
                    .addComponent(btnConexion))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout panelConfiguracionServidorLayout = new javax.swing.GroupLayout(panelConfiguracionServidor);
        panelConfiguracionServidor.setLayout(panelConfiguracionServidorLayout);
        panelConfiguracionServidorLayout.setHorizontalGroup(
            panelConfiguracionServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfiguracionServidorLayout.createSequentialGroup()
                .addContainerGap(168, Short.MAX_VALUE)
                .addComponent(contenedorServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(190, 190, 190))
        );
        panelConfiguracionServidorLayout.setVerticalGroup(
            panelConfiguracionServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionServidorLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(contenedorServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        tabbedSettings.addTab(settings_es_ES.getString("ConfiguracionServidor"), panelConfiguracionServidor);

        contenedorMailProps.setBorder(javax.swing.BorderFactory.createTitledBorder(null, settings_es_ES.getString("PropiedadesMail"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11), new java.awt.Color(255, 51, 51))); // NOI18N

        lblHost.setText(settings_es_ES.getString("HOST")); // NOI18N

        lblTLS.setText(settings_es_ES.getString("TLS HABILITADO")); // NOI18N

        lblPuerto.setText(settings_es_ES.getString("PUERTO")); // NOI18N

        txtHost.setEnabled(false);

        txtTLS.setEnabled(false);

        txtPuerto.setEnabled(false);

        lblUsuario.setText(settings_es_ES.getString("USUARIO")); // NOI18N

        lblAutenticacion.setText(settings_es_ES.getString("AUTENTICACION REQUERIDA")); // NOI18N

        txtUsuario.setEnabled(false);

        txtAutenticacion.setEnabled(false);

        javax.swing.GroupLayout contenedorMailPropsLayout = new javax.swing.GroupLayout(contenedorMailProps);
        contenedorMailProps.setLayout(contenedorMailPropsLayout);
        contenedorMailPropsLayout.setHorizontalGroup(
            contenedorMailPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorMailPropsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorMailPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHost)
                    .addComponent(lblTLS)
                    .addComponent(lblPuerto))
                .addGap(74, 74, 74)
                .addGroup(contenedorMailPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtHost)
                    .addComponent(txtTLS)
                    .addComponent(txtPuerto, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                .addGap(144, 144, 144)
                .addGroup(contenedorMailPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(lblAutenticacion))
                .addGap(40, 40, 40)
                .addGroup(contenedorMailPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAutenticacion, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        contenedorMailPropsLayout.setVerticalGroup(
            contenedorMailPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorMailPropsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorMailPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHost)
                    .addComponent(txtHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsuario)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(contenedorMailPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTLS)
                    .addComponent(txtTLS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(contenedorMailPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPuerto)
                    .addComponent(txtPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAutenticacion)
                    .addComponent(txtAutenticacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, settings_es_ES.getString("ContenidoMail"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11), new java.awt.Color(255, 51, 51))); // NOI18N

        lblFrom.setText(settings_es_ES.getString("DE")); // NOI18N

        lblRecipient.setText(settings_es_ES.getString("PARA")); // NOI18N

        lblSubject.setText(settings_es_ES.getString("ASUNTO")); // NOI18N

        lblProtocolo.setText(settings_es_ES.getString("PROTOCOLO")); // NOI18N

        lblUsuarioMail.setText(settings_es_ES.getString("USUARIO")); // NOI18N

        lblContraseniaMail.setText(settings_es_ES.getString("CONTRASEÑA")); // NOI18N

        txtRecipient.setEnabled(false);

        txtProtocolo.setEnabled(false);

        lblTextoContenido.setText(settings_es_ES.getString("CONTENIDO DEL MAIL")); // NOI18N

        txtAreaCuerpo.setColumns(20);
        txtAreaCuerpo.setRows(5);
        jScrollPane1.setViewportView(txtAreaCuerpo);

        btnGuardarMail.setText(settings_es_ES.getString("GUARDAR CONFIGURACION")); // NOI18N
        btnGuardarMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMailActionPerformed(evt);
            }
        });

        checkCMail.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkCMailStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFrom)
                    .addComponent(lblRecipient)
                    .addComponent(lblSubject)
                    .addComponent(lblProtocolo)
                    .addComponent(lblUsuarioMail)
                    .addComponent(lblContraseniaMail))
                .addGap(155, 155, 155)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFrom)
                    .addComponent(txtRecipient)
                    .addComponent(txtSubject)
                    .addComponent(txtProtocolo)
                    .addComponent(txtUsuarioMail)
                    .addComponent(txtContraseniaMail, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkCMail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblTextoContenido)
                                .addGap(164, 164, 164))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnGuardarMail)
                                .addGap(130, 130, 130))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFrom)
                    .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTextoContenido))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRecipient)
                            .addComponent(txtRecipient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSubject)
                            .addComponent(txtSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProtocolo)
                            .addComponent(txtProtocolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuarioMail)
                            .addComponent(txtUsuarioMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblContraseniaMail)
                        .addComponent(txtContraseniaMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkCMail))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarMail)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout contenedorMailLayout = new javax.swing.GroupLayout(contenedorMail);
        contenedorMail.setLayout(contenedorMailLayout);
        contenedorMailLayout.setHorizontalGroup(
            contenedorMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedorMailProps, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contenedorMailLayout.setVerticalGroup(
            contenedorMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorMailLayout.createSequentialGroup()
                .addComponent(contenedorMailProps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelMailLayout = new javax.swing.GroupLayout(panelMail);
        panelMail.setLayout(panelMailLayout);
        panelMailLayout.setHorizontalGroup(
            panelMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedorMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelMailLayout.setVerticalGroup(
            panelMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedorMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabbedSettings.addTab(settings_es_ES.getString("ConfiguracionMail"), panelMail);

        contenedorCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, settings_es_ES.getString("Cliente"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11), new java.awt.Color(255, 51, 51))); // NOI18N

        lblNombreCliente.setText(settings_es_ES.getString("NOMBRE DEL CLIENTE")); // NOI18N

        lblApellidoCliente.setText(settings_es_ES.getString("APELLIDO DEL CLIENTE")); // NOI18N

        lblOrganizacion.setText(settings_es_ES.getString("ORGANIZACION")); // NOI18N

        lblLocale.setText(settings_es_ES.getString("IDIOMA DEL LA INTERFAZ")); // NOI18N

        lblDescarga.setText(settings_es_ES.getString("CARPETA DE DESCARGA")); // NOI18N

        lblPublico.setText(settings_es_ES.getString("CARPETA DE ALMACENAMIENTO DE CLAVE PUBLICA")); // NOI18N

        lblPrivado.setText(settings_es_ES.getString("CARPETA DE ALMACENAMIENTO DE CLAVE PRIVADA")); // NOI18N

        comboLocale.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Español", "Ingles" }));

        txtDescarga.setEnabled(false);

        btnDescarga.setText("...");
        btnDescarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargaActionPerformed(evt);
            }
        });

        txtPublica.setEnabled(false);

        txtPrivada.setEnabled(false);

        btnKeys.setText("...");
        btnKeys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeysActionPerformed(evt);
            }
        });

        btnGuardarCliente.setText(settings_es_ES.getString("GUARDAR CONFIGURACION")); // NOI18N
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contenedorClienteLayout = new javax.swing.GroupLayout(contenedorCliente);
        contenedorCliente.setLayout(contenedorClienteLayout);
        contenedorClienteLayout.setHorizontalGroup(
            contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombreCliente)
                    .addComponent(lblApellidoCliente)
                    .addComponent(lblOrganizacion)
                    .addComponent(lblLocale)
                    .addComponent(lblDescarga)
                    .addComponent(lblPublico)
                    .addComponent(lblPrivado))
                .addGap(164, 164, 164)
                .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarCliente)
                    .addGroup(contenedorClienteLayout.createSequentialGroup()
                        .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombreCliente)
                            .addComponent(txtApellidoCliente)
                            .addComponent(txtOrganizacion)
                            .addComponent(comboLocale, 0, 286, Short.MAX_VALUE)
                            .addComponent(txtDescarga)
                            .addComponent(txtPublica)
                            .addComponent(txtPrivada))
                        .addGap(18, 18, 18)
                        .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDescarga)
                            .addComponent(btnKeys))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contenedorClienteLayout.setVerticalGroup(
            contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorClienteLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreCliente)
                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApellidoCliente)
                    .addComponent(txtApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrganizacion)
                    .addComponent(txtOrganizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLocale)
                    .addComponent(comboLocale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescarga)
                    .addComponent(txtDescarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDescarga))
                .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenedorClienteLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPublico)
                            .addComponent(txtPublica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(contenedorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrivado)
                            .addComponent(txtPrivada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(contenedorClienteLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btnKeys)))
                .addGap(48, 48, 48)
                .addComponent(btnGuardarCliente)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelConfiguracionClienteLayout = new javax.swing.GroupLayout(panelConfiguracionCliente);
        panelConfiguracionCliente.setLayout(panelConfiguracionClienteLayout);
        panelConfiguracionClienteLayout.setHorizontalGroup(
            panelConfiguracionClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfiguracionClienteLayout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addComponent(contenedorCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        panelConfiguracionClienteLayout.setVerticalGroup(
            panelConfiguracionClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedSettings.addTab(settings_es_ES.getString("ConfiguracionCliente"), panelConfiguracionCliente);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedSettings)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedSettings)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarServidorActionPerformed
        String text1 = this.txtNombreServidor.getText();
        String text2 = this.txtNombreUsuario.getText();
        char[] password = this.txtPasswordServidor.getPassword();

        String conc = new String(password);
        if (!Validator.isPassword(conc)) {
            String err = settings_es_ES.getString("NO ES UNA CONTRASEÑA VALIDA");
            String wrr2 = settings_es_ES.getString("CONSULTE LA AYUDA PARA MAS INFORMACION");
            String jkd = settings_es_ES.getString("CONTRASEÑA NO VALIDA");

            JOptionPane.showMessageDialog(this, err + "\n" + wrr2, jkd, JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (!(Validator.isTextEmpty(text1) || Validator.isTextEmpty(text2))) {
                if (password != null && password.length != 0 && Validator.isPassword(conc)) {
                    String pass = new String(password);

                    Loader d = LoaderProvider.getInstance();
                    ServerConfig co = new ServerConfig(text1);
                    co.setUserLogin(text2);
                    co.setPassLogin(pass);

                    d.setServerConfiguration(co);

                    String pp = settings_es_ES.getString("CONFIGURACION GUARDADA.");
                    String tt = settings_es_ES.getString("LOS CAMBIOS SURTIRAN EFECTO LA PROXIMA VEZ QUE INICIE EL PROGRAMA");
                    String gf = settings_es_ES.getString("EXITO");

                    JOptionPane.showMessageDialog(this, pp + "\n" + tt, gf, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String err = settings_es_ES.getString("NO ES UNA CONTRASEÑA VALIDA");
                    String wrr2 = settings_es_ES.getString("CONSULTE LA AYUDA PARA MAS INFORMACION");
                    String jkd = settings_es_ES.getString("CONTRASEÑA NO VALIDA");

                    JOptionPane.showMessageDialog(this, err + "\n" + wrr2, jkd, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                String err = settings_es_ES.getString("NO ES UNA CONTRASEÑA VALIDA");
                String wrr2 = settings_es_ES.getString("CONSULTE LA AYUDA PARA MAS INFORMACION");
                String jkd = settings_es_ES.getString("CONTRASEÑA NO VALIDA");

                JOptionPane.showMessageDialog(this, err + "\n" + wrr2, jkd, JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException | URISyntaxException | HeadlessException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarServidorActionPerformed

    private boolean isGmail(String mail) {
        String toCompare = mail.substring(mail.lastIndexOf("@") + 1, mail.length());

        return (toCompare.equals("gmail.com")) ? true : false;
    }
    private void btnGuardarMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMailActionPerformed

        String conc = new String(this.txtContraseniaMail.getPassword());
        if (!Validator.isPassword(conc)) {
            String err = settings_es_ES.getString("NO ES UNA CONTRASEÑA VALIDA");
            String wrr2 = settings_es_ES.getString("CONSULTE LA AYUDA PARA MAS INFORMACION");
            String jkd = settings_es_ES.getString("CONTRASEÑA NO VALIDA");

            JOptionPane.showMessageDialog(this, err + "\n" + wrr2, jkd, JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String mailUserName = this.txtUsuarioMail.getText();
            if (mailUserName != null && Validator.isMail(mailUserName) && isGmail(mailUserName)) {

                MailBuilder builder = new MailBuilder();
                m.getProperties().setProperty("mail.smtp.user", mailUserName);
                builder.buildProperties(m.getProperties());
                builder.buildFrom(this.txtFrom.getText());
                builder.buildRecipient(mailUserName);
                builder.buildSubject(this.txtSubject.getText());
                builder.buildSendProtocol(this.txtProtocolo.getText());
                builder.buildMailText(this.txtAreaCuerpo.getText());
                builder.buildMailUserName(this.txtUsuarioMail.getText());
                builder.buildMailUserPassword(new String(this.txtContraseniaMail.getPassword()));

                Mail mail = builder.getMail();

                Loader l = LoaderProvider.getInstance();
                l.setMailProperties(mailUserName);
                l.setMailContent(mail.getContent());

                String pp = settings_es_ES.getString("CONFIGURACION GUARDADA.");
                String tt = settings_es_ES.getString("LOS CAMBIOS SURTIRAN EFECTO LA PROXIMA VEZ QUE INICIE EL PROGRAMA");
                String gf = settings_es_ES.getString("EXITO");

                JOptionPane.showMessageDialog(this, pp + "\n" + tt, gf, JOptionPane.INFORMATION_MESSAGE);
            } else {
                String l = settings_es_ES.getString("NO ES UN MAIL VALIDO");
                String h = settings_es_ES.getString("MAIL NO VALIDO. SOLO COMPATIBLE CON GMAIL.");

                JOptionPane.showMessageDialog(this, l, h, JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnGuardarMailActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        try {

            ClientBuilder builder = new ClientBuilder();
            builder.buildClientName(this.txtNombreCliente.getText());
            builder.buildClientLastName(this.txtApellidoCliente.getText());
            builder.buildOrganization(this.txtOrganizacion.getText());
            builder.buildLocale((String) this.comboLocale.getSelectedItem());
            builder.buildDownloadPath(this.txtDescarga.getText());
            builder.buildPrivateStorage(this.txtPrivada.getText());
            builder.buildPublicStorage(this.txtPublica.getText());

            Client co = builder.getCLient();

            Loader l = LoaderProvider.getInstance();
            l.setClientConfiguration(co);

            String pp = settings_es_ES.getString("CONFIGURACION GUARDADA.");
            String tt = settings_es_ES.getString("LOS CAMBIOS SURTIRAN EFECTO LA PROXIMA VEZ QUE INICIE EL PROGRAMA");
            String gf = settings_es_ES.getString("EXITO");

            JOptionPane.showMessageDialog(this, pp + "\n" + tt, gf, JOptionPane.INFORMATION_MESSAGE);

        } catch (InformationRequiredException | ParserConfigurationException | SAXException | IOException | TransformerException | URISyntaxException | HeadlessException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnDescargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargaActionPerformed
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFileChooser jfc = new JFileChooser();

            jfc.setApproveButtonText(settings_es_ES.getString("SELECCIONAR"));
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setMultiSelectionEnabled(false);
            int response = jfc.showOpenDialog(this);

            if (response == JFileChooser.APPROVE_OPTION) {
                File selectedFiles = jfc.getSelectedFile();
                this.txtDescarga.setText(selectedFiles.getAbsolutePath() + "\\");
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | HeadlessException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDescargaActionPerformed

    private void btnKeysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeysActionPerformed
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFileChooser jfc = new JFileChooser();

            jfc.setApproveButtonText(settings_es_ES.getString("SELECCIONAR"));
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setMultiSelectionEnabled(false);
            int response = jfc.showOpenDialog(this);

            if (response == JFileChooser.APPROVE_OPTION) {
                File selectedFiles = jfc.getSelectedFile();
                this.txtPublica.setText(selectedFiles.getAbsolutePath() + "\\");
                this.txtPrivada.setText(selectedFiles.getAbsolutePath() + "\\");
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | HeadlessException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnKeysActionPerformed

    private void checkCMailStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkCMailStateChanged
        if (this.checkCMail.isSelected()) {
            this.txtContraseniaMail.setEnabled(true);
        } else {
            this.txtContraseniaMail.setEnabled(false);
        }
    }//GEN-LAST:event_checkCMailStateChanged

    private void checkCServidorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkCServidorStateChanged
        if (this.checkCServidor.isSelected()) {
            this.txtPasswordServidor.setEnabled(true);
        } else {
            this.txtPasswordServidor.setEnabled(false);
        }
    }//GEN-LAST:event_checkCServidorStateChanged

    private void btnConexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConexionActionPerformed
        try {
            String text1 = this.txtNombreServidor.getText();
            String text2 = this.txtNombreUsuario.getText();
            char[] password = this.txtPasswordServidor.getPassword();

            String conc = new String(password);
            if (!Validator.isPassword(conc)) {
                String err = settings_es_ES.getString("NO ES UNA CONTRASEÑA VALIDA");
                String wrr2 = settings_es_ES.getString("CONSULTE LA AYUDA PARA MAS INFORMACION");
                String jkd = settings_es_ES.getString("CONTRASEÑA NO VALIDA");

                JOptionPane.showMessageDialog(this, err + "\n" + wrr2, jkd, JOptionPane.ERROR_MESSAGE);
                return;
            }

            ServerConfig co = new ServerConfig(text1);
            co.setUserLogin(text2);
            co.setPassLogin(conc);
            RouterRetrieve r = new RouterRetrieve(co);
            r.testConnection();

            String l = settings_es_ES.getString("CONEXION A SERVIDOR FTP EXITOSA");
            String p = settings_es_ES.getString("PRUEBA DE CONEXION");
            JOptionPane.showMessageDialog(this, l, p, JOptionPane.INFORMATION_MESSAGE);

        } catch (SocketException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FTPConectionRefusedException ex) {
            String q = settings_es_ES.getString("PRUEBA DE CONEXION FALLIDA");
            JOptionPane.showMessageDialog(this, ex.getMessage(), q, JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnConexionActionPerformed
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Settings().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConexion;
    private javax.swing.JButton btnDescarga;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarMail;
    private javax.swing.JButton btnGuardarServidor;
    private javax.swing.JButton btnKeys;
    private javax.swing.JCheckBox checkCMail;
    private javax.swing.JCheckBox checkCServidor;
    private javax.swing.JComboBox comboLocale;
    private javax.swing.JPanel contenedorCliente;
    private javax.swing.JPanel contenedorMail;
    private javax.swing.JPanel contenedorMailProps;
    private javax.swing.JPanel contenedorServidor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApellidoCliente;
    private javax.swing.JLabel lblAutenticacion;
    private javax.swing.JLabel lblContraseniaMail;
    private javax.swing.JLabel lblContraseniaUsuario;
    private javax.swing.JLabel lblDescarga;
    private javax.swing.JLabel lblFrom;
    private javax.swing.JLabel lblHost;
    private javax.swing.JLabel lblIPServidor;
    private javax.swing.JLabel lblLocale;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblNombreServidor;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblOrganizacion;
    private javax.swing.JLabel lblPrivado;
    private javax.swing.JLabel lblProtocolo;
    private javax.swing.JLabel lblPublico;
    private javax.swing.JLabel lblPuerto;
    private javax.swing.JLabel lblRecipient;
    private javax.swing.JLabel lblSubject;
    private javax.swing.JLabel lblTLS;
    private javax.swing.JLabel lblTextoContenido;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblUsuarioMail;
    private javax.swing.JPanel panelConfiguracionCliente;
    private javax.swing.JPanel panelConfiguracionServidor;
    private javax.swing.JPanel panelMail;
    private javax.swing.JTabbedPane tabbedSettings;
    private javax.swing.JTextField txtApellidoCliente;
    private javax.swing.JTextArea txtAreaCuerpo;
    private javax.swing.JTextField txtAutenticacion;
    private javax.swing.JPasswordField txtContraseniaMail;
    private javax.swing.JTextField txtDescarga;
    private javax.swing.JTextField txtFrom;
    private javax.swing.JTextField txtHost;
    private javax.swing.JTextField txtIPServidor;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreServidor;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JTextField txtOrganizacion;
    private javax.swing.JPasswordField txtPasswordServidor;
    private javax.swing.JTextField txtPrivada;
    private javax.swing.JTextField txtProtocolo;
    private javax.swing.JTextField txtPublica;
    private javax.swing.JTextField txtPuerto;
    private javax.swing.JTextField txtRecipient;
    private javax.swing.JTextField txtSubject;
    private javax.swing.JTextField txtTLS;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtUsuarioMail;
    // End of variables declaration//GEN-END:variables
}
