package CGlab;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Renderer {

    public enum LineAlgo { NAIVE, DDA, BRESENHAM, BRESENHAM_INT; }

    private BufferedImage render;
    public final int h = 200;
    public final int w = 200;

    private String filename;
    private LineAlgo lineAlgo = LineAlgo.NAIVE;

    public Renderer(String filename) {
        render = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        this.filename = filename;
    }

    public void drawPoint(int x, int y) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);
        render.setRGB(x, y, white);
    }

    public void drawLine(int x0, int y0, int x1, int y1, LineAlgo lineAlgo) {
        if(lineAlgo == LineAlgo.NAIVE) drawLineNaive(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.DDA) drawLineDDA(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.BRESENHAM) drawLineBresenham(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.BRESENHAM_INT) drawLineBresenhamInt(x0, y0, x1, y1);
    }

    public void drawLineNaive(int x0, int y0, int x1, int y1) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);

//        render.setRGB(x0,y0,white);
//        render.setRGB(x1,y1,white);

        int dy = y1 - y0;
        int dx = x1 - x0;
        int m = dy/dx;
        int y = y0;

        for (int i = x0; i < x1; i++) {
            render.setRGB(i,y,white);
            y = y + m;
        }
    }

    public void drawLineDDA(int x0, int y0, int x1, int y1) {
        // TODO: zaimplementuj
    }

    public void drawLineBresenham(int x0, int y0, int x1, int y1) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);

        int dx = x1-x0;
        int dy = y1-y0;
        float derr = Math.abs(dy/(float)(dx));
        float err = 0;

        int y = y0;

        for (int x=x0; x<=x1; x++) {
            render.setRGB(x, y, white);
            err += derr;
            if (err > 0.5) {
                y += (y1 > y0 ? 1 : -1);
                err -= 1.;
            }
        } // Oktanty ktore dzialają: 0,1,7
    }

    public void drawLineBresenhamInt(int x0, int y0, int x1, int y1) {
        // TODO: zaimplementuj
    }

    public class Vec3i {
        public int x;
        public int y;
        public int z;

        @Override
        public String toString() {
            return x + " " + y + " " + z;
        }
    }

    public class Vec3f {
        public float x;
        public float y;
        public float z;
        @Override
        public String toString() {
            return x + " " + y + " " + z;
        }
    }

    public class Vec2f {
        public float x;
        public float y;
        @Override
        public String toString() {
            return x + " " + y;
        }
    }

    public class Vec2i {
        public int x;
        public int y;
        @Override
        public String toString() {
            return x + " " + y;
        }
    }

    public Vec3f barycentric(Vec2f A, Vec2f B, Vec2f C, Vec2f P) {
        Vec3f v1 = // tutaj potrzebujemy wektora składającego się ze współrzędnych
                // x wektorów AB, AC ora PA.

                Vec3f v2 = // tutaj potrzebujemy wektora składającego się ze współrzędnych
                // y wektorów AB, AC ora PA.

                Vec3f cross = // iloczyn wektorowy v1 i v2. Wskazówka: zaimplementuj do tego oddzielną metodę

                Vec2f uv = // wektor postaci: cross.x / cross.z, cross.y / cross.z

                //
                Vec3f barycentric = // współrzędne barycentryczne, uv.x, uv.y, 1- uv.x - uv.y
        return barycentric;
    }

    public void drawTriangle(Vec2f A, Vec2f B, Vec2f C) {
        // dla każdego punktu obrazu this.render:
        //      oblicz współrzędne baryc.
        //      jeśli punkt leży wewnątrz, zamaluj (patrz wykład)
    }



    public void save() throws IOException {
        File outputfile = new File(filename);
        render = Renderer.verticalFlip(render);
        ImageIO.write(render, "png", outputfile);
    }

    public void clear() {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int black = 0 | (0 << 8) | (0 << 16) | (255 << 24);
                render.setRGB(x, y, black);
            }
        }
    }

    public static BufferedImage verticalFlip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage flippedImage = new BufferedImage(w, h, img.getColorModel().getTransparency());
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return flippedImage;
    }
}