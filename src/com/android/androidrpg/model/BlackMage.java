package com.android.androidrpg.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import android.util.Log;

public class BlackMage
{
  private Bitmap bitmap;  // the actual bitmap
  private int x;          // the X coordinate
  private int y;          // the Y coordinate
  private boolean touched;    // if droid is touched/picked up

  private static final String TAG = BlackMage.class.getSimpleName();

  public BlackMage(Bitmap bitmap, int x, int y)
  {
    this.bitmap = bitmap;
    this.x = x;
    this.y = y;
  }

  public Bitmap getBitmap()
  {
    return bitmap;
  }
  public void setBitmap(Bitmap bitmap)
  {
    this.bitmap = bitmap;
  }

  public int getX()
  {
    return x;
  }
  public void setX(int x)
  {
    this.x = x;
  }

  public int getY()
  {
    return y;
  }
  public void setY(int y)
  {
    this.y = y;
  }

  public boolean isTouched()
  {
    return touched;
  }
  public void setTouched(boolean touched)
  {
    this.touched = touched;
  }

  public void draw(Canvas canvas)
  {
    canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
  }

  public void handleActionDown(int eventX, int eventY)
  {
    if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2)))
    {
      if (eventY >= (y - bitmap.getHeight() / 2) && (eventY <= (y + bitmap.getHeight() / 2)))
      {
        Log.d(TAG, "touched!");
        // droid touched
        setTouched(true);
      }
      else
      {
        setTouched(false);
      }
    }
    else
    {
      setTouched(false);
    }
  }
}
