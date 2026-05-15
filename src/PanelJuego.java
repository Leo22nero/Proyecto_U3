import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;



public class PanelJuego extends JPanel implements ActionListener, KeyListener {

    private Pantalla_final pantallaFinal;
    private Hilo musica;
    private Rana rana;
    private ArrayList<Obstaculo> obstaculos;
    private ArrayList<Mosquito> mosquitos;
    private Timer timer;
    private Random random;

    private int puntuacion = 0;
    private int record = 0;
    private int numeroPartida = 1;

    private boolean juegoTerminado = false;


    private int nubeX1 = 100;
    private int nubeX2 = 500;

    private Image spriteAgua;
    private Image fondo;

    public PanelJuego() {

        pantallaFinal = new Pantalla_final();
        this.setPreferredSize(new Dimension(800, 400));
        this.setBackground(new Color(135, 206, 235));
        this.setFocusable(true);
        this.addKeyListener(this);

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (juegoTerminado) {

                    Point click = e.getPoint();

                    if (
                            pantallaFinal
                                    .getBotonReiniciar()
                                    .contains(click)
                    ) {

                        reiniciar();
                    }

                    if (
                            pantallaFinal
                                    .getBotonPuntajes()
                                    .contains(click)
                    ) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Puntaje actual: " + puntuacion
                                        + "\n\nRecord historico: " + record,
                                "Tabla de puntuaciones",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                    if (
                            pantallaFinal
                                    .getBotonCreditos()
                                    .contains(click)
                    ) {

                        JPanel panelCreditos = new JPanel();

                        panelCreditos.setBackground(Color.BLACK);

                        panelCreditos.setPreferredSize(new Dimension(700, 320));

                        JTextArea texto = new JTextArea();

                        texto.setEditable(false);

                        texto.setBackground(Color.BLACK);

                        texto.setForeground(Color.WHITE);

                        texto.setFont(new Font("Arial", Font.BOLD, 22));

                        texto.setText("\n" + "JUEGO CREADO POR: \n\n"
                                        + "MARIANA CORREA CORREA\n\n"
                                        + "LEONARDO ESTRADA CORREA\n\n"
                                        + "ANA LAURA GERVACIO ELIAS\n\n"
                                        + "JULIA RUIZ LOPEZ"
                        );

                        texto.setLineWrap(false);

                        texto.setWrapStyleWord(false);

                        panelCreditos.add(texto);

                        JOptionPane.showMessageDialog(
                                null,
                                panelCreditos,
                                "Creditos",
                                JOptionPane.PLAIN_MESSAGE
                        );
                    }
                }
            }
        });

        try {
            spriteAgua = new ImageIcon(
                    getClass().getResource("/Sprites/agua.png")
            ).getImage();

        } catch (Exception e) {
            System.out.println("No se pudo cargar agua.png");
            spriteAgua = null;
        }

        try {
            fondo = new ImageIcon(
                    getClass().getResource("/Sprites/Imagenf.png")
            ).getImage();

        } catch (Exception e) {
            System.out.println("No se pudo cargar Imagenf.png");
            fondo = null;
        }

        rana = new Rana();
        obstaculos = new ArrayList<>();
        mosquitos = new ArrayList<>();
        random = new Random();

        timer = new Timer(20, this);
        timer.start();

        musica = new Hilo();
        musica.start();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), null);
        }

        g.setColor(Color.WHITE);

        g.fillOval(nubeX1, 60, 50, 30);
        g.fillOval(nubeX1 + 20, 50, 60, 40);
        g.fillOval(nubeX1 + 50, 60, 50, 30);

        g.fillOval(nubeX2, 100, 50, 30);
        g.fillOval(nubeX2 + 20, 90, 60, 40);
        g.fillOval(nubeX2 + 50, 100, 50, 30);

        if (spriteAgua != null) {

            for (int x = 0; x < getWidth(); x += 70) {
                g.drawImage(spriteAgua, x, 330, 70, 70, null);
            }

        } else {

            g.setColor(new Color(30, 144, 255));
            g.fillRect(0, 330, 800, 70);
        }

        rana.dibujar(g);

        for (Obstaculo o : obstaculos) {
            o.dibujar(g);
        }

        for (Mosquito m : mosquitos) {
            m.dibujar(g);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));

        g.drawString("Puntos: " + puntuacion, 20, 30);
        g.drawString("Record: " + record, 20, 60);

        g.setColor(new Color(0, 0, 0, 170));
        g.fillRoundRect(560, 15, 220, 45, 20, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("#TEAMSISTEMAS", 585, 44);

        if (juegoTerminado) {

            pantallaFinal.dibujar(g);

            return;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!juegoTerminado) {

            rana.actualizar();

            nubeX1 -= 1;
            nubeX2 -= 1;

            if (nubeX1 < -100) {
                nubeX1 = getWidth();
            }

            if (nubeX2 < -100) {
                nubeX2 = getWidth() + 300;
            }

            if (random.nextInt(100) < 3) {

                if (
                        obstaculos.isEmpty()
                                ||
                                obstaculos.get(
                                        obstaculos.size() - 1
                                ).getBounds().x < 600
                ) {

                    obstaculos.add(new Obstaculo(800, 330));
                }
            }

            if (random.nextInt(200) < 2) {
                mosquitos.add(new Mosquito(800, 250));
            }

            for (int i = 0; i < obstaculos.size(); i++) {

                Obstaculo o = obstaculos.get(i);

                o.mover();

                if (o.getBounds().intersects(rana.getBounds())) {

                    rana.golpear();

                    juegoTerminado = true;

                    if (puntuacion > record) {
                        record = puntuacion;
                    }

                    System.out.println(
                            "Record " + numeroPartida
                                    + " = Puntos: " + puntuacion
                    );

                    numeroPartida++;

                    repaint();

                    timer.stop();
                }

                if (o.fueraDePantalla()) {

                    obstaculos.remove(i);

                    puntuacion++;

                    if (puntuacion > record) {
                        record = puntuacion;
                    }
                }
            }

            for (int i = 0; i < mosquitos.size(); i++) {

                Mosquito m = mosquitos.get(i);

                m.mover();

                if (m.getBounds().intersects(rana.getBounds())) {

                    rana.golpear();

                    juegoTerminado = true;

                    if (puntuacion > record) {
                        record = puntuacion;
                    }

                    System.out.println(
                            "Record " + numeroPartida
                                    + " = Puntos: " + puntuacion
                    );

                    numeroPartida++;

                    repaint();

                    timer.stop();
                }

                if (m.fueraDePantalla()) {

                    mosquitos.remove(i);

                    puntuacion += 2;

                    if (puntuacion > record) {
                        record = puntuacion;
                    }
                }
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (!juegoTerminado) {

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                rana.saltar();
            }
        }
    }

    private void reiniciar() {

        rana.reset();

        obstaculos.clear();
        mosquitos.clear();

        puntuacion = 0;

        juegoTerminado = false;

        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}