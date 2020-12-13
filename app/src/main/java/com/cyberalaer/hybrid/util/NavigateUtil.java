package com.cyberalaer.hybrid.util;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class NavigateUtil {

    public static void navigate(Fragment fragment, int actionId) {
        NavHostFragment.findNavController(fragment).navigate(actionId);
    }

}
