package com.android.androidrpg;

import com.android.androidrpg.model.BlackMage;
import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class GameWorld extends SurfaceView implements SurfaceHolder.Callback {

  private MainThread thread;
  private BlackMage black_mage;

  private static final String TAG = GameWorld.class.getSimpleName();

  public GameWorld(Context context) {
    super(context);
    // adding the callback (this) to the surface holder to intercept events
    getHolder().addCallback(this);

    // Create black mage
    black_mage = new BlackMage(BitmapFactory.decodeResource(getResources(), R.drawable.black_mage), 150, 150);

    // Create main game loop thread
    thread = new MainThread(getHolder(), this);

    // make the GamePanel focusable so it can handle events
    setFocusable(true);
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    thread.setRunning(true);
    thread.start();
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    boolean retry = true;
    while (retry) {
      try {
        thread.join();
        retry = false;
      }
      catch (InterruptedException e) {
        // try again shutting down the thread
      }
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    Log.d(TAG, Integer.toString(event.getAction()));
    if (event.getAction() == MotionEvent.ACTION_DOWN)
    {
      // Tell BM we are moving him
      black_mage.handleActionDown((int)event.getX(), (int)event.getY());

      if (event.getY() > getHeight() - 50)
      {
        thread.setRunning(false);
        ((Activity)getContext()).finish();
      }
      else
      {
        Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
      }
    }
    else if (event.getAction() == MotionEvent.ACTION_MOVE)
    {
      Log.d(TAG, "Dragging...");
      if (black_mage.isTouched())
      {
        black_mage.setX((int)event.getX());
        black_mage.setY((int) event.getY());
      }
    }
    else if (event.getAction() == MotionEvent.ACTION_UP)
    {
      if (black_mage.isTouched())
      {
        black_mage.setTouched(false);
      }
    }
    return true;
    //return super.onTouchEvent(event);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawColor(Color.BLACK);
    black_mage.draw(canvas);
  }
}
