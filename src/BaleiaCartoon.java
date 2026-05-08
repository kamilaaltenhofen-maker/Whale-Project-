import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaleiaCartoon extends JPanel implements ActionListener {
    private double x = -500;
    private double anguloSeno = 0;
    private Timer timer;
    private static final Color AZUL_CORPO = new Color(120, 170, 185);
    private static final Color AZUL_CONTORNO = new Color(70, 110, 130);

    public BaleiaCartoon() {
        timer = new Timer(15, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fundo Azul Escuro (Oceano)
        g2d.setColor(new Color(15, 35, 65));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        double yOndular = 250 + (Math.sin(anguloSeno) * 50);
        double rotacao = Math.cos(anguloSeno) * 0.15;

        // Esguicho no meio da tela
        if (x > 350 && x < 550) {
            desenharEsguicho(g2d, x + 380, yOndular - 30);
        }

        desenharBaleiaFinal(g2d, x, yOndular, rotacao);
    }
private void desenharBaleiaFinal(Graphics2D g2d, double x, double y, double angulo) {
    AffineTransform old = g2d.getTransform();
    g2d.translate(x + 300, y + 90);
    g2d.rotate(angulo);
    g2d.translate(-300, -90);

    // =====================
    // CORPO COM DORSO CURVADO (CONFORME LINHA VERMELHA)
    // =====================
    Path2D corpo = new Path2D.Double();
    
    // Começamos no ponto de encontro com a cauda
    corpo.moveTo(100, 220); 
    
 
    corpo.curveTo(300, 150, 500, 120, 610, 190);
    
    // Frente da cabeça
    corpo.curveTo(630, 280, 630, 300, 560, 315);
    
    // Barriga (inferior)
    corpo.curveTo(640, 390, 250, 370, 100, 220); 
    corpo.closePath();

    g2d.setColor(AZUL_CORPO);
    g2d.fill(corpo);
    g2d.setColor(AZUL_CONTORNO);
    g2d.setStroke(new BasicStroke(2));
    g2d.draw(corpo);
 
    // CAUDA 
  
    Path2D cauda = new Path2D.Double();
    cauda.moveTo(100, 220);
    
    // Lóbulo Superior (mais arqueado)
    cauda.curveTo(30, 140, 20, 180, 30, 120); 
    cauda.curveTo(40, 180, 50, 210, 80, 220);
    
    // Lóbulo Inferior
    cauda.curveTo(40, 240, 50, 280, 20, 330);
    cauda.curveTo(30, 270, 60, 240, 100, 220);
    cauda.closePath();

    g2d.setColor(AZUL_CORPO);
    g2d.fill(cauda);
    g2d.setColor(AZUL_CONTORNO);
    g2d.draw(cauda);

    // ===== NADADEIRA PEITORAL =====
    Path2D peitoral = new Path2D.Double();
    peitoral.moveTo(430, 325);
    peitoral.curveTo(390, 420, 470, 420, 490, 340);
    
    g2d.setColor(new Color(95, 135, 155));
    g2d.fill(peitoral);
    g2d.setColor(AZUL_CONTORNO);
    g2d.draw(peitoral);

    // ===== OLHO =====
    g2d.setColor(Color.WHITE);
    g2d.fillOval(535, 210, 30, 30);
    g2d.setColor(Color.BLACK);
    g2d.fillOval(550, 215, 12, 12);

    // ===== BOCA =====
    g2d.setColor(AZUL_CONTORNO);
    g2d.drawArc(510, 275, 80, 40, 210, 80);

    g2d.setTransform(old);

}

    private void desenharEsguicho(Graphics2D g2d, double x, double y) {
        g2d.setColor(new Color(120, 220, 255, 180));

    g2d.fillOval((int)x, (int)y - 60, 12, 25);
    g2d.fillOval((int)x - 30, (int)y - 40, 20, 30);
    g2d.fillOval((int)x + 40, (int)y - 45, 30, 30);

    // bolhas
    g2d.fillOval((int)x + 5, (int)y - 80, 8, 8);
    g2d.fillOval((int)x + 20, (int)y - 70, 10, 10);
}
    

  

    @Override
    public void actionPerformed(ActionEvent e) {
        x += 2.8;
        anguloSeno += 0.045;
        if (x > getWidth() + 400) x = -800;
        repaint();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Baleia Completa e Ondular");
        f.add(new BaleiaCartoon());
        f.setSize(1200, 650);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}