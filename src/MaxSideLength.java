import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MaxSideLength {
    public static class Point {
        public int x;
        public int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public MaxSideLength(){}
    public void print(List<Point> points) {
        for(Point point: points){
            System.out.println("(" + point.x + ", " + point.y + ")");
        }
    }
    public long solve(String inputFile) {
        //Parsing Data from the input file
        int n;
        List<Point> points = new ArrayList<>();
        File input_file = new File(inputFile);
        try (BufferedReader buffer = new BufferedReader(new FileReader(input_file))) {
            String str;
            n = Integer.parseInt(buffer.readLine());
            while ((str = buffer.readLine()) != null) {
                String[] point = str.split(" ");
                int x = Integer.parseInt(point[0]);
                int y = Integer.parseInt(point[1]);
                points.add(new Point(x, y));
            }
        }
        catch (IOException e) {
            System.out.println("Please, enter a valid path...");
            return -1;
        }

        //PreSorting points on x-axis and on y-axis
        List<Point> xSortedPoints = new ArrayList<>(points);
        List<Point> ySortedPoints = new ArrayList<>(points);
        xSortedPoints.sort(Comparator.comparingInt(p -> p.x));
        ySortedPoints.sort(Comparator.comparingInt(p -> p.y));
        //Calling the recursive Divide and Conquer function
        return maxSideLength(xSortedPoints, ySortedPoints, n);
    }
    public long maxSideLength(List<Point> xSortedPoints, List<Point> ySortedPoints, int n) {
        //Base Case: when n = 1 return infinity (max integer)
        if(n == 1){
            return Integer.MAX_VALUE;
        }
        //When n = 2 return max(x_difference, y_difference)
        else if(n == 2){
            Point p0 = xSortedPoints.get(0);
            Point p1 = xSortedPoints.get(1);
            long xDifference = Math.abs(p0.x - p1.x);
            long yDifference = Math.abs(p0.y - p1.y);
            return Math.max(xDifference, yDifference);
        }

        //Divide into to sub problems
        int mid = n / 2;
        int xMedian = xSortedPoints.get(mid).x;

        List<Point> yLeftSortedPoints = new ArrayList<>();
        List<Point> yRightSortedPoints = new ArrayList<>();
        for(Point point: ySortedPoints) {
            if(point.x < xMedian) {
                yLeftSortedPoints.add(point);
            } else {
                yRightSortedPoints.add(point);
            }
        }

        //Conquer every sub problem
        long dl = maxSideLength(xSortedPoints.subList(0, mid), yLeftSortedPoints, mid);
        long dr = maxSideLength(xSortedPoints.subList(mid, n), yRightSortedPoints, n - mid);
        long dMin = Math.min(dl, dr);

        //Combine the solutions
        List<Point> strip = new ArrayList<>();
        for(Point point: ySortedPoints){
            if(Math.abs(point.x - xMedian) < dMin){
                strip.add(point);
            }
        }
        return minOfMax(strip, dMin);
    }
    public long minOfMax(List<Point> strip, long dMin){
        //We only need to get the maximum side length between every point and the one after it
        //And update the min accordingly
        long min = dMin;
        for(int i = 0; i < strip.size() - 1; i++){
            Point p0 = strip.get(i);
            Point p1 = strip.get(i + 1);
            long xDifference = Math.abs(p0.x - p1.x);
            long yDifference = Math.abs(p0.y - p1.y);
            min = Math.min(min, Math.max(xDifference, yDifference));
        }
        return min;
    }
}