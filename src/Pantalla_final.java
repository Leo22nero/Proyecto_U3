import java.awt.*;

public class Pantalla_final {

    private Rectangle botonReiniciar;
    private Rectangle botonPuntajes;
    private Rectangle botonCreditos;

    private final int anchoPanel = 520;
    private final int altoPanel = 320;

    public Pantalla_final() {

        int xPanel = (800 - anchoPanel) / 2;
        int yPanel = (400 - altoPanel) / 2;

        botonReiniciar = new Rectangle(
                xPanel + 80,
                yPanel + 120,
                360,
                55
        );

        botonPuntajes = new Rectangle(
                xPanel + 80,
                yPanel + 190,
                360,
                55
        );

        botonCreditos = new Rectangle(
                xPanel + 80,
                yPanel + 260,
                360,
                55
        );
    }

    public void dibujar(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        int xPanel = (800 - anchoPanel) / 2;
        int yPanel = (400 - altoPanel) / 2;

        g2.setColor(new Color(0, 0, 0, 170));
        g2.fillRect(0, 0, 800, 400);

        g2.setColor(new Color(202, 190, 160));
        g2.fillRoundRect(
                xPanel,
                yPanel,
                anchoPanel,
                altoPanel,
                25,
                25
        );

        g2.setColor(new Color(235, 235, 235));
        g2.fillRoundRect(
                xPanel + 20,
                yPanel + 20,
                480,
                70,
                20,
                20
        );

        g2.setColor(new Color(84, 152, 219));
        g2.setFont(new Font("Arial", Font.BOLD, 42));

        String titulo = "Juego Terminado";

        FontMetrics fmTitulo = g2.getFontMetrics();

        int xTitulo =
                xPanel
                        +
                        (anchoPanel - fmTitulo.stringWidth(titulo)) / 2;

        g2.drawString(titulo, xTitulo, yPanel + 68);

        dibujarBoton(
                g2,
                botonReiniciar,
                "Volver a jugar",
                26
        );

        dibujarBoton(
                g2,
                botonPuntajes,
                "Tabla de puntuaciones",
                22
        );

        dibujarBoton(
                g2,
                botonCreditos,
                "Creditos",
                28
        );
    }

    private void dibujarBoton(
            Graphics2D g2,
            Rectangle boton,
            String texto,
            int tamaño
    ) {

        g2.setColor(new Color(235, 235, 235));

        g2.fillRoundRect(
                boton.x,
                boton.y,
                boton.width,
                boton.height,
                15,
                15
        );

        g2.setColor(new Color(84, 152, 219));

        g2.setFont(new Font("Arial", Font.BOLD, tamaño));

        FontMetrics fm = g2.getFontMetrics();

        int xTexto =
                boton.x
                        +
                        (boton.width - fm.stringWidth(texto)) / 2;

        int yTexto =
                boton.y
                        +
                        ((boton.height - fm.getHeight()) / 2)
                        +
                        fm.getAscent();

        g2.drawString(texto, xTexto, yTexto);
    }

    public Rectangle getBotonReiniciar() {
        return botonReiniciar;
    }

    public Rectangle getBotonPuntajes() {
        return botonPuntajes;
    }

    public Rectangle getBotonCreditos() {
        return botonCreditos;
    }
}