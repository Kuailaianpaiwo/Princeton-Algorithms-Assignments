/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segment;
    private int elements;

    public BruteCollinearPoints(Point[] points)
    {
        if (points == null) throw new IllegalArgumentException();
        for (Point p: points)
            if (p == null) throw new IllegalArgumentException();

        int N = points.length;
        for (int i =0;i<N;i++)
            for (int j = i+1;j<N;j++)
                if (points[i].slopeTo(points[j])==Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException();



            LineSegment[] tem = new LineSegment[N/4+1];
        Point[] temp = new Point[points.length];
        for (int i =0;i<N;i++)temp[i] = points[i];
            for (int i =0;i < N;i++)
            {
                for (int j = i+1;j<N;j++) {
                    for (int k = j + 1; k < N; k++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {
                            for (int l = k + 1; l < N; l++) {
                                if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                                    Point[] points1 = new Point[4];
                                    points1[0] = points[i];
                                    points1[1] = points[j];
                                    points1[2] = points[k];
                                    points1[3] = points[l];
                                    Arrays.sort(points1);
                                    LineSegment lineSegment = new LineSegment(points1[0],
                                                                              points1[3]);
                                    tem[elements++] = lineSegment;
                                }
                            }
                        }
                    }
                }
            }
            segment = new LineSegment[elements];
            for (int i=0;i<elements;i++) segment[i]=tem[i];

    }    // finds all line segments containing 4 points
    public           int numberOfSegments()
    {
        return elements;
    }        // the number of line segments
    public LineSegment[] segments()
    {
        return segment;
    }                // the line segments

    public static void main(String[] args) {
         Point p1 = new Point(1987, 17395);
         Point p2 = new Point(18049, 19871);
         Point p3 = new Point(10821, 11069);
         Point p4 = new Point(13654, 19871);
         Point p5 = new Point(10012, 15954);
         Point p6 = new Point(20164, 17395);
         Point p7 = new Point(8897, 15954);
         Point p8 = new Point(20221, 11069);
         Point p9 = new Point( 17181, 15954 );
         Point p10 = new Point(8278, 11069 );
         Point p11 = new Point( 20336, 17850);
         Point p12 = new Point( 1860, 17850);
         Point p13 = new Point( 10536, 11069);
         Point p14 = new Point( 12352, 17850);
         Point p15 = new Point( 16149, 17850);
         Point p16 = new Point(15136, 17395);
         Point p17 = new Point( 16205, 19871);
         Point p18 = new Point(9884, 17395);
         Point p19 = new Point(4698, 15954);
         Point p20 = new Point(9846, 19871);


         Point[] points = new Point[20];
         points[0] = p1;
         points[1] = p2;
         points[2] = p3;
         points[3] = p4;
         points[4] = p5;
         points[5] = p6;
         points[6] = p7;
         points[7] = p8;
         points[8] = p9;
         points[9] = p10;
         points[10] = p11;
         points[11] = p12;
         points[12] = p13;
         points[13] = p14;
         points[14] = p15;
         points[15] = p16;
         points[16] = p17;
         points[17] = p18;
         points[18] = p19;
         points[19] = p20;
         FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
         System.out.println(fastCollinearPoints.numberOfSegments());
        for (int i = 0;i < fastCollinearPoints.numberOfSegments();i++)
            System.out.println(fastCollinearPoints.segments()[i]);



    }

}
