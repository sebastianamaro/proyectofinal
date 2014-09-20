package com.example.TuttiFruttiCore;

/**
 * Created by Nituguivi on 19/09/2014.
 */
public class Constants {
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public final static String GAME_ID_EXTRA_MESSAGE = "com.example.tuttifrutti.GAMEID";
    public final static String ROUND_ID_EXTRA_MESSAGE = "com.example.tuttifrutti.ROUNDID";
    public final static String GAME_INFO_EXTRA_MESSAGE = "com.example.tuttifrutti.GAMEINFO";
    public final static String GAME_SETTINGS_EXTRA_MESSAGE = "com.example.tuttifrutti.GAMESETTINGS";
    public final static String STOP_NOTIFICATION_DATA = "com.example.tuttifrutti.STOP_NOTIFICATION_DATA";
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    public final static String SENDER_ID = "630267112121";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCMDemo";

    // -2: notStarted
    // -1: noPreviousRounds
    // 1: previousRoundsAvailable
    public final static int GAME_STATUS_CODE_NOT_STARTED = -2;
    public final static int GAME_STATUS_CODE_NO_PREV_ROUNDS = -1;
    public final static int GAME_STATUS_CODE_RESULTS_AVAILABLE = 1;
}
