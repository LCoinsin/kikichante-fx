
import javafx.scene.control.TextField;

public class CountDown {
    private  int x;

     public CountDown(int x){
         this.x=x;
     }
        static void countDown( int x ) {
            while (x > 0 ) {
                System.out.println("x = " + x);
                TextField rb = new TextField(String.valueOf(x));
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}
                x--;
            }
        }


    }

