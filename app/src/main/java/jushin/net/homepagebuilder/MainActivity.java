package jushin.net.homepagebuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        intent.setClassName(this, "jushin.net.homepagebuilder.PutObjectActivity");
        startActivity(intent);
    }

}
