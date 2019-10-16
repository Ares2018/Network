package com.core.network;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;

/**
 * FragmentLifecycle
 *
 * @author a_liYa
 * @date 2019-10-16 11:28.
 */
class FragmentLifecycle {

    private static LifecycleCallbacks sLifecycleCallbacks;
    private static SupportLifecycleCallbacks sSupportLifecycleCallbacks;
    private static Boolean sSupportLibraryContains;

    public static void register(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (sLifecycleCallbacks == null) sLifecycleCallbacks = new LifecycleCallbacks();
                activity.getFragmentManager()
                        .registerFragmentLifecycleCallbacks(sLifecycleCallbacks, true);
            }
        } catch (Exception e) {
            // ignore it.
        }
        try {
            if (sSupportLibraryContains == null) {
                try {
                    Class.forName("android.support.v4.app.FragmentActivity");
                    sSupportLibraryContains = Boolean.TRUE;
                } catch (ClassNotFoundException e) {
                    sSupportLibraryContains = Boolean.FALSE;
                }
            }
            if (sSupportLibraryContains) {
                if (activity instanceof FragmentActivity) {
                    if (sSupportLifecycleCallbacks == null)
                        sSupportLifecycleCallbacks = new SupportLifecycleCallbacks();
                    ((FragmentActivity) activity).getSupportFragmentManager()
                            .registerFragmentLifecycleCallbacks(sSupportLifecycleCallbacks, true);
                }
            }
        } catch (Exception e) {
            // ignore it.
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static class LifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {

        @Override
        public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
            super.onFragmentViewDestroyed(fm, f);
            ApiCallManager.get().cancel(f);
        }
    }

    private static class SupportLifecycleCallbacks extends
            android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks {

        @Override
        public void onFragmentViewDestroyed(android.support.v4.app.FragmentManager fm,
                                            android.support.v4.app.Fragment f) {
            super.onFragmentViewDestroyed(fm, f);
            ApiCallManager.get().cancel(f);
        }
    }

}
