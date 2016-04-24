package com.termux.api;

import java.io.PrintWriter;

import android.content.Intent;
import android.telephony.SmsManager;

import com.termux.api.util.ResultReturner;
import com.termux.api.util.TermuxApiLogger;

public class SmsSendAPI {

    static void onReceive(TermuxApiReceiver apiReceiver, final Intent intent) {
        ResultReturner.returnData(apiReceiver, intent, new ResultReturner.WithStringInput() {
            @Override
            public void writeResult(PrintWriter out) throws Exception {
                final SmsManager smsManager = SmsManager.getDefault();
                String[] recipients = intent.getStringArrayExtra("recipients");

                if (recipients == null) {
                    // Used by old versions of termux-send-sms.
                    String recipient = intent.getStringExtra("recipient");
                    if (recipient != null) recipients = new String[]{recipient};
                }

                if (recipients == null || recipients.length == 0) {
                    TermuxApiLogger.error("No recipient given");
                } else {
                    for (String recipient : recipients) {
                        smsManager.sendTextMessage(recipient, null, inputString, null, null);
                    }
                }
            }
        });
    }

}
