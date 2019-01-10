/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class KdTree {
    private Node root;

    private int size;

    private class Node {
        private Point2D val;
        private boolean dimention;
        private Node left;
        private Node right;
        private RectHV rectHV;



        public Node(Point2D val, RectHV r, boolean dimention) {
            this.val = val;
            this.rectHV =r;
            this.dimention = dimention;
        }

    }


    public KdTree() {
    } // construct an empty set of points

    public boolean isEmpty() {
        return root == null;
    }                      // is the set empty?


    public int size() {
        return size;
    } // number of points in the set


    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
       if (root == null) root = insertV(root, p, new RectHV(0, 0, 1, 1));
       else root = insertV(root, p, root.rectHV);
    } // add the point to the set (if it is not already in the set)

    private Node insertV(Node n, Point2D p, RectHV rec)
    {
        if (n == null)
        {
            size ++;
            return new Node(p, rec, true);

        }

        if (n.val.equals(p)) return n;

        RectHV r;

        int comp = Point2D.X_ORDER.compare(p, n.val);

        if (comp < 0)
        {
            if (n.left == null)
                r =  new RectHV(n.rectHV.xmin(), n.rectHV.ymin(), n.val.x(),
                                n.rectHV.ymax());
            else r = n.left.rectHV;

            n.left = insertH(n.left, p, r);
        } else
        {
            if (n.right == null)
                r = new RectHV(n.val.x(), n.rectHV.ymin(), n.rectHV.xmax(),
                               n.rectHV.ymax());
            else r = n.right.rectHV;

            n.right = insertH(n.right, p, r);
        }


        return n;
    }

    private Node insertH(Node n, Point2D p, RectHV rec)
    {
        if (n == null)
        {
            size ++;
            return new Node(p, rec, false);
        }

        if (n.val.equals(p)) return n;

        RectHV r;

        int comp = Point2D.Y_ORDER.compare(p, n.val);

        if (comp < 0)
        {
            if (n.left == null)
                r = new  RectHV(n.rectHV.xmin(), n.rectHV.ymin(), n.rectHV.xmax(),
                            n.val.y());
            else r = n.left.rectHV;

            n.left = insertV(n.left, p, r);
        } else
        {
            if (n.right == null)
                r = new RectHV(n.rectHV.xmin(), n.val.y(), n.rectHV.xmax(),
                               n.rectHV.ymax());
            else r = n.right.rectHV;

            n.right = insertV(n.right, p, r);
        }

        return n;
    }




    private int size(Node n) {
        if (n == null) return 0;
        return size;
    }


    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        return contain(root, p);
    } // does the set contain point p?

    private boolean contain(Node n, Point2D p) {
        Node tem = n;
        while (n != null) {
            if (n.val.equals(p)) return true;

            int comp;

            if (n.dimention)
                comp = Point2D.X_ORDER.compare(p, n.val);
            else comp = Point2D.Y_ORDER.compare(p, n.val);

            if (comp < 0) n = n.left;

            else n = n.right;
        }

        return false;
    }


    public void draw() {
        draw(root);
    } // draw all points to standard draw

    private void draw(Node n) {
        if (n == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(n.val.x(), n.val.y());

        if (n.dimention) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(n.val.x(), n.rectHV.ymin(), n.val.x(), n.rectHV.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(n.rectHV.xmin(), n.val.y(), n.rectHV.xmax(), n.val.y());
        }
        draw(n.left);
        draw(n.right);
    }


    public Iterable<Point2D> range(RectHV rect) {
        Iterable<Point2D> result = range(root, rect, new TreeSet<>());
        return result;
    }          // all points that are inside the rectangle (or on the boundary)

    private Iterable<Point2D> range(Node n, RectHV r, TreeSet<Point2D> set) {
        if (n == null) return set;
        if (intersect(r, n.rectHV)) {
            if (inRec(n.val, r))
                set.add(n.val);
            range(n.left, r, set);
            range(n.right, r, set);
        }

        return set;
    }

    private boolean intersect(RectHV r1, RectHV r2) {
        return !(r1.xmin() > r2.xmax() || r1.xmax() < r2.xmin() || r1.ymax() < r2.ymin()
                || r1.ymin() > r2.ymax());
    }

    private boolean inRec(Point2D p, RectHV rectHV) {
        return p.x() <= rectHV.xmax() && p.x() >= rectHV.xmin() && p.y() >= rectHV.ymin()
                && p.y() <= rectHV.ymax();
    }

    private double distance(Point2D p, RectHV r) {
        if (inRec(p, r)) return 0;

        if (p.x() < r.xmax() && p.x() > r.xmin()) {
            if (p.y() < r.ymin()) return (r.ymin() - p.y()) * (r.ymin() - p.y());
            else return (p.y() - r.ymax()) * (p.y() - r.ymax());
        }
        else if (p.y() < r.ymax() && p.y() > r.ymin()) {
            if (p.x() < r.xmin()) return (r.xmin() - p.x()) * (r.xmin() - p.x());
            else return (p.x() - r.xmax()) * (p.x() - r.xmax());
        }
        return min(distance(new Point2D(r.xmax(), r.ymax()), p),
                   distance(new Point2D(r.xmax(), r.ymin()), p),
                   distance(new Point2D(r.xmin(), r.ymax()), p),
                   distance(new Point2D(r.xmin(), r.ymin()), p));
    }

    private double min(double d1, double d2, double d3, double d4) {
        double min = d1;
        if (d2 < min) min = d2;
        if (d3 < min) min = d3;
        if (d4 < min) min = d4;
        return min;

    }

    private double distance(Point2D p1, Point2D p2) {
        return (p1.x() - p2.x()) * (p1.x() - p2.x()) + (p1.y() - p2.y()) * (p1.y() - p2.y());
    }


    public Point2D nearest(Point2D p) {
        if (root == null)
            return null;

        return nearst(root, p, distance(root.val, p), root).val;
    } // a nearest neighbor in the set to point p; null if the set is empty

    private Node nearst(Node n, Point2D p, double nearst, Node nearstN) {
        if (n == null || nearst < distance(p, n.rectHV))
            return nearstN;

        double near = distance(n.val, p);

        if (near < nearst) {
            nearst = near;
            nearstN = n;
        }

        Node r1 = nearst(n.left, p, nearst, nearstN);
        Node r2 = nearst(n.right, p, nearst, nearstN);

        double d1 = distance(r1.val, p);
        double d2 = distance(r2.val, p);

        if (d1 < d2) return r1;
        return r2;
    }


    public static void main(String[] args) {
        KdTree p = new KdTree();
        p.insert(new Point2D(0.25, 1.0));
        p.insert(new Point2D(1.0, 1.0));
        p.insert(new Point2D(0.75, 0.75));
        p.insert(new Point2D(0.5, 0.75));
        p.insert(new Point2D(0.5, 0.0));
        p.insert(new Point2D(0.25, 0.75));
        p.insert(new Point2D(0.25, 0.0));
        p.insert(new Point2D(0.75, 0.0));
        p.insert(new Point2D(0.25, 0.75));

        System.out.println(p.size);

    } // unit testing of the methods (optional)
}

//     private static class Node {
//         private Point2D p;
//         private RectHV  rect;
//         private Node    left;
//         private Node    right;
//
//         public Node(Point2D p, RectHV rect) {
//             RectHV r = rect;
//             if (r == null)
//                 r = new RectHV(0, 0, 1, 1);
//             this.rect   = r;
//             this.p      = p;
//         }
//     }
//
//     private Node    root;
//     private int     size;
//
//     // construct an empty set of points
//     public KdTree() {
//         root = null;
//         size = 0;
//     }
//
//     // is the set empty?
//     public boolean isEmpty() { return root == null; }
//
//     // number of points in the set
//     public int size() { return size; }
//
//     // larger or equal keys go right
//     private Node insertH(Node x, Point2D p, RectHV rect) {
//         if (x == null) {
//             size++;
//             return new Node(p, rect);
//         }
//         if (x.p.equals(p))  return x;
//
//         RectHV r;
//         int cmp = Point2D.Y_ORDER.compare(x.p, p);
//         if (cmp > 0) {
//             if (x.left == null)
//                 r = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.p.y());
//             else
//                 r = x.left.rect;
//             x.left = insertV(x.left, p, r);
//         } else {
//             if (x.right == null)
//                 r = new RectHV(rect.xmin(), x.p.y(), rect.xmax(), rect.ymax());
//             else
//                 r = x.right.rect;
//             x.right = insertV(x.right, p, r);
//         }
//
//         return x;
//     }
//
//     // larger or equal keys go right
//     private Node insertV(Node x, Point2D p, RectHV rect) {
//         if (x == null) {
//             size++;
//             return new Node(p, rect);
//         }
//         if (x.p.equals(p))  return x;
//
//         RectHV r;
//         int cmp = Point2D.X_ORDER.compare(x.p, p);
//         if (cmp > 0) {
//             if (x.left == null)
//                 r = new RectHV(rect.xmin(), rect.ymin(), x.p.x(), rect.ymax());
//             else
//                 r = x.left.rect;
//             x.left = insertH(x.left, p, r);
//         } else {
//             if (x.right == null)
//                 r = new RectHV(x.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
//             else
//                 r = x.right.rect;
//             x.right = insertH(x.right, p, r);
//         }
//
//         return x;
//     }
//
//     // add the point p to the set (if it is not already in the set)
//     public void insert(Point2D p) {
//         if (isEmpty())
//             root = insertV(root, p, null);
//         else
//             root = insertV(root, p, root.rect);
//     }
//
//     // larger or equal keys go right
//     private boolean contains(Node x, Point2D p, boolean vert) {
//         if (x == null)      return false;
//         if (x.p.equals(p))  return true;
//         int cmp;
//         if (vert)   cmp = Point2D.X_ORDER.compare(x.p, p);
//         else        cmp = Point2D.Y_ORDER.compare(x.p, p);
//         if (cmp > 0)        return contains(x.left, p, !vert);
//         else                return contains(x.right, p, !vert);
//     }
//
//     // does the set contain the point p?
//     public boolean contains(Point2D p) {
//         return contains(root, p, true);
//     }
//
//     private void draw(Node x, boolean vert) {
//         if (x.left != null)     draw(x.left, !vert);
//         if (x.right != null)    draw(x.right, !vert);
//
//         // draw the point first
//         StdDraw.setPenColor(StdDraw.BLACK);
//         StdDraw.setPenRadius(.01);
//         StdDraw.point(x.p.x(), x.p.y());
//
//         // draw the line
//         double xmin, ymin, xmax, ymax;
//         if (vert) {
//             StdDraw.setPenColor(StdDraw.RED);
//             xmin = x.p.x();
//             xmax = x.p.x();
//             ymin = x.rect.ymin();
//             ymax = x.rect.ymax();
//         } else {
//             StdDraw.setPenColor(StdDraw.BLUE);
//             ymin = x.p.y();
//             ymax = x.p.y();
//             xmin = x.rect.xmin();
//             xmax = x.rect.xmax();
//         }
//         StdDraw.setPenRadius();
//         StdDraw.line(xmin, ymin, xmax, ymax);
//     }
//
//     // draw all of the points to standard draw
//     public void draw() {
//         // draw the rectangle
//         StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
//         if (isEmpty()) return;
//         draw(root, true);
//     }
//
//     private void range(Node x, RectHV rect, Queue<Point2D> q) {
//         if (x == null)
//             return;
//         if (rect.contains(x.p))
//             q.enqueue(x.p);
//         if (x.left != null && rect.intersects(x.left.rect))
//             range(x.left, rect, q);
//         if (x.right != null && rect.intersects(x.right.rect))
//             range(x.right, rect, q);
//     }
//
//     // all points in the set that are inside the rectangle
//     public Iterable<Point2D> range(RectHV rect) {
//         Queue<Point2D> q = new Queue<Point2D>();
//         range(root, rect, q);
//         return q;
//     }
//
//     private Point2D nearest(Node x, Point2D p, Point2D mp, boolean vert) {
//         Point2D min = mp;
//
//         if (x == null)    return min;
//         if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(min))
//             min = x.p;
//
//         // choose the side that contains the query point first
//         if (vert) {
//             if (x.p.x() < p.x()) {
//                 min = nearest(x.right, p, min, !vert);
//                 if (x.left != null
//                         && (min.distanceSquaredTo(p)
//                         > x.left.rect.distanceSquaredTo(p)))
//                     min = nearest(x.left, p, min, !vert);
//             } else {
//                 min = nearest(x.left, p, min, !vert);
//                 if (x.right != null
//                         && (min.distanceSquaredTo(p)
//                         > x.right.rect.distanceSquaredTo(p)))
//                     min = nearest(x.right, p, min, !vert);
//             }
//         } else {
//             if (x.p.y() < p.y()) {
//                 min = nearest(x.right, p, min, !vert);
//                 if (x.left != null
//                         && (min.distanceSquaredTo(p)
//                         > x.left.rect.distanceSquaredTo(p)))
//                     min = nearest(x.left, p, min, !vert);
//             } else {
//                 min = nearest(x.left, p, min, !vert);
//                 if (x.right != null
//                         && (min.distanceSquaredTo(p)
//                         > x.right.rect.distanceSquaredTo(p)))
//                     min = nearest(x.right, p, min, !vert);
//             }
//         }
//         return min;
//     }
//
//     // a nearest neighbor in the set to p: null if set is empty
//     public Point2D nearest(Point2D p) {
//         if (isEmpty()) return null;
//         return nearest(root, p, root.p, true);
//     }
// }
