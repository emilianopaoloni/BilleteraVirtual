package vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import clasesListener.RegistrarUsuarioListener;
import utilidades.InstaladorUtilidades;

public class VistaRegistroUsuario extends JFrame {
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoEmail;
    private JPasswordField campoPassword;
    private JButton botonRegistrar;
    private JCheckBox checkBoxTerminos;
    private Image fondo; // Imagen de fondo
    private Font fuenteTitulo, fuenteTextos; // Fuente base cargada desde recursos

    public VistaRegistroUsuario() {
        // Usar RecursosUtil para cargar la imagen y la fuente
        fondo = InstaladorUtilidades.cargarImagen("recursos/fondoiniciosesion.jpg");
        fuenteTitulo = InstaladorUtilidades.cargarFuente("recursos/crypto CRASH.ttf");
        fuenteTextos = InstaladorUtilidades.cargarFuente("recursos/Conthrax-SemiBold.otf");

        // Configuración de la ventana
        setTitle("Registro de Usuario");
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

                // Dibujar el título centrado "REGISTRATE" "#00f691"
                g.setColor(Color.decode("#00f691")); // Establecer color verde agua
                g.setFont(fuenteTitulo.deriveFont(Font.ITALIC, 58)); // Usar fuente con tamaño 60 e italic

                String titulo = "REGISTRATE";
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
        panelCentral.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Borde negro
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes

        // Componentes
        Color colorEscritura = Color.decode("#00f691");

        JLabel labelNombre = new JLabel("nombre:");
        labelNombre.setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Tamaño 18, estilo italic
        labelNombre.setForeground(Color.WHITE); // Cambiar el color del texto a blanco
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelCentral.add(labelNombre, gbc);

        // Campo de texto para nombre
        campoNombre = new JTextField(25);
        campoNombre.setFont(new Font("Consolas", Font.PLAIN, 11));
        campoNombre.setBackground(Color.BLACK); // Fondo negro
        campoNombre.setForeground(colorEscritura); // Texto en color
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCentral.add(campoNombre, gbc);

        JLabel labelApellido = new JLabel("apellido:");
        labelApellido.setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Tamaño 18, estilo italic
        labelApellido.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentral.add(labelApellido, gbc);

        // Campo de texto para apellido
        campoApellido = new JTextField(25);
        campoApellido.setFont(new Font("Consolas", Font.PLAIN, 11));
        campoApellido.setBackground(Color.BLACK);
        campoApellido.setForeground(colorEscritura);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCentral.add(campoApellido, gbc);

        JLabel labelEmail = new JLabel("email:");
        labelEmail.setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Tamaño 18, estilo italic
        labelEmail.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCentral.add(labelEmail, gbc);

        // Campo de texto para email
        campoEmail = new JTextField(25);
        campoEmail.setFont(new Font("Consolas", Font.PLAIN, 11));
        campoEmail.setBackground(Color.BLACK);
        campoEmail.setForeground(colorEscritura);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelCentral.add(campoEmail, gbc);

        JLabel labelPassword = new JLabel("password");
        labelPassword.setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Tamaño 18, estilo italic
        labelPassword.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelCentral.add(labelPassword, gbc);

        // Campo de texto para password
        campoPassword = new JPasswordField(25);
        campoPassword.setFont(new Font("Consolas", Font.PLAIN, 11));
        campoPassword.setBackground(Color.BLACK);
        campoPassword.setForeground(colorEscritura);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panelCentral.add(campoPassword, gbc);

        // Checkbox para aceptar los términos y condiciones (sin fondo)
        checkBoxTerminos = new JCheckBox("aceptar términos y condiciones");
        checkBoxTerminos.setFont(fuenteTextos.deriveFont(Font.PLAIN, 11)); // Fuente crypto CRASH
        checkBoxTerminos.setForeground(colorEscritura); // Color verde agua
        checkBoxTerminos.setBackground(colorBackground); // Sin fondo
        checkBoxTerminos.setBorderPainted(false); // Sin borde
        checkBoxTerminos.setFocusPainted(false); // Sin efecto de foco
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelCentral.add(checkBoxTerminos, gbc);

        // Botón de registrar
        botonRegistrar = new JButton("Registrarse");
        botonRegistrar.setFont(fuenteTitulo.deriveFont(Font.ITALIC, 18));
        botonRegistrar.setBackground(colorEscritura);
        botonRegistrar.setForeground(Color.BLACK);
        botonRegistrar.setOpaque(true);
        botonRegistrar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        botonRegistrar.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCentral.add(botonRegistrar, gbc);
        
     // Crear el botón "Volver a inicio de sesión"
        JButton botonVolver = new JButton("Volver a Inicio de Sesión");
        botonVolver.setFont(fuenteTitulo.deriveFont(Font.ITALIC, 18));
        botonVolver.setBackground(colorEscritura);
        botonVolver.setForeground(Color.BLACK);
        botonVolver.setOpaque(true);
        botonVolver.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        botonVolver.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 6; // Lo coloca debajo del botón de registrar
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCentral.add(botonVolver, gbc);

        // Agregar la acción al botón "Volver a Inicio de Sesión"
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes cerrar la ventana actual y abrir la vista de inicio de sesión
                dispose(); // Cierra la ventana actual
                new VistaInicioSesion(); // Abre la vista de inicio de sesión
            }
        });

        // Agregar el panel central al panel de fondo
        panelFondo.add(panelCentral);

        // Hacer visible la ventana
        setVisible(true);

        // Agregar eventos
        this.botonRegistrar.addActionListener(new RegistrarUsuarioListener(this));
    }

    // Métodos para acceder a los componentes
    public String getNombre() {
        return campoNombre.getText();
    }

    public String getApellido() {
        return campoApellido.getText();
    }

    public String getEmail() {
        return campoEmail.getText();
    }

    public String getPassword() {
        return new String(campoPassword.getPassword());
    }

    public JButton getBotonRegistrar() {
        return botonRegistrar;
    }

    // Método para obtener el valor booleano del checkbox
    public boolean isTerminosAceptados() {
        return checkBoxTerminos.isSelected();
    }
}
