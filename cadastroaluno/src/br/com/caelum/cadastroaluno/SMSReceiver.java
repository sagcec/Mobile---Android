package br.com.caelum.cadastroaluno;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();

		Object msg[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[msg.length];

		for (int i = 0; i < msg.length; i++) {
			smsMessage[i] = SmsMessage.createFromPdu((byte[]) msg[i]);
		}

		MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
		mp.start();

		Toast.makeText(context, "sms", Toast.LENGTH_LONG).show();
	}

}