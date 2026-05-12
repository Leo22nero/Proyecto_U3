import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.util.Objects;

public class Rana {

    private int x = 100;
    private int y = 300;


    private int velocidadY = 0;

    private final int GRAVEDAD = 1;
    private final int SUELO_Y = 320;

    private boolean enSuelo = true;
    private boolean golpeada = false;

    private int saltosRealizados = 0;
    private final int MAX_SALTOS = 2;


    private Image rana1;
    private Image rana2;
    private Image ranaSalto;
    private Image ranaGolpe;

    private Image spriteActual;


    private int animacionContador = 0;

    public Rana() {

        try {

            rana1 = new ImageIcon(
                    Objects.requireNonNull(getClass().getResource(
                            "/Sprites/rana1.png"
                    ))
            ).getImage();

            rana2 = new ImageIcon(
                    Objects.requireNonNull(getClass().getResource(
                            "/Sprites/rana2.png"
                    ))
            ).getImage();

            ranaSalto = new ImageIcon(
                    Objects.requireNonNull(getClass().getResource(
                            "/Sprites/ranaSalto.png"
                    ))
            ).getImage();

            ranaGolpe = new ImageIcon(
                    Objects.requireNonNull(getClass().getResource(
                            "/Sprites/ranaGolpe.png"
                    ))
            ).getImage();

            spriteActual = rana1;

        } catch (Exception e) {

            System.out.println(
                    "Error cargando sprites de rana"
            );
        }
    }


    public void saltar() {

        if (!golpeada && saltosRealizados < MAX_SALTOS) {

            velocidadY = -16;
            enSuelo = false;
            saltosRealizados++;
        }
    }


    public void actualizar() {

        if (golpeada) {

            spriteActual = ranaGolpe;
            return;
        }

        y += velocidadY;

        if (y < SUELO_Y) {

            velocidadY += GRAVEDAD;
            enSuelo = false;

        } else {

            y = SUELO_Y;
            velocidadY = 0;
            enSuelo = true;
            saltosRealizados = 0;
        }


        animacionContador++;

        if (!enSuelo) {

            spriteActual = ranaSalto;

        } else {

            if ((animacionContador / 10) % 2 == 0) {

                spriteActual = rana1;

            } else {

                spriteActual = rana2;
            }
        }
    }

    public void golpear() {

        golpeada = true;
        spriteActual = ranaGolpe;
    }


    public void dibujar(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (spriteActual != null) {

            g2d.drawImage(
                    spriteActual, x, y, 48, 48, null);

        } else {

            g2d.setColor(Color.GREEN);

            g2d.fillRect(x, y, 30, 30);
        }
    }


    public Rectangle getBounds() {

        return new Rectangle(x, y, 40, 40);
    }

    public void reset() {

        y = SUELO_Y;
        velocidadY = 0;
        enSuelo = true;
        saltosRealizados = 0;
        golpeada = false;
        spriteActual = rana1;
    }
}