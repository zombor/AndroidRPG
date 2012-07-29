package com.android.androidrpg;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import android.app.Activity;

public class GameWorld extends SurfaceView implements SurfaceHolder.Callback {

  private MainThread thread;

  private static final String TAG = GameWorld.class.getSimpleName();

  public GameWorld(Context context) {
    super(context);
    // adding the callback (this) to the surface holder to intercept events
    getHolder().addCallback(this);

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
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      if (event.getY() > getHeight() - 50) {
        thread.setRunning(false);
        ((Activity)getContext()).finish();
      } else {
        Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
      }
    }
    return super.onTouchEvent(event);
  }

  @Override
  protected void onDraw(Canvas canvas) {
  }
}
