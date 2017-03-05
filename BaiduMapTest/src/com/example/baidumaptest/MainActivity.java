package com.example.baidumaptest;

import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {
	private MapView mapview;
	private BaiduMap baidumap;
	private LocationManager manager;
	private String provider;
	private boolean isFirstLocate=true;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mapview=(MapView)findViewById(R.id.map_view);
//		UserLocationFound found=new UserLocationFound(this);
//		double lon=found.getLongitude();
//		double lat=found.getLatitude();
		
		baidumap=mapview.getMap();
		baidumap.setMyLocationEnabled(true);
		manager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		List<String>providerlist=manager.getProviders(true);
		if(providerlist.contains(LocationManager.GPS_PROVIDER)){
			provider=LocationManager.GPS_PROVIDER;
			Toast.makeText(this,"GPS��λ!",Toast.LENGTH_SHORT).show();
			
		}
		else if(providerlist.contains(LocationManager.NETWORK_PROVIDER)){
			provider=LocationManager.NETWORK_PROVIDER;
			Toast.makeText(this,"���綨λ!",Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this,"no provider can be used!",Toast.LENGTH_SHORT).show();
			return;
		}
		
		Location location=manager.getLastKnownLocation(provider);
		if(location!=null){
			ShowLocation(location);
		}
		else{
			Toast.makeText(this,"��ȡλ��ʧ��",Toast.LENGTH_SHORT).show();
		}
		
		manager.requestLocationUpdates(provider, 5000, 1, locationListener);
		
		
		LatLng ll=new LatLng(32.33746,119.392496);// ��ȡά�ȣ�����
		MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(ll);
		baidumap.animateMapStatus(update);
		update=MapStatusUpdateFactory.zoomTo(16f);
		baidumap.animateMapStatus(update);
		
		
		MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
		locationBuilder.latitude(32.33746);
		locationBuilder.longitude(119.392496);
		MyLocationData data=locationBuilder.build();
	    baidumap.setMyLocationData(data);
		
		
	}
	

	
	private void ShowLocation(Location location2) {
		Toast.makeText(this, "λ�û�ȡ�ɹ�", Toast.LENGTH_SHORT).show();
		// TODO �Զ����ɵķ������
		
	}


	LocationListener locationListener=new LocationListener(){

		@Override
		public void onLocationChanged(Location location) {
			if(location!=null){
				navigateTo(location);
			}
			// TODO �Զ����ɵķ������
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO �Զ����ɵķ������
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO �Զ����ɵķ������
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO �Զ����ɵķ������
			
		}
		
	};
	
	
	
	
	
	
	
	private void navigateTo(Location location) {
		if(isFirstLocate){
			
			LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());// ��ȡά�ȣ�����
			MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(ll);
			baidumap.animateMapStatus(update);
			update=MapStatusUpdateFactory.zoomTo(16f);
			baidumap.animateMapStatus(update);
			isFirstLocate=false;
		}
		MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
		locationBuilder.latitude(location.getLatitude());
		locationBuilder.longitude(location.getLongitude());
		MyLocationData data=locationBuilder.build();
	    baidumap.setMyLocationData(data);
		
		// TODO �Զ����ɵķ������
		
	}










	@Override
	protected void onResume() {
		// TODO �Զ����ɵķ������
		super.onResume();
		mapview.onResume();
	}










	@Override
	protected void onPause() {
		// TODO �Զ����ɵķ������
		super.onPause();
		mapview.onPause();
	}










	@Override
	protected void onDestroy() {
		// TODO �Զ����ɵķ������
		super.onDestroy();
		mapview.onDestroy();
		baidumap.setMyLocationEnabled(false);
		if(manager!=null){
			manager.removeUpdates(locationListener);
		}
	}










	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}