package com.alaer.lib.event;

import de.greenrobot.event.EventBus;

public class EventUtil {

    private static final int EVENT_TOKEN_INVALID = 1;
    private static final int EVENT_INSTALL_PERMISSION_REQUEST = 2;
    private static final int EVENT_INSTALL_APK = 3;
    /////////////////////////////////////////////////////////////////////////

    public static void sendTokenInvalid() {
        Event event = Event.create(EVENT_TOKEN_INVALID);
        EventBus.getDefault().post(event);
    }

    public static boolean isTokenInvalid(Event event) {
        return event != null && event.type == EVENT_TOKEN_INVALID;
    }

    public static void sendInstallRequestPermission(Object target) {
        Event event = Event.create(EVENT_INSTALL_PERMISSION_REQUEST, target);
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
