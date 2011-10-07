package com.freeworks.kukuri.util;

  

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;  
  
import javax.microedition.khronos.opengles.GL10;  
  
  
  
import android.content.Context;
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.graphics.Rect;  
import android.graphics.RectF;  
import android.opengl.GLUtils;  
  
/** 
 * スプライトクラス 
 */  
public class SpriteTexture {  
 public final static int LOAD_ERROR = -1; // 読み込みエラーメッセージ  
   
 final static float[] TEXCOORDS = { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f }; // UV座標  
  
 final static FloatBuffer TEXCOORD_BUFFER = getFloatBuffer(TEXCOORDS);   // UVバッファ  
 static GL10 gl;
 public int index; // イメージバインドID  
 Bitmap image;  // ビットマップイメージ  
 int width, height; // 画像サイズ  
  Context context;
   
 public float scale;  // 拡大値  
 public float angle;  // 回転値  
  
 
 public SpriteTexture(GL10 _gl) {  
  index = LOAD_ERROR;  
  image = null;    
  scale = 1;  
  angle = 0;  
  gl =_gl;
 }  
   
 /** 
  * 開放処理 
  */  
 public void release() {  
  int[] textures = {index};  
  gl.glDeleteTextures(1, textures, 0);  
  image = null;  
 }  
   
   
   
 /** 
  * スプライトの作成 
  * @param bitmap 
  * @return 作成されたスプライトデータ 
  */  
 public static SpriteTexture create(Bitmap bitmap,GL10 _gl) {  
  SpriteTexture sprite = new SpriteTexture(_gl);  
    
  // テクスチャIDの設定  
  sprite.index = setTextureID();  
    
  // ビットマップイメージの設定  
  GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);  
  sprite.image = bitmap;  
  sprite.image.recycle();  
    
  // 画像サイズの取得  
  sprite.width = bitmap.getWidth();  
  sprite.height = bitmap.getHeight();  
          
  return sprite;  
 }  
   
 /** 
  * テクスチャIDの設定 
  * @return テクスチャID 
  */  
 static int setTextureID() {  
  int[] ids = { LOAD_ERROR };  
    
  // 使用テクスチャ数の設定  
  gl.glGenTextures(1, ids, 0);  
    
  // IDをバインド  
  gl.glBindTexture(GL10.GL_TEXTURE_2D, ids[0]);  
    
  // パラメータ設定  
  gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT );  
  gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT );  
  gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR );  
  gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );  
    
  // ポリゴン色とテクスチャ色の合成方法  
  gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);  
    
  return ids[0];  
 }  
   
 /** 
  * 描画 
  * @param x X座標 
  * @param y Y座標 
  */  
 public void draw(int x, int y,int screen_height) {          
  // テクスチャをバインド  
  gl.glBindTexture(GL10.GL_TEXTURE_2D, index);  
  gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
  gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
  
  // UV座標  
  setTexture();  
    
  // 頂点カラー  
  gl.glColor4f(1.0f,1.0f,1.0f,1.0f);  
    
  // 頂点バッファ  
  float[] vertices = getVertices(x, screen_height - y, getWidth(), getHeight());  
  gl.glVertexPointer(3, GL10.GL_FLOAT, 0, getFloatBuffer(vertices));  
    
  // 描画    
  gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
  gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
  gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
 }  
   

public static FloatBuffer getFloatBuffer(float vertex[])
{
	  ByteBuffer vbb = ByteBuffer.allocateDirect(vertex.length * 4);
	  vbb.order(ByteOrder.nativeOrder());
	  FloatBuffer vertexBuffer = vbb.asFloatBuffer();
	  vertexBuffer.put(vertex);
	  vertexBuffer.position(0);
	  return vertexBuffer;
}
 /** 
  * UV座標の設定 
  */  
 public void setTexture() {  
  gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, TEXCOORD_BUFFER);  
 }  
   
 
   
 /** 
  * 座標変換頂点バッファの取得（画像幅指定） 
  * @param x X座標 
  * @param y Y座標 
  * @param width 横幅 
  * @param height 縦幅 
  * @return 頂点バッファ 
  */  
 final float[] getVertices(int x, int y, int width, int height) {  
  // 座標系変換用  
  int px = width >> 1;  
  int py = height >> 1;  
  
  // 頂点バッファ  
  float[] vertices = {  
   x, y, 0,  
   x + width, y, 0,  
   x, y + height, 0,  
   x + width, y + height, 0,  
  };  
  
/*  for(int i = 0; i < 12; i += 3) {  
   vertices[i] -= x + px;  
   vertices[i + 1] -= y + py;  
  
   // 拡大  
   vertices[i] *= scale;  
   vertices[i + 1] *= scale;  
  
   // 回転  
   float tx = vertices[i];  
   float ty = vertices[i + 1];  
   vertices[i] = (float) (Math.cos(Math.PI*angle/180.0f) * tx - Math.sin(Math.PI*angle/180.0f) * ty);  
   vertices[i + 1] = (float) (Math.sin(Math.PI*angle/180.0f) * tx + Math.cos(Math.PI*angle/180.0f) * ty);  
  
   // 移動  
   vertices[i] += x + px;  
   vertices[i + 1] += y - py;  
  }  */
  
  return vertices;  
 }  
   
 /** 
  * 横幅取得 
  * @return 
  */  
 public int getWidth() {  
  return width;  
 }  
   
 /** 
  * 縦幅取得 
  * @return 
  */  
 public int getHeight() {  
  return height;  
 }  
   
 /** 
  * 範囲取得 
  * @return 
  */  
 public Rect getRect() {  
  return new Rect(0, 0, width, height);  
 }  
} 
