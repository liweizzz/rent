package arithmetic;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Exam2 {
    public static List<Point> test(List<Point> list){
        List<Point> result = new ArrayList<>();
        int size = list.size();
        if(size <3){
            return list;
        }
        for (int i = 0; i < list.size(); i++) {
            if(i == 0 || i == list.size()-1){
                result.add(list.get(i));
                continue;
            }
            Point prePoint = list.get(i - 1);
            Point point = list.get(i);
            Point nextPoint = list.get(i+1);

            int preSum =(prePoint.getX() + prePoint.getY());
            int sum =(point.getX() + point.getY());
            int nextSum =(nextPoint.getX() + nextPoint.getY());

            if((point.getX() == prePoint.getX() && point.getX() == nextPoint.getX())
                    || (point.getY() == prePoint.getY() && point.getY() == nextPoint.getY())
                    || (sum == preSum && sum == nextSum) || (sum == preSum + 2  && sum == nextSum -2)){
                continue;
            }
            result.add(point);
        }
        return result;
    }

    @Data
    static class Point {
        private int x;
        private int y;
        public Point(int x,int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        List<Point> list = new ArrayList<>();
        list.add(new Point(2,8));
        list.add(new Point(3,7));
        list.add(new Point(3,6));
        list.add(new Point(3,5));
        list.add(new Point(4,4));
        list.add(new Point(5,3));
        list.add(new Point(6,2));
        list.add(new Point(7,3));
        list.add(new Point(8,4));
        list.add(new Point(7,5));
        List<Point> test = test(list);
        System.out.println(test);
    }
}
