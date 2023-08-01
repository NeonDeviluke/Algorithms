import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    private int[][] rgb;
    private double[][] energy;

    private double[] seamDist;
    private int[] seamPath;

    private int count = 0;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        this.picture = picture;
        width = picture.width();
        height = picture.height();

        setRGB(picture);
        initEnergy();

    }

    private void initEnergy() {
        energy = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setEnergy(i, j);
            }
        }
    }

    private void setRGB(Picture picture) {
        rgb = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rgb[i][j] = picture.get(j, i).getRGB();
            }
        }
    }

    private void setEnergy(int i, int j) {
        if (i == 0 || j == 0 || i == height - 1 || j == width - 1)
            energy[i][j] = 1000;
        else
            energy[i][j] = Math.sqrt(deltaX(j, i) + deltaY(j, i));
    }


    // current picture
    public Picture picture() {
        Picture p = new Picture(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                p.set(j, i, new Color(rgb[i][j]));
            }
        }
        picture = p;
        setRGB(picture);
        initEnergy();
        return picture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (y >= height || y < 0 || x >= width || x < 0) throw new IllegalArgumentException();
        return energy[y][x];
    }

    // rgb number of col x, row y
    private int getRed(int x, int y) {
        return (rgb[y][x] >> 16) & 0xFF;
    }

    private int getGreen(int x, int y) {
        return (rgb[y][x] >> 8) & 0xFF;
    }

    private int getBlue(int x, int y) {
        return (rgb[y][x]) & 0xFF;
    }

    // delta value of col x, row y
    private double deltaX(int x, int y) {
        int reddiff = getRed(x + 1, y) - getRed(x - 1, y);
        int greendiff = getGreen(x + 1, y) - getGreen(x - 1, y);
        int bluediff = getBlue(x + 1, y) - getBlue(x - 1, y);
        return reddiff * reddiff + greendiff * greendiff + bluediff * bluediff;
    }

    private double deltaY(int x, int y) {
        int reddiff = getRed(x, y + 1) - getRed(x, y - 1);
        int greendiff = getGreen(x, y + 1) - getGreen(x, y - 1);
        int bluediff = getBlue(x, y + 1) - getBlue(x, y - 1);
        return reddiff * reddiff + greendiff * greendiff + bluediff * bluediff;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int wid = width;
        int hei = height;
        findSP(hei, wid, false);
        int[] result = new int[width];
        for (int v = seamPath[width * height + 1]; v > 0; v = seamPath[v])
            result[(v - 1) / height] = (v - 1) % height;
        return result;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        findSP(width, height, true);
        int[] result = new int[height];
        for (int v = seamPath[width * height + 1]; v > 0; v = seamPath[v])
            result[(v - 1) / width] = (v - 1) % width;
        return result;
    }

    private void findSP(int width, int height, boolean vertical) {
        int n = width * height;
        seamDist = new double[n + 2];
        seamPath = new int[n + 2];

        for (int i = 1; i < n + 2; i++) {
            if (i <= width) seamDist[i] = 0;
            else seamDist[i] = Double.POSITIVE_INFINITY;
        }
        seamDist[0] = 0;

        for (int i = width + 1; i < n + 1; i++) {
            relax(i, width, vertical);
        }

        for (int i = n - width + 1; i < n + 1; i++) {
            relax(n + 1, i, 1000000);
        }
    }

    private void relax(int i, int width, boolean vertical) {
        if (vertical) relaxV(i, width);
        else relaxH(i, width);
    }

    private void relaxV(int i, int width) {
        int x = (i - 1) % width;
        int y = (i - 1) / width;

        if (x > 0) relax(i, i - width - 1, energy(x - 1, y - 1));
        if (x < width - 1) relax(i, i - width + 1, energy(x + 1, y - 1));
        relax(i, i - width, energy(x, y - 1));
    }

    private void relaxH(int i, int width) {
        int x = (i - 1) / width;
        int y = (i - 1) % width;

        if (y > 0) relax(i, i - width - 1, energy(x - 1, y - 1));
        if (y < width - 1) relax(i, i - width + 1, energy(x - 1, y + 1));
        relax(i, i - width, energy(x - 1, y));
    }

    private void relax(int to, int from, double e) {
        if (seamDist[to] > seamDist[from] + e) {
            seamDist[to] = seamDist[from] + e;
            seamPath[to] = from;
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || height() <= 1 || seam.length == 0 || seam.length != width()
                || !isValidSeam(seam, height()))
            throw new IllegalArgumentException();

        height--;
        for (int i = 0; i < width; i++) {
            int rm = seam[i];
            int j = 0;
            while (j < height) {
                if (j >= rm) {
                    rgb[j][i] = rgb[j + 1][i];
                    energy[j][i] = energy[j + 1][i];
                }
                j++;
            }
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || width() <= 1 || seam.length == 0 || seam.length != height()
                || !isValidSeam(seam, width()))
            throw new IllegalArgumentException();
        width--;
        for (int i = 0; i < height; i++) {
            int rm = seam[i];
            int j = 0;
            while (j < width) {
                if (j >= rm) {
                    rgb[i][j] = rgb[i][j + 1];
                    energy[i][j] = energy[i][j + 1];
                }

                j++;
            }
        }
    }

    private boolean isValidSeam(int[] seam, int size) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] < 0 || seam[i + 1] < 0 || seam[i] > size - 1 || seam[i + 1] > size - 1) {
                return false;
            }

            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                return false;
            }
        }
        if (seam.length == 1) {
            if (seam[0] < 0 || seam[0] > size - 1) {
                return false;
            }
        }
        return true;
    }

}