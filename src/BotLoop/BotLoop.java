package BotLoop;

public class BotLoop {

    private static int sleepTime = 180;          // sleep in seconds

    public static void startBotLoop() {
        try {Thread.sleep(10 * 1000L);}
        catch (InterruptedException e) {Thread.currentThread().interrupt();}

        while(true) {
            try {
                LoopMembers.loopMembers();
                LoopMessages.loopMessages();

                System.out.println("Pausing for '"+ sleepTime +"' seconds");
                Thread.sleep(sleepTime * 1000L);
                System.out.println("Paused for '"+ sleepTime +"' seconds");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
