package jp.ac.gifu_u.info.magane.myapplication0514;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_LIGHT);
        // 一つ以上見つかったら最初のセンサを取得してリスナーに登録
        if (sensors.size() != 0) {
            Sensor sensor = sensors.get(0);
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 一時停止の際にリスナー登録を解除
        manager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 必要に応じて実装
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 明るさセンサが変化したとき
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            // 明るさの値（単位：ルクス）を取得
            float intensity = event.values[0];
            // 結果をテキストとして表示
            String str = Float.toString(intensity) + " ルクス";
            TextView textView = findViewById(R.id.status_text);
            textView.setText(str);
        }
    }
}
