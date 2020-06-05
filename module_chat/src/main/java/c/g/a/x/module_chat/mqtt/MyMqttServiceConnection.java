package c.g.a.x.module_chat.mqtt;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class MyMqttServiceConnection implements ServiceConnection {


//        startService(new Intent(this, MyMqttService .class));
//        stopService(new Intent(this, MyMqttService .class));
//
//        bindService(new Intent(this, MyMqttService .class),conn,Service.BIND_AUTO_CREATE);
//        unbindService(conn);

    public MyMqttService mService;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mService = ((MyMqttService.LocalBinder) service).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mService = null;
    }
}

