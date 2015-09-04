package main.bindroid.sdattendance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by Dev.Dhar on 04/09/15.
 */
public class FloorView extends ImageView {

	double y = 10;
	double x = 50;

	public FloorView(Context context) {
		super(context);

	}

	public FloorView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FloorView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint mPaint = new Paint();
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.loc_icon);
		canvas.drawBitmap(bitmap, getImageLocX(),
				getImageLocY() - bitmap.getHeight(), mPaint);

	}

	public void setImageLocX(double x) {
		this.x = x;

	}

	public void setImageLocY(double y) {
		this.y = y;

	}

	public float getImageLocX() {
		return (float) x;
	}

	public float getImageLocY() {
		return (float) y;
	}

	public void setLocation(Location loc) {
		this.loc = loc;
	}

	private Location loc;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return super.onTouchEvent(event);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		postDelayed(new Runnable() {

			@Override
			public void run() {
				double ratioX = (double) getMeasuredWidth() / (double) 468;
				double ratioY = (double) getMeasuredHeight() / (double) 469;
				setImageLocX(loc.getX() * ratioX - 30);
				setImageLocY(loc.getY() * ratioY - 15);
				invalidate();
			}
		}, 1000);
	}
}
