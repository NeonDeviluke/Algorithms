/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> seganomitane = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        Point[] data = points.clone();
        Arrays.sort(data);

        if (hasDuplicate(data)) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < data.length - 3; i++) {
            Arrays.sort(data);
            Arrays.sort(data, data[i].slopeOrder());

            for (int p = 0, fir = 1, count = 2; count < data.length; count++) {
                while (count < data.length && (Double.compare(data[p].slopeTo(data[fir]),
                                                              data[p].slopeTo(data[count])) == 0)) {
                    count++;
                }

                if (count - fir >= 3 && data[p].compareTo(data[fir]) < 0) {
                    seganomitane.add(new LineSegment(data[p], data[count - 1]));
                }
                fir = count;
            }
        }

    }

    private boolean hasDuplicate(Point[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i].compareTo(data[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

    public int numberOfSegments() {
        return seganomitane.size();
    }

    public LineSegment[] segments() {
        return seganomitane.toArray(new LineSegment[seganomitane.size()]);
    }
}
