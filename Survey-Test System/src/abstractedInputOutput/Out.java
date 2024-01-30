package abstractedInputOutput;

/**
 * Please note this class is here as an example and the error checking is minimal.
 *
 * @author Sean Grimes, sean@seanpgrimes.com
 */
public abstract class Out {
    // No public c'tor to fit the singleton requirements
    protected Out() {}
    // The instance that gets returned for all getInstance calls
    private static Out output;
    // The current flag value for abstractedInputOutput.ComType (console, file, audio, video) that this class
    // should use
    /*
        NOTE: Default is CONSOLE -- communicate with user on command line by default
        This will ultimately use the abstractedInputOutput.ConsoleIn class by default. Similar to how abstractedInputOutput.Out has
        abstractedInputOutput.ConsoleOut, abstractedInputOutput.FileOut, abstractedInputOutput.AudioOut, to use other abstractedInputOutput.In types you would need to write new
        classes that extend abstractedInputOutput.In (e.g. AudioIn, FileIn)
     */
    protected static ComType comType = ComType.CONSOLE;
    public static void setComType(ComType ct) { comType = ct; }
    public static ComType getComType() { return comType; }

    // Function implemented in the child classes
    public abstract void say(Object msg);

    public static Out getInstance() {
        if(Out.comType == ComType.CONSOLE) {
            output = ConsoleOut.getInstance();
        }
        return output;
    }
}

