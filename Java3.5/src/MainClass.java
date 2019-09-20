import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainClass {
        public static final int CARS_COUNT = 4 ;
        public static void main (String[] args) {
            try {
                start();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        public static void start() throws Exception{
            System.out.println( "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!" );
            Race race = new Race( new Road( 60 ), new Tunnel(CARS_COUNT), new Road( 40 ));
            Car[] cars = new Car[CARS_COUNT];
            CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT);
            CountDownLatch cdl1 = new CountDownLatch(CARS_COUNT);
            CountDownLatch cdl2 = new CountDownLatch(CARS_COUNT);
            for ( int i = 0 ; i < cars.length; i++) {
                cars[i] = new Car(race, 20 + ( int ) (Math.random() * 10 ), cyclicBarrier, cdl1, cdl2);
            }
            for ( int i = 0 ; i < cars.length; i++) {
                new Thread(cars[i]).start();
            }
            cdl1.await();
            System.out.println( "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!" );
            cdl2.await();
            System.out.println( "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!" );
        }
}
