package com.cyberalaer.hybrid.util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class NavigateUtil {

    public static void navigate(Fragment fragment, int actionId) {
        NavHostFragment.findNavController(fragment).navigate(actionId);
    }

    public static void navigate(Fragment fragment, int actionId, Bundle data) {
        NavHostFragment.findNavController(fragment).navigate(actionId, data);
    }

}
