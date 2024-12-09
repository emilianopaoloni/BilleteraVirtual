package vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import clasesListener.IniciarSesionListener;
import utilidades.InstaladorUtilidades;

public class VistaInicioSesion extends JFrame {

    private JTextField campoEmail;
    private JPasswordField campoPassword;
    private JButton botonIniciarSesion;
    private JButton botonRegistrate;
    private Image fondo; // Imagen de fondo
    private Font fuenteTitulo, fuenteTextos;// Fuente base cargada desde recursos

    public VistaInicioSesion() {
        // Usar RecursosUtil para cargar la imagen y la fuente
        fondo = InstaladorUtilidades.cargarImagen("recursos/fondoiniciosesion.jpg");
        fuenteTitulo = InstaladorUtilidades.cargarFuente("recursos/crypto CRASH.ttf");
        fuenteTextos = InstaladorUtilidades.cargarFuente("recursos/Conthrax-SemiBold.otf");
        // Configuración de la ventana
        setTitle("Login");
        setSize(800, 600); // Ajusta el tamaño según sea necesario
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setResizable(false); // Evita que el usuario pueda agrandarla
        
     // Reutilizar cargarImagen para establecer el ícono de la ventana
        ImageIcon icono = InstaladorUtilidades.cargarIcono("logoapp1",60); // Ajusta el nombre según corresponda
        if (icono != null) {
            setIconImage(icono.getImage()); // Usar la imagen del ImageIcon
        }

        // Crear un panel con imagen de fondo
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Dibujar la imagen de fondo
                if (fondo != null) {
                    g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                }

                // Dibujar el título centrado
                g.setColor(Color.YELLOW); // Establecer color amarillo
                g.setFont(fuenteTitulo.deriveFont(Font.ITALIC, 60)); // Usar fuente con tamaño 60 e italic

                String titulo = "CRYPTO CRASH";
                FontMetrics metrics = g.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(titulo)) / 2; // Calcular posición centrada
                int y = (getHeight() / 8) + metrics.getAscent(); // Posición vertical (ajustada)

                g.drawString(titulo, x, y);
            }
        };

        panelFondo.setLayout(new GridBagLayout()); // Centra el recuadro
        this.setContentPane(panelFondo); // Establece el panel de fondo como principal

        // Crear el recuadro central con color de fondo específico
        JPanel panelCentral = new JPanel(new GridBagLayout());
        Color colorBackground = Color.decode("#171717"); // Color recuadro #171717
        panelCentral.setBackground(colorBackground); // Fondo negro
        panelCentral.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Borde negro
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes

        // Componentes
        Color colorEscritura = Color.decode("#00f691");

        JLabel labelEmail = new JLabel("email");
        labelEmail.setFont(fuenteTextos.deriveFont(Font.BOLD, 14)); // Tamaño 18, estilo italic
        labelEmail.setForeground(Color.WHITE); // Cambiar el color del texto a blanco
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelCentral.add(labelEmail, gbc);

        // Campo de texto para email
        campoEmail = new JTextField(25);
        campoEmail.setFont(new Font("Consolas", Font.BOLD, 11));
        campoEmail.setBackground(Color.BLACK); // Fondo negro
        campoEmail.setForeground(colorEscritura); // Texto en color
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCentral.add(campoEmail, gbc);

        JLabel labelPassword = new JLabel("password");
        labelPassword.setFont(fuenteTextos.deriveFont(Font.BOLD, 14));
        labelPassword.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentral.add(labelPassword, gbc);

        // Campo de texto para password
        campoPassword = new JPasswordField(25);
        campoPassword.setFont(new Font("Consolas", Font.PLAIN, 11));
        campoPassword.setBackground(Color.BLACK);
        campoPassword.setForeground(colorEscritura);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCentral.add(campoPassword, gbc);

        // Botón de iniciar sesión
        botonIniciarSesion = new JButton("Iniciar Sesión");
        botonIniciarSesion.setFont(fuenteTextos.deriveFont(Font.PLAIN, 13));
        botonIniciarSesion.setBackground(Color.decode("#00f691"));
        botonIniciarSesion.setForeground(Color.BLACK);
        botonIniciarSesion.setOpaque(true);
        botonIniciarSesion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        botonIniciarSesion.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCentral.add(botonIniciarSesion, gbc);

        // Botón de registrarse
        botonRegistrate = new JButton("Registrarse");
        botonRegistrate.setFont(fuenteTextos.deriveFont(Font.PLAIN, 13));
        botonRegistrate.setBackground(Color.decode("#00f691"));
        botonRegistrate.setForeground(Color.BLACK);
        botonRegistrate.setOpaque(true);
        botonRegistrate.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        botonRegistrate.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCentral.add(botonRegistrate, gbc);

        // Agregar el panel central al panel de fondo
        panelFondo.add(panelCentral);

        // Hacer visible la ventana
        setVisible(true);

        // Agregar eventos
        this.botonIniciarSesion.addActionListener(new IniciarSesionListener(this));
        botonRegistrate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarse();
            }
        });
    }
    
    public String getEmail() {
        return campoEmail.getText();
    }

    public String getPassword() {
        return new String(campoPassword.getPassword());
    }

    public JButton getBotonIniciarSesion() {
        return botonIniciarSesion;
    }

    public JButton getBotonRegistrate() {
        return botonRegistrate;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }


    private void registrarse() {
        this.setVisible(false);
        VistaRegistroUsuario vistaRegistroUsuario = new VistaRegistroUsuario();
        vistaRegistroUsuario.setVisible(true);
    }
}
