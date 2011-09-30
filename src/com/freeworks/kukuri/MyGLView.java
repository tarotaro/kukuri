package com.freeworks.kukuri;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.AttributeSet;

public class MyGLView extends GLSurfaceView implements GLSurfaceView.Renderer{
  
  
  public MyGLView(Context context) {
    super(context);    
    setRenderer(this);
  }
  public MyGLView( Context ctx, AttributeSet attrs){
		super( ctx, attrs );
		setRenderer(this);
	}
  @Override
  public void onDrawFrame(GL10 gl) {
	// TODO Auto-generated method stub
	  gl.glClear(gl.GL_DEPTH_BUFFER_BIT|gl.GL_COLOR_BUFFER_BIT);
	
  }
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
	// TODO Auto-generated method stub
		gl.glViewport( 0, 0, width, height );	// ビューポートの再セット

  	//　射影行列の指定
  		gl.glMatrixMode( GL10.GL_PROJECTION );	//　射影行列（プロジェクションモード）
  		gl.glLoadIdentity();	//　単位行列のセット
  		GLU.gluPerspective(gl, 45.0f, (float)width/(float)height, 0.1f, 1000.0f);  		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	// TODO Auto-generated method stub
		gl.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );
	
	}

}
