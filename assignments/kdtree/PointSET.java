/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {
   private TreeSet<Point2D> PointSet;

    public         PointSET()
    {
        PointSet = new TreeSet<>();
    }                               // construct an empty set of points


    public           boolean isEmpty()
    {
        return PointSet.isEmpty();
    }                      // is the set empty?


    public               int size()
    {
        return PointSet.size();
    }                         // number of points in the set


    public              void insert(Point2D p)
    {
        if (p == null) throw new IllegalArgumentException();
        PointSet.add(p);
    }           // add the point to the set (if it is not already in the set)



    public           boolean contains(Point2D p)
    {
        if (p == null) throw new IllegalArgumentException();
        return PointSet.contains(p);
    }  // does the set contain point p?


    public              void draw()
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        for (Point2D p: PointSet)
            StdDraw.point(p.x(), p.y());
    }                         // draw all points to standard draw


    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null) throw  new IllegalArgumentException();
       TreeSet<Point2D> set = new TreeSet<>();
       for (Point2D p: PointSet)
           if (inRec(p, rect))
               set.add(p);

           return set;
    }  // all points that are inside the rectangle (or on the boundary)


    public           Point2D nearest(Point2D p)
    {
        if (p == null) throw  new IllegalArgumentException();
        if (isEmpty()) return null;
        Point2D minP = null;
        double min = 0;

        for (Point2D p1: PointSet)
        {
            if (minP == null)
            {
                minP = p1;
                min = SQdistance(minP,p);
            }
            else if (min >= SQdistance(p1, p))
            {
                min = SQdistance(p1, p);
            minP = p1;
            }
        }

        return minP;
    }             // a nearest neighbor in the set to point p; null if the set is empty


    private boolean inRec(Point2D p, RectHV rectHV)
    {
        return p.x() <= rectHV.xmax() && p.x() >= rectHV.xmin() && p.y() >= rectHV.ymin() && p.y() <= rectHV.ymax();
    }


    private double SQdistance(Point2D p1, Point2D p2)
    {
        return (p1.x() - p2.x()) * (p1.x() - p2.x()) + (p1.y() - p2.y()) * (p1.y() - p2.y());
    }


    public static void main(String[] args)
    {
        PointSET p = new PointSET();
        p.insert(new Point2D(0.25, 1.0));
        p.insert(new Point2D(1.0, 1.0));
        p.insert(new Point2D(0.75, 0.75));
        p.insert(new Point2D(0.5, 0.75));
        p.insert(new Point2D(0.5, 0.0));
        p.insert(new Point2D(0.25, 0.75));
        p.insert(new Point2D(0.25, 0.0));
        p.insert(new Point2D(0.75, 0.0));
        p.insert(new Point2D(0.25, 0.75));

       Point2D p1 = p.nearest(new Point2D(0.75, 0.25));
        System.out.println(p1);

    }                 // unit testing of the methods (optiona

    } // unit testing of the methods (optional)




