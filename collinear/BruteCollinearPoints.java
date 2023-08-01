/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> seganomitane = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }

        Point[] data = points.clone();
        Arrays.sort(data);

        if (hasDuplicate(data)) {
            throw new IllegalArgumentException();
        }

        for (int first = 0; first < data.length - 3; first++) {
            for (int second = first + 1; second < data.length - 2; second++) {
                double slope1 = data[first].slopeTo(data[second]);
                for (int third = second + 1; third < data.length - 1; third++) {
                    double slope2 = data[first].slopeTo(data[third]);
                    if (slope2 == slope1) {
                        for (int fourth = third + 1; fourth < data.length; fourth++) {
                            double slope3 = data[first].slopeTo(data[fourth]);
                            if (slope3 == slope1) {
                                seganomitane.add(new LineSegment(data[first], data[fourth]));
                            }
                        }
                    }
                }
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
