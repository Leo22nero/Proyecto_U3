import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class PanelJuego extends JPanel implements ActionListener, KeyListener {

    private Rana rana;
    private ArrayList<Obstaculo> obstaculos;
    private ArrayList<Mosquito> mosquitos;//pegar en codigomar
    private Timer timer;
    private Random random;

    private int puntuacion = 0;
    private boolean juegoTerminado = false;

    // Nubes
    private int nubeX1 = 100;
    private int nubeX2 = 500;

    // Imagen del agua
    private Image spriteAgua;

    // Imagen de fondo
    private Image fondo;

    public PanelJuego() {

        this.setPreferredSize(new Dimension(800, 400));

        this.setBackground(new Color(135, 206, 235));

        this.setFocusable(true);
        this.addKeyListener(this);

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
        mosquitos = new ArrayList<>();//agregar alcod
        random = new Random();

        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (fondo != null) {g.drawImage(fondo,0,0,getWidth(), getHeight(),null);
        }

        g.setColor(Color.WHITE);

        // Nube 1
        g.fillOval(nubeX1, 60, 50, 30);
        g.fillOval(nubeX1 + 20, 50, 60, 40);
        g.fillOval(nubeX1 + 50, 60, 50, 30);

        // Nube 2
        g.fillOval(nubeX2, 100, 50, 30);
        g.fillOval(nubeX2 + 20, 90, 60, 40);
        g.fillOval(nubeX2 + 50, 100, 50, 30);

        if (spriteAgua != null) {

            for (int x = 0; x < getWidth(); x += 70) {

                g.drawImage(
                        spriteAgua,
                        x,
                        330,
                        70,
                        70,
                        null
                );
            }

        } else {

            g.setColor(new Color(30, 144, 255));
            g.fillRect(0, 330, 800, 70);
        }

        rana.dibujar(g);

        for (Obstaculo o : obstaculos) {
            o.dibujar(g);
        }
        
        //agregar al cod marrr
        for (Mosquito m : mosquitos) {
    m.dibujar(g);
}

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));

        g.drawString(
                "Puntos: " + puntuacion,
                20,
                30
        );

        if (juegoTerminado) {

            g.setColor(Color.RED);

            g.setFont(
                    new Font("Arial", Font.BOLD, 30)
            );

            g.drawString(
                    "GAME OVER - Pulsa R",
                    250,
                    200
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!juegoTerminado) {

            rana.actualizar();

            // =========================
            // MOVIMIENTO DE NUBES
            // =========================
            nubeX1 -= 1;
            nubeX2 -= 1;

            if (nubeX1 < -100) {
                nubeX1 = getWidth();
            }

            if (nubeX2 < -100) {
                nubeX2 = getWidth() + 300;
            }

            // =========================
            // GENERAR OBSTÁCULOS
            // =========================
            if (random.nextInt(100) < 3) {

                if (
                        obstaculos.isEmpty()
                                ||
                                obstaculos.get(
                                        obstaculos.size() - 1
                                ).getBounds().x < 600
                ) {

                    obstaculos.add(
                            new Obstaculo(800, 330)
                    );
                }
            }
            
            //pegra codmariannaaa
            if (random.nextInt(200) < 2) {

    mosquitos.add(
            new Mosquito(800, 250)
    );
}

            // =========================
            // ACTUALIZAR OBSTÁCULOS
            // =========================
            for (int i = 0; i < obstaculos.size(); i++) {

                Obstaculo o = obstaculos.get(i);

                o.mover();

                // Colisión
                if (
                        o.getBounds().intersects(
                                rana.getBounds()
                        )
                ) {

                    juegoTerminado = true;
                    timer.stop();
                }

                // Sale de pantalla
                if (o.fueraDePantalla()) {

                    obstaculos.remove(i);
                    puntuacion++;
                }
            }
            //pegar mariana al codigo 
            for (int i = 0; i < mosquitos.size(); i++) {

    Mosquito m = mosquitos.get(i);

    m.mover();

    // Colisión
    if (
            m.getBounds().intersects(
                    rana.getBounds()
            )
    ) {

        juegoTerminado = true;
        timer.stop();
    }

    // Sale de pantalla
    if (m.fueraDePantalla()) {

        mosquitos.remove(i);

        puntuacion += 2;
    }
}
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Saltar
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            rana.saltar();
        }

        // Reiniciar
        if (
                e.getKeyCode() == KeyEvent.VK_R
                        &&
                        juegoTerminado
        ) {

            reiniciar();
        }
    }

    private void reiniciar() {

        rana.reset();

        obstaculos.clear();

        puntuacion = 0;

        juegoTerminado = false;

        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
