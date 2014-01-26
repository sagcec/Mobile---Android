package br.com.caelum.cadastroaluno;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class Map extends MapActivity {

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.mapa);

		MapView mapview = (MapView) findViewById(R.id.map_view);
		MapController mapCont = mapview.getController();

		mapview.setSatellite(true);
		mapview.setStreetView(true);
		mapview.displayZoomControls(true);

		mapCont.setZoom(14);

		List over = mapview.getOverlays();

		MyLocationOverlay my = new MyLocationOverlay(this, mapview);
		over.add(my);
		my.enableCompass();
		my.enableMyLocation();

		LocationManager loc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria crit = new Criteria();
		crit.setAccuracy(Criteria.ACCURACY_COARSE);
		crit.setAltitudeRequired(false);
		crit.setCostAllowed(true);
		crit.setPowerRequirement(Criteria.POWER_HIGH);
		String prov = loc.getBestProvider(crit, true);

		Location l = loc.getLastKnownLocation("gps");
	}

	void atualizarMeuLocal(Location l) {
		if (l != null) {
			Double geoLat = l.getLatitude() * 1E6;
			Double geoLong = l.getLongitude() * 1E6;
			GeoPoint point = new GeoPoint(geoLat.intValue(), geoLong.intValue());

		}
	}

}