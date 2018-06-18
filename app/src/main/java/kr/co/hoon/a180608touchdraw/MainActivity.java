package kr.co.hoon.a180608touchdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // 선들을 그릴 정보를 가진 ArrayList 변수
    ArrayList<Vertex> list;

    class MyCustomView extends View {
        Paint paint;

        public MyCustomView(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStrokeWidth(2);
            paint.setAntiAlias(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            // 리스트를 순회하면서 선 그리기
            for(int i = 0; i<list.size(); i=i+1){
                if(list.get(i).isDraw){
                    // 그리기모드가 true일 때만 이전위치의 점과 선을 그리기
                    canvas.drawLine(list.get(i-1).x, list.get(i-1).y, list.get(i).x, list.get(i).y, paint);
                }
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                list.add(new Vertex(event.getX(), event.getY(), false));
                return true;
            }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                list.add(new Vertex(event.getX(), event.getY(), true));
                // 선을 다시 그리도록 무효화 - onDraw()가 다시 호출됨
                invalidate();
                return true;
            }
            return super.onTouchEvent(event);
        }
    }

    // 좌표와 그리기모드를 저장한느 VO클래스
    class Vertex{
        public float x;
        public float y;
        public boolean isDraw;

        public Vertex(float x, float y, boolean isDraw) {
            this.x = x;
            this.y = y;
            this.isDraw = isDraw;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        MyCustomView myCustomView = new MyCustomView(this);
        setContentView(myCustomView);

        list = new ArrayList<>();
    }
}
