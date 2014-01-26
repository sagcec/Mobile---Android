package br.com.achei.control.util;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;

public class AparelhoUtil {

	public String getMacAddress(Context context) {
		WifiManager wimanager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		String macAddress = wimanager.getConnectionInfo().getMacAddress();

		if (macAddress == null) {
			macAddress = "Device don't have mac address or wi-fi is disabled";
		}

		return macAddress;
	}

	public static String getDeviceId(Activity act) {
		String id = Secure.getString(act.getContentResolver(),
				Secure.ANDROID_ID);

		return id;
	}

}