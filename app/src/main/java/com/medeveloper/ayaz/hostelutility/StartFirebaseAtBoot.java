
package com.medeveloper.ayaz.hostelutility;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.medeveloper.ayaz.hostelutility.MyService;

/**
 * Start the service when the device boots.
 *
 * @author vikrum
 *
 */
public class StartFirebaseAtBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(MyService.class.getName()));
    }
}