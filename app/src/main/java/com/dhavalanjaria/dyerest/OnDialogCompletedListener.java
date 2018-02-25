package com.dhavalanjaria.dyerest;

/**
 * Created by Dhaval Anjaria on 2/26/2018.
 */

/**
 * This listener provides a callback method for EditDialogFragment. This listener must be passed to
 * the newInstance method of EditDialogFragment. The onDialogComplete function in this listener is
 * called by the fragment.
 */
public interface OnDialogCompletedListener {
    void onDialogComplete(String text);
}
