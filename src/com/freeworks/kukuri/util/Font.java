package com.freeworks.kukuri.util;

import android.graphics.Bitmap;  
import android.graphics.Bitmap.Config;  
import android.graphics.Canvas;  
import android.graphics.Paint;  
import android.graphics.Paint.FontMetrics;  
import com.freeworks.kukuri.util.SpriteTexture;  
import javax.microedition.khronos.opengles.GL10; 
/** 
 * フォントクラス 
 */  
public class Font {  
 final static Paint paint = new Paint(); // 描画情報  
 static FontMetrics font = paint.getFontMetrics(); // フォントメトリクス  
  
 /** 
  * コンストラクタ 
  * インスタンス化禁止 
  */  
 Font() {}  
  
 /** 
  * 描画 
  * @param text 
  * @param x 
  * @param y 
  */  
 public static void draw(String text, int x, int y,int screen_height,GL10 gl) {  
  // 文字サイズの取得  
    
  int[] size = { getWidth(text), getHeight() };  
    
  int[] power_size ={ 2, 2 };  
   
  for(int i = 0; i < size.length; i++) {  
	  		while(power_size[i] < size[i]) {  
	  				power_size[i] <<= 1;  
	  		}  
  }  
 
  //キャンバスとビットマップを共有化  
  Bitmap image = Bitmap.createBitmap(power_size[0], power_size[1], Config.ARGB_8888);  
  Canvas canvas = new Canvas(image);
    
  
  // 文字列をキャンバスに描画  
  paint.setAntiAlias(true);  
  canvas.drawText(text, 0, Math.abs(font.ascent), paint);  
  
  // キャンバスデータからスプライトを作成  
  SpriteTexture sprite = SpriteTexture.create(image,gl);  
  
  // スプライトの描画  
  sprite.draw(x, y,screen_height);
  
  // 開放  
  sprite.release();  
  sprite = null;  
  canvas = null;  
 }  
  
 /** 
  * 文字列色の設定 
  * @param color 
  */  
 public static void setColor(int color) {  
  paint.setColor(color);  
 }  
  
 /** 
  * 文字サイズの設定 
  * @param size 
  */  
 public static void setSize(int size) {  
  paint.setTextSize(size);  
  font = paint.getFontMetrics(); // 文字サイズ更新後のメトリクスを取得  
 }  
  
 /** 
  * 文字列の幅の取得 
  * @param text 
  * @return 文字列の幅 
  */  
 public static int getWidth(String text) {  
  return (int) (paint.measureText(text) + 0.5f);  
 }  
  
 /** 
  * 文字列の高さを取得 
  * @return 文字列の高 
  */  
 public static int getHeight() {  
  return (int) (Math.abs(font.top) + Math.abs(font.bottom) + 0.5f);  
 }  
}  