package dodeunifront.dodeuni.map;

import android.content.Intent;
import android.util.Log;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import dodeunifront.dodeuni.map.view.LocationDetailActivity;

public class MarkerEventListener implements MapView.POIItemEventListener {
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        //Log.d("MARKER", "onCalloutBalloonOfPOIItemTouched tag: " + mapPOIItem.getTag());
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        //Log.d("MARKER", "onCalloutBalloonOfPOIItemTouched tag: " + mapPOIItem.getTag());
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        //Log.d("MARKER", "onDraggablePOIItemMoved tag: " + mapPOIItem.getTag());
    }
}
