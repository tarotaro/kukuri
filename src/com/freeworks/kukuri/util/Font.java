package com.freeworks.kukuri.util;

import android.graphics.Bitmap;  
import android.graphics.Bitmap.Config;  
import android.graphics.Canvas;  
import android.graphics.Paint;  
import android.graphics.Paint.FontMetrics;  
import com.freeworks.kukuri.util.SpriteTexture;  
import javax.microedition.khronos.opengles.GL10; 
/** 
 * �t�H���g�N���X 
 */  
public class Font {  
 final static Paint paint = new Paint(); // �`����  
 static FontMetrics font = paint.getFontMetrics(); // �t�H���g���g���N�X  
  
 /** 
  * �R���X�g���N�^ 
  * �C���X�^���X���֎~ 
  */  
 Font() {}  
  
 /** 
  * �`�� 
  * @param text 
  * @param x 
  * @param y 
  */  
 public static void draw(String text, int x, int y,int screen_height,GL10 gl) {  
  // �����T�C�Y�̎擾  
    
  int[] size = { getWidth(text), getHeight() };  
    
  int[] power_size ={ 2, 2 };  
   
  for(int i = 0; i < size.length; i++) {  
	  		while(power_size[i] < size[i]) {  
	  				power_size[i] <<= 1;  
	  		}  
  }  
 
  //�L�����o�X�ƃr�b�g�}�b�v�����L��  
  Bitmap image = Bitmap.createBitmap(power_size[0], power_size[1], Config.ARGB_8888);  
  Canvas canvas = new Canvas(image);
    
  
  // ��������L�����o�X�ɕ`��  
  paint.setAntiAlias(true);  
  canvas.drawText(text, 0, Math.abs(font.ascent), paint);  
  
  // �L�����o�X�f�[�^����X�v���C�g���쐬  
  SpriteTexture sprite = SpriteTexture.create(image,gl);  
  
  // �X�v���C�g�̕`��  
  sprite.draw(x, y,screen_height);
  
  // �J��  
  sprite.release();  
  sprite = null;  
  canvas = null;  
 }  
  
 /** 
  * ������F�̐ݒ� 
  * @param color 
  */  
 public static void setColor(int color) {  
  paint.setColor(color);  
 }  
  
 /** 
  * �����T�C�Y�̐ݒ� 
  * @param size 
  */  
 public static void setSize(int size) {  
  paint.setTextSize(size);  
  font = paint.getFontMetrics(); // �����T�C�Y�X�V��̃��g���N�X���擾  
 }  
  
 /** 
  * ������̕��̎擾 
  * @param text 
  * @return ������̕� 
  */  
 public static int getWidth(String text) {  
  return (int) (paint.measureText(text) + 0.5f);  
 }  
  
 /** 
  * ������̍������擾 
  * @return ������̍� 
  */  
 public static int getHeight() {  
  return (int) (Math.abs(font.top) + Math.abs(font.bottom) + 0.5f);  
 }  
}  