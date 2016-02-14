package johnny.hope.inc.englishkids;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import johnny.hope.inc.englishkids.ui.MainActivity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        findViewById(R.id.btn_setting).setOnClickListener(this);
        findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                MainActivity.open(this);
                break;
            case R.id.btn_setting:
                Toast.makeText(this, "Comming soon!", Toast.LENGTH_SHORT).show();//TODO
                break;
        }
    }
}
