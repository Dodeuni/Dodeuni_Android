package dodeunifront.dodeuni.map;

import android.util.Log;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MarkerEventListener implements MapView.POIItemEventListener {
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        Log.d("MARKER", "onPOIItemSelected tag: " + mapPOIItem.getTag());
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        Log.d("MARKER", "onCalloutBalloonOfPOIItemTouched tag: " + mapPOIItem.getTag());
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        Log.d("MARKER", "onCalloutBalloonOfPOIItemTouched tag: " + mapPOIItem.getTag());
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        Log.d("MARKER", "onDraggablePOIItemMoved tag: " + mapPOIItem.getTag());
    }
}
