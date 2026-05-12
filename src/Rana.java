import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.util.Objects;

public class Rana {

    // =========================
    // POSICIÓN
    // =========================
    private int x = 100;
    private int y = 300;

    // =========================
    // FÍSICA
    // =========================
    private int velocidadY = 0;

    private final int GRAVEDAD = 1;
    private final int SUELO_Y = 320;

    private boolean enSuelo = true;

    // =========================
    // SPRITES
    // =========================
    private Image rana1;
    private Image rana2;
    private Image ranaSalto;

    // Sprite actual
    private Image spriteActual;

    // =========================
    // ANIMACIÓN
    // =========================
    private int animacionContador = 0;

    public Rana() {

        try {

            // Sprite 1 corriendo
            rana1 = new ImageIcon(
                    Objects.requireNonNull(getClass().getResource(
                            "/Sprites/rana1.png"
                    ))
            ).getImage();

            // Sprite 2 corriendo
            rana2 = new ImageIcon(
                    Objects.requireNonNull(getClass().getResource(
                            "/Sprites/rana2.png"
                    ))
            ).getImage();

            // Sprite salto
            ranaSalto = new ImageIcon(
                    Objects.requireNonNull(getClass().getResource(
                            "/Sprites/ranaSalto.png"
                    ))
            ).getImage();

            // Sprite inicial
            spriteActual = rana1;

        } catch (Exception e) {

            System.out.println(
                    "Error cargando sprites de rana"
            );
        }
    }

    // =========================
    // SALTAR
    // =========================
    public void saltar() {

        if (enSuelo) {

            velocidadY = -16;
            enSuelo = false;
        }
    }

    // =========================
    // ACTUALIZAR
    // =========================
    public void actualizar() {

        // Movimiento vertical
        y += velocidadY;

        // Física del salto
        if (y < SUELO_Y) {

            velocidadY += GRAVEDAD;
            enSuelo = false;

        } else {

            y = SUELO_Y;
            velocidadY = 0;
            enSuelo = true;
        }

        // =========================
        // ANIMACIÓN
        // =========================
        animacionContador++;

        // Si está saltando
        if (!enSuelo) {

            spriteActual = ranaSalto;

        } else {

            // Alternar sprites
            if ((animacionContador / 10) % 2 == 0) {

                spriteActual = rana1;

            } else {

                spriteActual = rana2;
            }
        }
    }

    // =========================
    // DIBUJAR
    // =========================
    public void dibujar(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        // =========================
        // MEJORAR CALIDAD
        // =========================
        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );

        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
        );

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // =========================
        // DIBUJAR SPRITE
        // =========================
        if (spriteActual != null) {

            g2d.drawImage(
                    spriteActual,
                    x,
                    y,
                    48,
                    48,
                    null
            );

        } else {

            // Si falla el sprite
            g2d.setColor(Color.GREEN);

            g2d.fillRect(
                    x,
                    y,
                    30,
                    30
            );
        }
    }

    // =========================
    // COLISIONES
    // =========================
    public Rectangle getBounds() {

        return new Rectangle(
                x,
                y,
                40,
                40
        );
    }

    // =========================
    // REINICIAR
    // =========================
    public void reset() {

        y = SUELO_Y;

        velocidadY = 0;

        enSuelo = true;
    }
}