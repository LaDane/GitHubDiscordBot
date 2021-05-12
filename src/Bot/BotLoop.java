package Bot;

public class BotLoop {

    private static int sleepTime = 10;          // sleep in seconds

    public static void startBotLoop() {
        while(true) {
            try {
                Thread.sleep(sleepTime * 1000L);
                System.out.println("Paused for '"+ sleepTime +"' seconds");


            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
