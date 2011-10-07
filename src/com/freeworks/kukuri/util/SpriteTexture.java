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
 * �X�v���C�g�N���X 
 */  
public class SpriteTexture {  
 public final static int LOAD_ERROR = -1; // �ǂݍ��݃G���[���b�Z�[�W  
   
 final static float[] TEXCOORDS = { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f }; // UV���W  
  
 final static FloatBuffer TEXCOORD_BUFFER = getFloatBuffer(TEXCOORDS);   // UV�o�b�t�@  
 static GL10 gl;
 public int index; // �C���[�W�o�C���hID  
 Bitmap image;  // �r�b�g�}�b�v�C���[�W  
 int width, height; // �摜�T�C�Y  
  Context context;
   
 public float scale;  // �g��l  
 public float angle;  // ��]�l  
  
 
 public SpriteTexture(GL10 _gl) {  
  index = LOAD_ERROR;  
  image = null;    
  scale = 1;  
  angle = 0;  
  gl =_gl;
 }  
   
 /** 
  * �J������ 
  */  
 public void release() {  
  int[] textures = {index};  
  gl.glDeleteTextures(1, textures, 0);  
  image = null;  
 }  
   
   
   
 /** 
  * �X�v���C�g�̍쐬 
  * @param bitmap 
  * @return �쐬���ꂽ�X�v���C�g�f�[�^ 
  */  
 public static SpriteTexture create(Bitmap bitmap,GL10 _gl) {  
  SpriteTexture sprite = new SpriteTexture(_gl);  
    
  // �e�N�X�`��ID�̐ݒ�  
  sprite.index = setTextureID();  
    
  // �r�b�g�}�b�v�C���[�W�̐ݒ�  
  GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);  
  sprite.image = bitmap;  
  sprite.image.recycle();  
    
  // �摜�T�C�Y�̎擾  
  sprite.width = bitmap.getWidth();  
  sprite.height = bitmap.getHeight();  
          
  return sprite;  
 }  
   
 /** 
  * �e�N�X�`��ID�̐ݒ� 
  * @return �e�N�X�`��ID 
  */  
 static int setTextureID() {  
  int[] ids = { LOAD_ERROR };  
    
  // �g�p�e�N�X�`�����̐ݒ�  
  gl.glGenTextures(1, ids, 0);  
    
  // ID���o�C���h  
  gl.glBindTexture(GL10.GL_TEXTURE_2D, ids[0]);  
    
  // �p�����[�^�ݒ�  
  gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT );  
  gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT );  
  gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR );  
  gl.glTexParameterx( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );  
    
  // �|���S���F�ƃe�N�X�`���F�̍������@  
  gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);  
    
  return ids[0];  
 }  
   
 /** 
  * �`�� 
  * @param x X���W 
  * @param y Y���W 
  */  
 public void draw(int x, int y,int screen_height) {          
  // �e�N�X�`�����o�C���h  
  gl.glBindTexture(GL10.GL_TEXTURE_2D, index);  
  gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
  gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
  
  // UV���W  
  setTexture();  
    
  // ���_�J���[  
  gl.glColor4f(1.0f,1.0f,1.0f,1.0f);  
    
  // ���_�o�b�t�@  
  float[] vertices = getVertices(x, screen_height - y, getWidth(), getHeight());  
  gl.glVertexPointer(3, GL10.GL_FLOAT, 0, getFloatBuffer(vertices));  
    
  // �`��    
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
  * UV���W�̐ݒ� 
  */  
 public void setTexture() {  
  gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, TEXCOORD_BUFFER);  
 }  
   
 
   
 /** 
  * ���W�ϊ����_�o�b�t�@�̎擾�i�摜���w��j 
  * @param x X���W 
  * @param y Y���W 
  * @param width ���� 
  * @param height �c�� 
  * @return ���_�o�b�t�@ 
  */  
 final float[] getVertices(int x, int y, int width, int height) {  
  // ���W�n�ϊ��p  
  int px = width >> 1;  
  int py = height >> 1;  
  
  // ���_�o�b�t�@  
  float[] vertices = {  
   x, y, 0,  
   x + width, y, 0,  
   x, y + height, 0,  
   x + width, y + height, 0,  
  };  
  
/*  for(int i = 0; i < 12; i += 3) {  
   vertices[i] -= x + px;  
   vertices[i + 1] -= y + py;  
  
   // �g��  
   vertices[i] *= scale;  
   vertices[i + 1] *= scale;  
  
   // ��]  
   float tx = vertices[i];  
   float ty = vertices[i + 1];  
   vertices[i] = (float) (Math.cos(Math.PI*angle/180.0f) * tx - Math.sin(Math.PI*angle/180.0f) * ty);  
   vertices[i + 1] = (float) (Math.sin(Math.PI*angle/180.0f) * tx + Math.cos(Math.PI*angle/180.0f) * ty);  
  
   // �ړ�  
   vertices[i] += x + px;  
   vertices[i + 1] += y - py;  
  }  */
  
  return vertices;  
 }  
   
 /** 
  * �����擾 
  * @return 
  */  
 public int getWidth() {  
  return width;  
 }  
   
 /** 
  * �c���擾 
  * @return 
  */  
 public int getHeight() {  
  return height;  
 }  
   
 /** 
  * �͈͎擾 
  * @return 
  */  
 public Rect getRect() {  
  return new Rect(0, 0, width, height);  
 }  
} 
