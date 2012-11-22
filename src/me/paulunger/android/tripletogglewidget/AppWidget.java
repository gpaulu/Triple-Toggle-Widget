package me.paulunger.android.tripletogglewidget;

import me.paulunger.android.tripletogglewidget.R;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.RemoteViews;
/*
 * credit Donn Felker - This a modified version of his widget with extra functionality
 * see https://github.com/donnfelker/Silent-Mode-Toggle for original source
 */
public class AppWidget extends AppWidgetProvider {
	final static String TAG="SilentAppWidget";
	final static String UPDATE_VIEW="UpdateAppWidgetRemoteView";
	@Override
	public void onReceive(Context context, Intent intent){
		if(intent.getAction().equals(UPDATE_VIEW)){
			context.startService(new Intent(context, ToggleService.class));
		}else if(intent.getAction().equals(AudioManager.RINGER_MODE_CHANGED_ACTION)){
			context.startService(new Intent(context, UpdateService.class));
		}
		else{
			super.onReceive(context, intent);
		}
	}
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		context.startService(new Intent(context, InitService.class));
	}
	public static class ToggleService extends IntentService{
		public ToggleService(){
			super("AppWidget$ToggleService");
		}
		@Override
		protected void onHandleIntent(Intent intent){
			ComponentName me=new ComponentName(this, AppWidget.class);
			AppWidgetManager mgr=AppWidgetManager.getInstance(this);
			mgr.updateAppWidget(me, buildUpdate(this));
		}
		private RemoteViews buildUpdate(Context context){
			RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.widget);
			AudioManager audioManager=(AudioManager)context.getSystemService(Activity.AUDIO_SERVICE);
			
			if(audioManager.getRingerMode()==AudioManager.RINGER_MODE_SILENT){
				updateViews.setImageViewResource(R.id.phoneState, R.drawable.sound_on );
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			}else if(audioManager.getRingerMode()==AudioManager.RINGER_MODE_NORMAL){
				updateViews.setImageViewResource(R.id.phoneState, R.drawable.vibrate );
				audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			}else{
				updateViews.setImageViewResource(R.id.phoneState, R.drawable.silent );
				audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			}
			
			Intent i=new Intent(this,AppWidget.class);
			i.setAction(UPDATE_VIEW);
			PendingIntent pi=PendingIntent.getBroadcast(context, 0, i, 0);
			updateViews.setOnClickPendingIntent(R.id.phoneState, pi);
			return updateViews;
		}
	}
	
	public static class UpdateService extends IntentService{
		public UpdateService(){
			super("AppWidget$UpdateService");
		}
		@Override
		protected void onHandleIntent(Intent intent){
			ComponentName me=new ComponentName(this, AppWidget.class);
			AppWidgetManager mgr=AppWidgetManager.getInstance(this);
			mgr.updateAppWidget(me, buildUpdate(this));
		}
		private RemoteViews buildUpdate(Context context){
			RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.widget);
			AudioManager audioManager=(AudioManager)context.getSystemService(Activity.AUDIO_SERVICE);
			
			if(audioManager.getRingerMode()==AudioManager.RINGER_MODE_SILENT){
				updateViews.setImageViewResource(R.id.phoneState, R.drawable.silent );
			}else if(audioManager.getRingerMode()==AudioManager.RINGER_MODE_NORMAL){
				updateViews.setImageViewResource(R.id.phoneState, R.drawable.sound_on );
			}else{
				updateViews.setImageViewResource(R.id.phoneState, R.drawable.vibrate );
			}
			Intent i=new Intent(this,AppWidget.class);
			i.setAction(UPDATE_VIEW);
			PendingIntent pi=PendingIntent.getBroadcast(context, 0, i, 0);
			updateViews.setOnClickPendingIntent(R.id.phoneState, pi);
			return updateViews;
		}
	}
	public static class InitService extends IntentService{
		public InitService(){
			super("AppWidget$UpdateService");
		}
		@Override
		protected void onHandleIntent(Intent intent){
			ComponentName me=new ComponentName(this, AppWidget.class);
			AppWidgetManager mgr=AppWidgetManager.getInstance(this);
			mgr.updateAppWidget(me, buildUpdate(this));
		}
		private RemoteViews buildUpdate(Context context){
			RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.widget);
			AudioManager audioManager=(AudioManager)context.getSystemService(Activity.AUDIO_SERVICE);
			
			if(audioManager.getRingerMode()==AudioManager.RINGER_MODE_SILENT){
				updateViews.setImageViewResource(R.id.phoneState, R.drawable.silent );
			}else if(audioManager.getRingerMode()==AudioManager.RINGER_MODE_NORMAL){
				updateViews.setImageViewResource(R.id.phoneState, R.drawable.sound_on );
			}else{
				updateViews.setImageViewResource(R.id.phoneState, R.drawable.vibrate );
			}
			
			Intent i=new Intent(this,AppWidget.class);
			i.setAction(UPDATE_VIEW);
			PendingIntent pi=PendingIntent.getBroadcast(context, 0, i, 0);
			updateViews.setOnClickPendingIntent(R.id.phoneState, pi);
			return updateViews;
		}
	}
	
}
