package it.ennova.rxwifi.internals;

import static it.ennova.rxwifi.internals.Channel.*;

/**
 * This class is an internal class that contains the methods needed in order to convert the frequency
 * of the given WiFi Network into the channel it's using for communicating.
 */
class ChannelUtils {

    private ChannelUtils() {}

    public static int fromFrequency (int frequency) throws FrequencyOutOfRangeException{
        if (CHANNEL_24GHZ.isInRange(frequency)) {
            return CHANNEL_24GHZ.getChannelFrom(frequency);
        } else if (CHANNEL_5GHZ.isInRange(frequency)) {
            return CHANNEL_5GHZ.getChannelFrom(frequency);
        } else {
            throw new FrequencyOutOfRangeException();
        }
    }
}
