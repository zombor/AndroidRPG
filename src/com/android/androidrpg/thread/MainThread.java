package com.android.androidrpg;

import android.util.Log;
import android.view.SurfaceHolder;
import android.graphics.Canvas;

public class MainThread extends Thread {

  // flag to hold game state
  private boolean running;

  private SurfaceHolder surfaceHolder;
  private GameWorld gameWorld;

  private static final String TAG = MainThread.class.getSimpleName();

  public MainThread(SurfaceHolder surfaceHolder, GameWorld gameWorld)
  {
    super();
    this.surfaceHolder = surfaceHolder;
    this.gameWorld = gameWorld;
  }

  public void setRunning(boolean running) {
    this.running = running;
  }

  @Override
  public void run() {
    Canvas canvas;
    long tickCount = 0L;

    Log.d(TAG, "Starting game loop");

    while (running) {
      tickCount++;
      canvas = null;

      // try locking the canvas for exclusive pixel editing on the surface
      try
      {
        canvas = this.surfaceHolder.lockCanvas();
        synchronized (surfaceHolder)
        {
          this.gameWorld.onDraw(canvas);
        }
      }
      finally
      {
        if (canvas != null)
        {
          surfaceHolder.unlockCanvasAndPost(canvas);
        }
      }
    }

    Log.d(TAG, "Game loop executed " + tickCount + " times");
  }
}
