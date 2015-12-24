package it.ennova.rxwifi.internals;

/**
 * This class represents the channel of the network, so that it's easier to understand if the given
 * frequency is in range and if it is, calculate the channel it's transmitting to.
 */
class Channel {

    private final int min;
    private final int max;
    private final int offset;

    private static final int MIN_24GHZ = 2412;
    private static final int MAX_24GHZ = 2484;
    private static final int OFFSET_24GHZ = 1;
    private static final int MIN_5GHZ = 5170;
    private static final int MAX_5GHZ = 5825;
    private static final int OFFSET_5GHZ = 34;

    public static Channel CHANNEL_24GHZ = new Channel(MIN_24GHZ, MAX_24GHZ, OFFSET_24GHZ);
    public static Channel CHANNEL_5GHZ = new Channel(MIN_5GHZ, MAX_5GHZ, OFFSET_5GHZ);

    protected Channel(int min, int max, int offset) {
        this.min = min;
        this.max = max;
        this.offset = offset;
    }

    public boolean isInRange(int frequency) {
        return frequency >= min && frequency <= max;
    }

    public int getChannelFrom(int frequency) {
        return (frequency - min) / 5 + offset;
    }
}
