package com.cyberalaer.hybrid.event;

import de.greenrobot.event.EventBus;

public class EventUtil {

    private static final int EVENT_INSTALL_PERMISSION_REQUEST = 1;
    private static final int EVENT_INSTALL_APK = 2;
    /////////////////////////////////////////////////////////////////////////

    public static void sendInstallRequestPermission() {
        Event event = Event.create(EVENT_INSTALL_PERMISSION_REQUEST);
        EventBus.getDefault().post(event);
    }

    public static boolean isInstallRequestPermission(Event event) {
        return event != null && event.type == EVENT_INSTALL_PERMISSION_REQUEST;
    }

    public static void sendInstallApk() {
        Event event = Event.create(EVENT_INSTALL_APK);
        EventBus.getDefault().post(event);
    }

    public static boolean isInstallApk(Event event) {
        return event != null && event.type == EVENT_INSTALL_APK;
    }

}
