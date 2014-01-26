package br.com.caelum.cadastroaluno;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Indicador extends View implements OnTouchListener {

	private static final float MAX_TELA = 200;
	private static final float MAX_ALTURA = 30;

	private float x;

	public Indicador(Context context, AttributeSet attrs) {
		super(context, attrs);

		setMinimumHeight((int) MAX_ALTURA);
		setMinimumWidth((int) MAX_TELA);

		setOnTouchListener(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(getSuggestedMinimumWidth(), getMeasuredHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.CYAN);
		Paint p = new Paint();
		p.setColor(Color.RED);
		RectF r = new RectF(0, 0, x, MAX_ALTURA);
		canvas.drawRect(r, p);
	}

	public double getValor() {
		return x * 10 / MAX_TELA;
	}

	public void setValor(double valor) {
		this.x = (float) (MAX_TELA * valor / 10);
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.x = event.getX();
			invalidate();

			break;
		default:
			return false;
		}

		return false;
	}

}