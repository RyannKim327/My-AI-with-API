package mpop.revii.ai;
 
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getActionBar().setIcon(AI.setResources(this, "ic_launcher", "drawable"));
		getActionBar().setTitle("Bebe ang AI");
		getActionBar().setSubtitle("Developed by RyannKim327");
		setContentView(R.layout.activity_main);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			// NotificationChannel notip = new NotificationChannel("mpop.revii.notif", "MPOP AI", NotificationManager.IMPORTANCE_DEFAULT);
			// NotificationManager manage = getSystemService(NotificationManager.class);
			// manage.createNotificationChannel(notip);
			Notification.Builder notif = new Notification.Builder(this, "mpop.revii.ai.notif");
			notif.setContentTitle("Bebe ang AI");
			notif.setContentText("Click to subscribe");

		}
		Intent i = new Intent(this, Overlay.class);
		i.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
		startService(i);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
	}
}
