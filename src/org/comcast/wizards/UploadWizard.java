package org.comcast.wizards;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.comcast.builder.Client;
import org.comcast.builder.Mail;
import org.comcast.crypto.CryptoData;
import org.comcast.exceptions.InformationRequiredException;
import org.comcast.logic.DateScheduler;
import org.comcast.logic.ServerConfig;
import org.comcast.proxy.InterfaceWorks;
import org.comcast.proxy.UploadHandler;
import org.comcast.proxy.Works;
import org.comcast.router.Message;
import org.comcast.structures.LocalIterator;
import org.comcast.structures.SimpleList;
import org.comcast.tableModels.LocalWizardTableModel;
import org.comcast.xml.Loader;
import org.comcast.xml.LoaderProvider;
import org.netbeans.api.wizard.WizardDisplayer;
import org.netbeans.spi.wizard.DeferredWizardResult;
import org.netbeans.spi.wizard.ResultProgressHandle;
import org.netbeans.spi.wizard.Summary;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardController;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPanelProvider;
import org.xml.sax.SAXException;

/**
 * Clase que representa al asistente de configuracion y subida de archivos al
 * servidor ftp.
 *
 * @author Damian Bruera.
 * @version 4.2
 * @since Java 7
 */
public class UploadWizard {

    private ResourceBundle uploadWizard_es_ES;

    public UploadWizard() {
        try {
            Client c = LoaderProvider.getInstance().getClientConfiguration();

            switch (c.getLocalization()) {
                case "Español":
                    uploadWizard_es_ES = ResourceBundle.getBundle("org/comcast/locale/UploadWizard_es_ES");
                    break;
                case "Ingles":
                    uploadWizard_es_ES = ResourceBundle.getBundle("org/comcast/locale/UploadWizard_en_US");
                    break;
                default:
                    uploadWizard_es_ES = ResourceBundle.getBundle("org/comcast/locale/UploadWizard_en_US");
                    break;
            }

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException | URISyntaxException | InformationRequiredException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void main(Map args) {
        final Map properties = args;
        Runnable r;
        r = new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                    String title = uploadWizard_es_ES.getString("ASISTENTE SUBIR ARCHIVOS");
                    String[] subtitles = new String[]{uploadWizard_es_ES.getString("BIENVENIDO"), uploadWizard_es_ES.getString("VERIFICAR SERVIDOR"),
                        uploadWizard_es_ES.getString("CONFIGURACION CLIENTE"), uploadWizard_es_ES.getString("VERIFICAR MAIL"),
                        uploadWizard_es_ES.getString("ARCHIVOS A SUBIR"), uploadWizard_es_ES.getString("DESTINO REMOTO")};

                    WizardProvider mp = new WizardProvider(title, subtitles);
                    Wizard wizard = mp.createWizard();
                    Object result;
                    result = WizardDisplayer.showWizard(wizard,
                            new Rectangle(100, 100,
                            1000, 600), null, properties);
                    System.out.println("Result = " + result);
                    //             System.exit (0);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(UploadWizard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        EventQueue.invokeLater(r);
    }
}

class WizardProvider extends WizardPanelProvider {

    private ResourceBundle uploadWizard_es_ES;

    WizardProvider(String title, String[] subtitles) {

        super(title,
                new String[]{"bienvenido", "servidor", "cliente", "mail", "archivosSubir", "destino"},
                subtitles);


        try {
            Client c = LoaderProvider.getInstance().getClientConfiguration();

            switch (c.getLocalization()) {
                case "Español":
                    uploadWizard_es_ES = ResourceBundle.getBundle("org/comcast/locale/UploadWizard_es_ES");
                    break;
                case "Ingles":
                    uploadWizard_es_ES = ResourceBundle.getBundle("org/comcast/locale/UploadWizard_en_US");
                    break;
                default:
                    uploadWizard_es_ES = ResourceBundle.getBundle("org/comcast/locale/UploadWizard_en_US");
                    break;
            }

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException | URISyntaxException | InformationRequiredException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private CryptoData stringParts(Message full, long serialID) throws Exception {
        Client client = LoaderProvider.getInstance().getClientConfiguration();
        String partida = full.getLocalPath();
        System.out.println(partida);
        String general = "key" + serialID;

        String aux = partida.substring(partida.lastIndexOf("\\") + 1, partida.lastIndexOf("."));
        String particion = partida.substring(0, partida.lastIndexOf("\\") + 1);
        String ex = partida.substring(partida.lastIndexOf(".") + 1, partida.length());

        String pn = general + ".public";
        String pv = general + ".private";
        String cryp = aux + ".ftp";

        String publicName = client.getPublicStorage() + pn;
        String privateName = client.getPrivateStorage() + pv;
        String cryptoFile = particion + cryp;

        String[] args = new String[8];
        args[0] = aux; //FILE NAME
        args[1] = partida; //ORIGINAL
        args[2] = cryptoFile; //CRYPTOFILE
        args[3] = publicName; //PUBLIC KEY
        args[4] = privateName; //PRIVATE KEY
        args[5] = ex; //EXTENSION
        args[6] = full.getRemotePath() + cryp; //DESTINATION

        CryptoData data = new CryptoData(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);

        return data;
    }

    private boolean isLoadable(String remoteDestination) throws Exception {

        CryptoData data = LoaderProvider.getInstance().getCryptoData(remoteDestination);

        return (data != null) ? false : true;
    }

    @Override
    protected JComponent createPanel(final WizardController wc, String string, final Map map) {

        final Loader l = LoaderProvider.getInstance();

        switch (string) {
            case "bienvenido":

                JLabel lbl = new JLabel();
                JLabel lbl1 = new JLabel();
                JLabel lbl2 = new JLabel();
                lbl.setText(uploadWizard_es_ES.getString("BIENVENIDO AL ASISTENTE PARA SUBIR ARCHIVOS."));
                lbl1.setText(uploadWizard_es_ES.getString("ESTE ASISTENTE LO GUIARÁ DURANTE EL PROCESO DE SUBIR ARCHIVOS A UN SERVIDOR FTP"));
                lbl2.setText(uploadWizard_es_ES.getString("PRESIONE NEXT PARA EMPEZAR"));

                JPanel pn1 = new JPanel();
                pn1.add(lbl);

                JPanel pn2 = new JPanel();
                pn2.add(lbl2);

                JPanel pn3 = new JPanel();
                pn3.add(lbl1);

                JPanel bienvenido = new JPanel();
                bienvenido.setLayout(new GridLayout(3, 1));
                bienvenido.add(pn1);
                bienvenido.add(pn3);
                bienvenido.add(pn2);
                return bienvenido;

            case "servidor":
                final JCheckBox chServer = new JCheckBox();
                final JCheckBox chUser = new JCheckBox();
                final JCheckBox chIP = new JCheckBox();
                final JPanel servidor = new JPanel();

                try {
                    wc.setProblem(uploadWizard_es_ES.getString("DEBE VERIFICAR LOS DATOS"));

                    ServerConfig config = l.getServerConfiguration();
                    JLabel server = new JLabel(uploadWizard_es_ES.getString("NOMBRE SERVIDOR"));
                    JLabel user = new JLabel(uploadWizard_es_ES.getString("NOMBRE USUARIO"));
                    JLabel ip = new JLabel(uploadWizard_es_ES.getString("NUMERO IP"));

                    JTextField txtServer = new JTextField(40);
                    txtServer.setEnabled(false);
                    txtServer.setText(config.getHostName());

                    JTextField txtUser = new JTextField(40);
                    txtUser.setEnabled(false);
                    txtUser.setText(config.getUserLogin());

                    JTextField txtIP = new JTextField(40);
                    txtIP.setEnabled(false);
                    txtIP.setText(config.getIpAddress());

                    JButton validar = new JButton(uploadWizard_es_ES.getString("VALIDAR"));
                    validar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (chServer.isSelected() && chUser.isSelected() && chIP.isSelected()) {
                                wc.setProblem(null);
                            } else {
                                wc.setProblem(uploadWizard_es_ES.getString("DEBE VERIFICAR LOS DATOS"));
                            }
                        }
                    });

                    JPanel one = new JPanel();
                    one.add(server);
                    one.add(txtServer);
                    one.add(chServer);

                    JPanel two = new JPanel();
                    two.add(user);
                    two.add(txtUser);
                    two.add(chUser);

                    JPanel three = new JPanel();
                    three.add(ip);
                    three.add(txtIP);
                    three.add(chIP);

                    JPanel four = new JPanel();
                    four.add(validar);

                    servidor.setLayout(new GridLayout(4, 1));
                    servidor.add(one);
                    servidor.add(two);
                    servidor.add(three);
                    servidor.add(four);

                    return servidor;

                } catch (Exception ex) {
                    String a = (uploadWizard_es_ES.getString("SE HA PRODUCIDO UN ERROR "));
                    wc.setProblem(a + ex.toString());
                    chUser.setEnabled(false);
                    chServer.setEnabled(false);
                    chIP.setEnabled(false);
                    return servidor;
                }

            case "cliente":

                final JCheckBox chLocale = new JCheckBox();
                final JCheckBox chPublic = new JCheckBox();
                final JCheckBox chPrivate = new JCheckBox();
                final JPanel cliente = new JPanel();

                try {
                    wc.setProblem(uploadWizard_es_ES.getString("DEBE VERIFICAR LOS DATOS"));

                    Client c = l.getClientConfiguration();

                    JLabel locale = new JLabel(uploadWizard_es_ES.getString("IDIOMA"));
                    JLabel publicStorage = new JLabel(uploadWizard_es_ES.getString("DIRECTORIO DE LLAVE PUBLICA"));
                    JLabel privateStorage = new JLabel(uploadWizard_es_ES.getString("DIRECTORIO DE LLAVE PRIVADA"));

                    JTextField txtLocale = new JTextField(40);
                    txtLocale.setEnabled(false);
                    txtLocale.setText(c.getLocalization());

                    JTextField txtPublic = new JTextField(40);
                    txtPublic.setEnabled(false);
                    txtPublic.setText(c.getPublicStorage());

                    JTextField txtPrivate = new JTextField(40);
                    txtPrivate.setEnabled(false);
                    txtPrivate.setText(c.getPrivateStorage());

                    JButton validar = new JButton(uploadWizard_es_ES.getString("VALIDAR"));
                    validar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (chLocale.isSelected() && chPublic.isSelected() && chPrivate.isSelected()) {
                                wc.setProblem(null);
                            } else {
                                wc.setProblem(uploadWizard_es_ES.getString("DEBE VERIFICAR LOS DATOS"));
                            }
                        }
                    });
                    JPanel one = new JPanel();
                    one.add(locale);
                    one.add(txtLocale);
                    one.add(chLocale);

                    JPanel two = new JPanel();
                    two.add(publicStorage);
                    two.add(txtPublic);
                    two.add(chPublic);

                    JPanel three = new JPanel();
                    three.add(privateStorage);
                    three.add(txtPrivate);
                    three.add(chPrivate);

                    JPanel four = new JPanel();
                    four.add(validar);

                    cliente.setLayout(new GridLayout(4, 1));
                    cliente.add(one);
                    cliente.add(two);
                    cliente.add(three);
                    cliente.add(four);

                    return cliente;

                } catch (Exception ex) {
                    String a = (uploadWizard_es_ES.getString("SE HA PRODUCIDO UN ERROR "));
                    wc.setProblem(a + ex.toString());
                    chLocale.setEnabled(false);
                    chPrivate.setEnabled(false);
                    chPublic.setEnabled(false);
                    return cliente;
                }

            case "mail":
                final JCheckBox chHost = new JCheckBox();
                final JCheckBox chTLS = new JCheckBox();
                final JCheckBox chPort = new JCheckBox();
                final JCheckBox chAuthentication = new JCheckBox();
                final JPanel mail = new JPanel();
                try {
                    wc.setProblem(uploadWizard_es_ES.getString("DEBE VERIFICAR LOS DATOS"));

                    Mail mailC = l.getMail();

                    JLabel host = new JLabel(uploadWizard_es_ES.getString("SMTP HOST"));
                    JLabel tls = new JLabel(uploadWizard_es_ES.getString("USO DE TLS"));
                    JLabel port = new JLabel(uploadWizard_es_ES.getString("PUERTO DE SERVICIO SMNP"));
                    JLabel autenti = new JLabel(uploadWizard_es_ES.getString("USUARIO"));

                    JTextField txtHost = new JTextField(40);
                    txtHost.setEnabled(false);
                    txtHost.setText(mailC.getProperties().getProperty("mail.smtp.host"));

                    JTextField txtTLS = new JTextField(40);
                    txtTLS.setEnabled(false);
                    txtTLS.setText(mailC.getProperties().getProperty("mail.smtp.starttls.enable"));

                    JTextField txtPort = new JTextField(40);
                    txtPort.setEnabled(false);
                    txtPort.setText(mailC.getProperties().getProperty("mail.smtp.port"));

                    JTextField txtAut = new JTextField(40);
                    txtAut.setEnabled(false);
                    txtAut.setText(mailC.getMailUserName());

                    JButton validar = new JButton(uploadWizard_es_ES.getString("VALIDAR"));
                    validar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (chHost.isSelected() && chTLS.isSelected() && chPort.isSelected() && chAuthentication.isSelected()) {
                                wc.setProblem(null);
                            } else {
                                wc.setProblem(uploadWizard_es_ES.getString("DEBE VERIFICAR LOS DATOS"));
                            }
                        }
                    });
                    JPanel one = new JPanel();
                    one.add(host);
                    one.add(txtHost);
                    one.add(chHost);

                    JPanel two = new JPanel();
                    two.add(tls);
                    two.add(txtTLS);
                    two.add(chTLS);

                    JPanel three = new JPanel();
                    three.add(port);
                    three.add(txtPort);
                    three.add(chPort);

                    JPanel four = new JPanel();
                    four.add(autenti);
                    four.add(txtAut);
                    four.add(chAuthentication);

                    JPanel five = new JPanel();
                    five.add(validar);


                    mail.setLayout(new GridLayout(5, 1));
                    mail.add(one);
                    mail.add(two);
                    mail.add(three);
                    mail.add(four);
                    mail.add(five);

                    return mail;

                } catch (Exception ex) {
                    String a = (uploadWizard_es_ES.getString("SE HA PRODUCIDO UN ERROR "));
                    wc.setProblem(a + ex.toString());
                    chHost.setEnabled(false);
                    chTLS.setEnabled(false);
                    chPort.setEnabled(false);
                    chAuthentication.setEnabled(false);
                    return mail;
                }

            case "archivosSubir":
                JPanel archivosSubir = new JPanel();
                archivosSubir.setLayout(new GridLayout(4, 1));
                final JTable area = new JTable();
                JScrollPane kk = new JScrollPane(area);
                StringBuilder builder = new StringBuilder();
                try {
//                    wc.setProblem("Debe Verificar los datos");
                    final Message[] selectedItems = (Message[]) map.get("selectedFiles");

                    if (selectedItems != null) {
                        for (int i = 0; i < selectedItems.length; i++) {
                            Message aux = selectedItems[i];
                            CryptoData cd = stringParts(aux, System.nanoTime());

                            if (!isLoadable(cd.getDestination())) {
                                String a = uploadWizard_es_ES.getString("EL ARCHIVO ");
                                String b = uploadWizard_es_ES.getString(" YA SE ENCUENTRA REGISTRADO. ELIMINALO DE LA SELECCION.");
                                wc.setProblem(a + aux.getLocalFile().getName() + b);
                                break;
                            }
                        }

                        area.setModel(new LocalWizardTableModel(selectedItems));
                        alineacion(area);
                    } else {
                        builder.append(uploadWizard_es_ES.getString("NO HAY ARCHIVOS SELECCIONADOS"));
                        wc.setProblem(uploadWizard_es_ES.getString("NO HAY ARCHIVOS SELECCIONADOS"));
                    }

                    JLabel p = new JLabel(uploadWizard_es_ES.getString("ASIGNAR PRIORIDADES A LOS ARCHIVOS"));

                    final JComboBox archivos = getArchivos(selectedItems);
                    final JComboBox prioridades = getPrioridades();

                    JButton añadir = new JButton(uploadWizard_es_ES.getString("AÑADIR"));
                    añadir.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                Integer sel = (Integer) archivos.getSelectedItem();
                                int prio = prioridades.getSelectedIndex();

                                selectedItems[sel - 1].setPriority(prio + 1);

                                String z = uploadWizard_es_ES.getString("ARCHIVO: ");
                                String y = uploadWizard_es_ES.getString("CAMBIO DE PRIORIDAD A ");
                                String x = uploadWizard_es_ES.getString("CAMBIOS DE PRIORIDAD");

                                JOptionPane.showMessageDialog(null,
                                        z + selectedItems[sel - 1].getLocalPath() + "\n" + y + "\n" + prioridades.getSelectedItem(),
                                        x, JOptionPane.INFORMATION_MESSAGE);

                                area.setModel(new LocalWizardTableModel(selectedItems));
                                alineacion(area);

                            } catch (Exception ex) {
                                String a = (uploadWizard_es_ES.getString("PROBLEMA AL CARGAR LOS ARCHIVOS: "));
                                wc.setProblem(a + ex.toString());
                            }
                        }
                    });
                    wc.setProblem(uploadWizard_es_ES.getString("NO HA FINALIZADO LAS MODIFICACIONES"));

                    JButton finalizar = new JButton(uploadWizard_es_ES.getString("FINALIZAR MODIFICACIONES"));
                    finalizar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            map.put("newItems", selectedItems);
                            wc.setProblem(null);
                        }
                    });

                    JPanel pin = new JPanel();
                    pin.add(finalizar);

                    JPanel panel = new JPanel();
                    panel.add(archivos);
                    panel.add(prioridades);
                    panel.add(añadir);

                    JPanel one = new JPanel();
                    javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(one);
                    one.setLayout(jPanel9Layout);
                    jPanel9Layout.setHorizontalGroup(
                            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kk, javax.swing.GroupLayout.Alignment.TRAILING));
                    jPanel9Layout.setVerticalGroup(
                            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kk, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE));

                    archivosSubir.add(one);
                    archivosSubir.add(p);
                    archivosSubir.add(panel);
                    archivosSubir.add(finalizar);

                    return archivosSubir;

                } catch (Exception ex) {
                    String a = (uploadWizard_es_ES.getString("PROBLEMA AL CARGAR LOS ARCHIVOS: "));
                    wc.setProblem(a + ex.toString());
                    return archivosSubir;
                }

            case "destino":
                wc.setProblem(uploadWizard_es_ES.getString("RUTA REMOTA NO VALIDA O FECHA NO VALIDA"));

                JLabel desc = new JLabel(uploadWizard_es_ES.getString("UBICACION REMOTA ELEGIDA: "));
                final JLabel selec = new JLabel();

                final JButton buildScheduler = new JButton(uploadWizard_es_ES.getString("ARMAR HORARIO"));
                buildScheduler.setEnabled(false);

                JButton ingresar = new JButton("....");
                ingresar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String get = (String) map.get("destination");

                        if (get == null || get.equalsIgnoreCase("")) {
                            String s = uploadWizard_es_ES.getString("DEBE SELECCIONAR UNA RUTA REMOTA VALIDA");
                            String g = uploadWizard_es_ES.getString("RUTA REMOTA");

                            JOptionPane.showMessageDialog(null, s, g, JOptionPane.WARNING_MESSAGE);

                            wc.setProblem(uploadWizard_es_ES.getString("DEBE SELECCIONAR UNA RUTA REMOTA VALIDA"));
                        } else {
                            selec.setText(get);
                            buildScheduler.setEnabled(true);
                        }
                    }
                });

                JLabel tarea = new JLabel("DD/MM/YYYY HH:MM:SS");
                JLabel titulo = new JLabel(uploadWizard_es_ES.getString("CONFIGURAR TAREA"));
                titulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

                final JComboBox dias = getDias();
                final JComboBox meses = getMeses();

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                final JTextField años = new JTextField(5);
                años.setText(String.valueOf(year));
                años.setEnabled(false);

                final JComboBox hora = getHoras();
                final JComboBox minutos = getMinutos();
                final JTextField segundos = new JTextField(3);
                segundos.setText("00");
                segundos.setEnabled(false);

                buildScheduler.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Integer dia = (Integer) dias.getSelectedItem();
                            Integer mes = (Integer) meses.getSelectedItem();
                            int año = Integer.parseInt(años.getText());

                            Integer hor = (Integer) hora.getSelectedItem();
                            Integer min = (Integer) minutos.getSelectedItem();
                            int seg = Integer.parseInt(segundos.getText());

                            DateScheduler date = new DateScheduler(hor, min, seg, dia, mes, año);
                            Date runTime = org.quartz.DateBuilder.dateOf(date.getHour(), date.getMinute(), date.getSecond(),
                                    date.getDay(), date.getMonth(), date.getYear());
                            long t = (runTime.getTime() - System.currentTimeMillis());

                            if (t < 0) {
                                wc.setProblem(uploadWizard_es_ES.getString("LA FECHA SELECCIONADA DEBE SER MAYOR A LA FECHA DEL SISTEMA"));
                            } else {
                                wc.setProblem(null);
                                map.put("dateScheduler", date);

                                String k = uploadWizard_es_ES.getString("LA TAREA FUE PROGRAMADA PARA EJECUTARSE EN:");
                                String h = uploadWizard_es_ES.getString("TAREA SUBIR ARCHIVO");
                                JOptionPane.showMessageDialog(null, k + "\n" + runTime, h, JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (Exception ex) {
                            wc.setProblem(uploadWizard_es_ES.getString("LA FECHA SELECCIONADA DEBE SER VALIDA"));
                        }
                    }
                });

                JPanel fecha = new JPanel();
                fecha.add(dias);
                fecha.add(meses);
                fecha.add(años);

                JPanel horario = new JPanel();
                horario.add(hora);
                horario.add(minutos);
                horario.add(segundos);

                JPanel one = new JPanel();
                one.add(desc);

                JPanel two = new JPanel();
                two.add(selec);

                JPanel three = new JPanel();
                three.add(ingresar);

                JPanel four = new JPanel();
                four.add(tarea);

                JPanel five = new JPanel();
                five.add(buildScheduler);

                JPanel intermedio1 = new JPanel();
                intermedio1.setLayout(new GridLayout(1, 3));
                intermedio1.add(one);
                intermedio1.add(two);
                intermedio1.add(three);

                JPanel intermedio2 = new JPanel();
                intermedio2.setLayout(new GridLayout(1, 4));
                intermedio2.add(four);
                intermedio2.add(fecha);
                intermedio2.add(horario);
                intermedio2.add(five);

                JPanel destino = new JPanel();
                destino.setLayout(new GridLayout(3, 1));
                destino.add(intermedio1);
                destino.add(new JPanel().add(titulo));
                destino.add(intermedio2);

                return destino;
        }

        return null;
    }

    private JComboBox getHoras() {
        Integer[] horas = new Integer[24];
        for (int i = 0; i < 24; i++) {
            horas[i] = i;
        }

        return new JComboBox(horas);
    }

    private JComboBox getMinutos() {
        Integer[] minutos = new Integer[60];
        for (int i = 0; i < 60; i++) {
            minutos[i] = i;
        }
        return new JComboBox(minutos);
    }

    private JComboBox getDias() {
        Integer[] dias = new Integer[31];
        for (int i = 1; i < 32; i++) {
            dias[i - 1] = i;
        }
        return new JComboBox(dias);
    }

    private JComboBox getMeses() {
        Integer[] dias = new Integer[12];
        for (int i = 1; i < 13; i++) {
            dias[i - 1] = i;
        }
        return new JComboBox(dias);
    }

    private JComboBox getArchivos(Message[] mess) {
        Integer[] dias = new Integer[mess.length];
        for (int i = 0; i < mess.length; i++) {
            dias[i] = i + 1;
        }
        return new JComboBox(dias);
    }

    private JComboBox getPrioridades() {
        String[] stgr = new String[]{"HIGH", "NORMAL", "LOW"};
        return new JComboBox(stgr);
    }

    private void alineacion(JTable table) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        int count = table.getColumnCount();

        for (int i = 0; i < count; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    @Override
    protected Object finish(Map settings) throws WizardException {
        System.out.println("finish called");

//      return "String finishes abs";
        return new Result();

//      String [] items = new String [2];
//      items [0] = "Name = "+settings.get ("name");
//      items [1] = "Status = "+settings.get ("status");
//      return Summary.create (items, null);
    }
}

class Result extends DeferredWizardResult {

    private ResourceBundle uploadWizard_es_ES;

    public Result() {
        // Uncomment the following line to make it possible to close the dialog
        // while the operation is running (abort the operation, in other words).
        // super (true);

        try {
            Client c = LoaderProvider.getInstance().getClientConfiguration();

            if (c.getLocalization().equalsIgnoreCase("Español")) {
                uploadWizard_es_ES = ResourceBundle.getBundle("org/comcast/locale/UploadWizard_es_ES");
            } else {
                uploadWizard_es_ES = ResourceBundle.getBundle("org/comcast/locale/UploadWizard_en_US");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void start(Map settings, ResultProgressHandle progress) {
        Message[] selectedItems = (Message[]) settings.get("newItems");
        SimpleList<Message> transfer = null;
        InterfaceWorks behind = null;

        progress.setProgress(uploadWizard_es_ES.getString("PREPARAR ARCHIVOS"), 0, 3);
        try {
            for (Message aux : selectedItems) {
                String destination = (String) settings.get("destination");
                aux.setRemotePath(destination);
            }

            Thread.sleep(3000);
        } catch (InterruptedException ie) {
        }

        progress.setProgress(uploadWizard_es_ES.getString("PREPARAR TAREA"), 1, 3);
        try {
            InterfaceWorks w = new Works();
            behind = (InterfaceWorks) Proxy.newProxyInstance(w.getClass().getClassLoader(),
                    w.getClass().getInterfaces(), new UploadHandler(w));

            SimpleList<Message> buffer = new SimpleList<>();
            transfer = buffer.toSimpleList(selectedItems);

            Thread.sleep(1000);
        } catch (InterruptedException ie) {
        }

        progress.setProgress(uploadWizard_es_ES.getString("TRANSFERIR ARCHIVOS"), 2, 3);
        try {

            DateScheduler date = (DateScheduler) settings.get("dateScheduler");
            Date runTime = org.quartz.DateBuilder.dateOf(date.getHour(), date.getMinute(), date.getSecond(),
                    date.getDay(), date.getMonth(), date.getYear());
            long l = runTime.getTime() - System.currentTimeMillis();

            behind.transferFiles(transfer, date);

            Thread.sleep(l + 10L);
        } catch (Exception ie) {
            ie.printStackTrace();
        }

        DateScheduler date = (DateScheduler) settings.get("dateScheduler");
        Date runTime = org.quartz.DateBuilder.dateOf(date.getHour(), date.getMinute(), date.getSecond(),
                date.getDay(), date.getMonth(), date.getYear());

        LocalIterator<Message> iterador = transfer.getIterador();

        String[] items = new String[iterador.size() + 2];
        int i = 0;

        String y = uploadWizard_es_ES.getString("TAREA REALIZADA: ");
        items[i] = y + runTime;

        i++;

        String h = uploadWizard_es_ES.getString("DESTINO REMOTO: ");
        items[i] = h + (String) settings.get("destination");

        while (iterador.hasMoreElements()) {
            i++;
            Message aux = iterador.returnElement();

            String d = uploadWizard_es_ES.getString("ARCHIVO TRANSFERIDO: ");
            items[i] = d + aux.getLocalPath();
        }

        // Replace null with an object reference to have this object returned
        // from the showWizard() method.

        progress.finished(Summary.create(items, null));
    }
}