import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable{
    public volatile static int CARS_COUNT;
    CyclicBarrier cb;
    CountDownLatch[] cdls;
    static {
        CARS_COUNT = 0 ;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName () {
        return name;
    }
    public int getSpeed () {
        return speed;
    }
    public Car (Race race, int speed, CyclicBarrier cb, CountDownLatch... cdls) {
        this .race = race;
        this .speed = speed;
        CARS_COUNT++;
        this .name = "Участник #" + CARS_COUNT;
        this.cb = cb;
        this.cdls = cdls;
    }

    @Override
    public void run () {
        try {
            //cb.await();
            System.out.println( this .name + " готовится" );
            Thread.sleep( 500 + ( int )(Math.random() * 800 ));
            System.out.println( this .name + " готов" );
            cb.await();
            System.out.println(this .name+" стартовал");
            cdls[0].countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for ( int i = 0 ; i < race.getStages().size(); i++) {
            race.getStages().get(i).go( this );
        }
        cdls[1].countDown();
    }


}
