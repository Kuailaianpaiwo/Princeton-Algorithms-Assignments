/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
   private Point[] pts;
   private List<LineSegment> lines;

    public FastCollinearPoints(Point[] points)
    {
        if (points == null) throw new IllegalArgumentException();
        for (Point p: points)
            if (p == null) throw new IllegalArgumentException();

        int n = points.length;
        for (int i = 0; i < n; i++)
            for (int j = i+1; j < n; j++)
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException();

                pts = new Point[n];
                lines = new ArrayList<>();

                for (int i = 0; i < n; i++)
                    pts[i] = points[i];

                for (int i = 0; i < n; i++)
                {
                    Double[] slopeBF = new Double[i];
                    Point[] pointAF = new Point[n - i -1];

                    for (int j = 0; j < i; j++)
                        slopeBF[j] = pts[i].slopeTo(pts[j]);

                    for (int j = 0; j < n - i - 1; j++)
                        pointAF[j] = pts[j + i + 1];

                    Merge.sort(slopeBF);
                    Arrays.sort(pointAF, pts[i].slopeOrder());

                    add(pts[i], slopeBF, pointAF);
                }


    }     // finds all line segments containing 4 or more points


    private void add(Point point, Double[] slopBF, Point[] pointAF)
    {
        for (int i = 0; i < pointAF.length -1; i++)
        {
            int count = 1;
            int index = i;
            while (i < pointAF.length-1 && (pointAF[i].slopeTo(point) == pointAF[i+1].slopeTo(point)))
            {
            count++;
            i++;
            }
            if (count >= 3 && !exist(point.slopeTo(pointAF[index]), slopBF))
            {
                Point[] points = new Point[i - index + 2];
                points[0] = point;
                for (int j = 1; j < points.length; j++)
                    points[j] = pointAF[index+j-1];
                Arrays.sort(points);
                lines.add(new LineSegment(points[0], points[points.length-1]));
            }

        }
    }

    private boolean exist(double s, Double[] slopBf)
    {
        int lo = 0;
        int hi = slopBf.length - 1;



        while (lo <= hi)
        {
            int mid = lo + (hi - lo)/2;
            if (slopBf[mid] > s) hi = mid-1;
            else if (slopBf[mid] < s) lo = mid + 1;
            else if (slopBf[mid] == s) return true;
        }
        return false;
    }

    public           int numberOfSegments()
    {
        return lines.size();
    }        // the number of line segments
    public LineSegment[] segments()
    {
        return lines.toArray(new LineSegment[lines.size()]);
    }                // the line segments

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
      }

