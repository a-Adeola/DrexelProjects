package abstractedInputOutput;

/**
 * Please note this class is here as an example and the error checking is minimal.
 *
 * @author Sean Grimes, sean@seanpgrimes.com
 */
public class ConsoleOut extends Out {
    // No public c'tor to fit the singleton requirements
    private ConsoleOut() {}
    // The instance that's returned in the getInstance() call
    private static ConsoleOut instance;

    // Return a abstractedInputOutput.ConsoleOut object in a threadsafe manner with lazy initialization
    public static ConsoleOut getInstance() {
        if(instance == null) {
            synchronized (ConsoleOut.class) {
                if(instance == null)
                    instance = new ConsoleOut();
            }
        }
        return instance;
    }

    // This function simply prints a message to the console
    public void say (Object msg) {
        System.out.println(msg);
    }
}
