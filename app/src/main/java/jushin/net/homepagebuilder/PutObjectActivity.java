package jushin.net.homepagebuilder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;


public class PutObjectActivity extends Activity {

    ScrollView scroll;
    RelativeLayout layout;
    Button addButton;
    EditText countEdit;

    Button addActionButton;
    Button removeActionButton;

    public static int count = 0;

    boolean scrollStop = false;

    View.OnTouchListener movingListener = new View.OnTouchListener() {

        private float downX;
        private float downY;

        private int downLeftMargin;
        private int downTopMargin;

        public int testFor;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            final ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams)v.getLayoutParams();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                scrollStop = true;
                downX = event.getRawX();
                downY = event.getRawY();
                downLeftMargin = param.leftMargin;
                downTopMargin = param.topMargin;
                Log.d("action_down", String.format("X:%4d Y:%4d",downLeftMargin, downTopMargin));
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                param.leftMargin = downLeftMargin + (int)(event.getRawX() - downX);
                param.topMargin = downTopMargin + (int)(event.getRawY() - downY);
                v.layout(param.leftMargin, param.topMargin, param.leftMargin + v.getWidth(), param.topMargin + v.getHeight());
                Log.d("action_move", String.format("X:%4d Y:%4d", downLeftMargin, downTopMargin));
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                scrollStop = false;
                Log.d("action_up", String.format("X:%4d Y:%4d", downLeftMargin, downTopMargin));
                return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_object);

        scroll = (ScrollView)findViewById(R.id.scrollView1);
        layout = (RelativeLayout)findViewById(R.id.setLayout);

        countEdit = (EditText)findViewById(R.id.countEdit);
        addButton = (Button)findViewById(R.id.buttonAdd);

        addActionButton = (Button)findViewById(R.id.addTouch);
        removeActionButton = (Button)findViewById(R.id.removeTouch);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = new Button(PutObjectActivity.this);
                btn.setOnTouchListener(movingListener);
                btn.setText(Integer.toString(++count));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btn = ((Button) v);
                        Toast.makeText(PutObjectActivity.this, btn.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                layout.addView(btn);
            }
        });

        addButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                layout.removeAllViews();
                count = 0;

                try {
                    Integer.parseInt(countEdit.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(PutObjectActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return false;
                }

                for (int i = 0; i < Integer.parseInt(countEdit.getText().toString()); i++) {
                    Button btn = new Button(PutObjectActivity.this);
                    btn.setOnTouchListener(movingListener);
                    btn.setText(Integer.toString(++count));
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button btn = ((Button) v);
                            Toast.makeText(PutObjectActivity.this, btn.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    layout.addView(btn);
                }
                return false;
            }
        });

        addActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < layout.getChildCount(); i++) {
                    layout.getChildAt(i).setOnTouchListener(movingListener);
                }
            }
        });

        removeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < layout.getChildCount(); i++) {
                    layout.getChildAt(i).setOnTouchListener(null);
                }
            }
        });

        removeActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                scrollStop = true;
                return false;
            }
        });

        addActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                scrollStop = false;
                return false;
            }
        });

        scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return scrollStop;
            }
        });

    }
}
