package com.freeworks.kukuri;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.AttributeSet;

import com.freeworks.kukuri.util.Font;

public class MyGLView extends GLSurfaceView implements GLSurfaceView.Renderer{
  
  float [] m_Weight_3Dpos=null;
  float [] m_Weight_Normal=null;
  float [] m_Wall_Color=null;
  float [] m_Weight_Color=null;
  short [] m_Weight_index=null;
  final float  MAX_AXIS_VALUE_Y=10.0f;
  final float  MAX_AXIS_VALUE_X=12.0f;
  final float  MAX_AXIS_VALUE_Z=-6.5f;
  
  int m_Width;
  int m_Height;
  
  public MyGLView(Context context) {
    super(context);    
    setRenderer(this);
  }
  public MyGLView( Context ctx, AttributeSet attrs){
		super( ctx, attrs );
		setRenderer(this);
		float weight[]=new float[]{70.0f,73.0f,73.5f,75.0f,76.0f,77.0f,77.5f,76.0f,75.5f,76.0f,75.0f,74.0f,73.0f,72.0f};				
		this.CreateWeight3DPos(weight);
		
		m_Wall_Color = new float[4*4];
		for(int count=0;count<2;count++){
			
			m_Wall_Color[count*2*4+0]=0.7f;
			m_Wall_Color[count*2*4+1]=1.0f;
			m_Wall_Color[count*2*4+2]=0.7f;
			m_Wall_Color[count*2*4+3]=0.7f;
			
			m_Wall_Color[(count*2+1)*4+0]=0.4f;
			m_Wall_Color[(count*2+1)*4+1]=0.7f;
			m_Wall_Color[(count*2+1)*4+2]=0.4f;
			m_Wall_Color[(count*2+1)*4+3]=1.0f;
		}
	}
  @Override
  public void onDrawFrame(GL10 gl) {
	// TODO Auto-generated method stub
	  gl.glClear(GL10.GL_DEPTH_BUFFER_BIT|GL10.GL_COLOR_BUFFER_BIT);
	  
	  
	  float vertex[]={0.0f,0.0f,0.0f, 0.0f,0.0f,MAX_AXIS_VALUE_Z, 12.0f,0.0f,0.0f, 12.0f,0.0f,MAX_AXIS_VALUE_Z};
	  float vertex2[]={0.0f,0.0f,0.0f, 0.0f,0.0f,MAX_AXIS_VALUE_Z, 0.0f,10.0f,0.0f, 0.0f,10.0f,MAX_AXIS_VALUE_Z};
	  
	  
	  ByteBuffer vbb = ByteBuffer.allocateDirect(vertex.length * 4);
	  vbb.order(ByteOrder.nativeOrder());
	  FloatBuffer vertexBuffer = vbb.asFloatBuffer();
	  vertexBuffer.put(vertex);
	  vertexBuffer.position(0);
	  
	  ByteBuffer vbb2 = ByteBuffer.allocateDirect(vertex2.length * 4);
	  vbb2.order(ByteOrder.nativeOrder());
	  FloatBuffer vertexBuffer2 = vbb2.asFloatBuffer();
	  vertexBuffer2.put(vertex2);
	  vertexBuffer2.position(0);
	  
	  
	  ByteBuffer vcbb = ByteBuffer.allocateDirect(m_Wall_Color.length * 4);
	  vcbb.order(ByteOrder.nativeOrder());
	  FloatBuffer vertexcBuffer = vcbb.asFloatBuffer();
	  vertexcBuffer.put(m_Wall_Color);
	  vertexcBuffer.position(0);
	  
	  
	  
	  short[] indices = { 0, 1, 2,3};
	  ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
	  ibb.order(ByteOrder.nativeOrder());
	  ShortBuffer indexBuffer = ibb.asShortBuffer();
	  indexBuffer.put(indices);
	  indexBuffer.position(0);
	  	  
	  gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	  gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	  
	  gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);	  
	  gl.glColorPointer(4, GL10.GL_FLOAT, 0, vertexcBuffer);
	  gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
	  gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	  gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	  
	  
	  gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	  gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	  gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer2);
	  
	  gl.glColorPointer(4, GL10.GL_FLOAT, 0, vertexcBuffer);
	  gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
	  gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	  gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	  
	  if(m_Weight_3Dpos!=null)
	  {
		  ByteBuffer weightbb = ByteBuffer.allocateDirect(m_Weight_3Dpos.length * 4);
		  weightbb.order(ByteOrder.nativeOrder());
		  FloatBuffer weightBuffer = weightbb.asFloatBuffer();
		  weightBuffer.put(m_Weight_3Dpos);
		  weightBuffer.position(0);
		  
		  ByteBuffer weightnbb = ByteBuffer.allocateDirect(m_Weight_Color.length * 4);
		  weightnbb.order(ByteOrder.nativeOrder());
		  FloatBuffer weightnBuffer = weightnbb.asFloatBuffer();
		  weightnBuffer.put(m_Weight_Color);
		  weightnBuffer.position(0);
		  
		  ByteBuffer iwbb = ByteBuffer.allocateDirect(m_Weight_index.length * 2);
		  iwbb.order(ByteOrder.nativeOrder());
		  ShortBuffer windexBuffer = iwbb.asShortBuffer();
		  windexBuffer.put(m_Weight_index);
		  windexBuffer.position(0);
		  
		 		 
		  
		  gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		  gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		  gl.glVertexPointer(3, GL10.GL_FLOAT, 0, weightBuffer);
		  gl.glColorPointer(4,GL10.GL_FLOAT, 0, weightnBuffer);		 
		  gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, m_Weight_index.length, GL10.GL_UNSIGNED_SHORT, windexBuffer);
		  
		  gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		  gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	  }
	  
	gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);  
	gl.glEnable(GL10.GL_ALPHA_TEST);    
	gl.glEnable(GL10.GL_BLEND);
	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); // デフォルトはアルファ合成    
	gl.glActiveTexture(GL10.GL_TEXTURE0);	  
	String version = gl.glGetString(GL10.GL_VERSION);  	    
	// モデル行列を無効化 
	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glPushMatrix();
	gl.glLoadIdentity();	
	// screen_width及びscreen_heightはデバイスの画面サイズ  
	GLU.gluOrtho2D(gl, 0, m_Width, 0, m_Height);
	
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glPushMatrix();
	gl.glLoadIdentity();  
	gl.glDisable(GL10.GL_DEPTH_TEST);  
	gl.glDisable(GL10.GL_NORMALIZE);  
	gl.glEnable(GL10.GL_TEXTURE_2D);
	Font.setSize(20);
	Font.setColor(Color.argb(255, 200,200, 200));  	    
	Font.draw(version, 200, 240,m_Height,gl);    
	gl.glPopMatrix();
	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glPopMatrix();
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glDisable(GL10.GL_TEXTURE_2D);  
	gl.glEnable(GL10.GL_DEPTH_TEST);  	    
	  
	 
	  
	  
  }
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
	// TODO Auto-generated method stub
		
	    
		gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        
        m_Width=width;
        m_Height=height;
        
        
        
                
		gl.glViewport( 0, 0, width, height );	// ビューポートの再セット
		gl.glShadeModel(GL10.GL_SMOOTH);

  		gl.glMatrixMode( GL10.GL_PROJECTION );	//　射影行列（プロジェクションモード）
  		gl.glLoadIdentity();	//　単位行列のセット
  		GLU.gluPerspective(gl, 60.0f, (float)width/(float)height, 0.1f, 1000.0f);
  		gl.glMatrixMode(GL10.GL_MODELVIEW);
  		gl.glLoadIdentity();
  		GLU.gluLookAt(gl, 9.0f, 10.0f, 9.0f,  6.5f, 5.5f, -2.5f, 0.0f, 1.0f, 0.0f);
  		gl.glTranslatef(1.2f, 0.0f, -0.5f);
  		  		
  		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	// TODO Auto-generated method stub
		gl.glClearColor( 0.5f, 0.5f, 0.5f, 1.0f );		
	}
	
	public void CreateWeight3DPos(float  weight[]){
		
		m_Weight_3Dpos = new float[weight.length*3*2];
				
		m_Weight_index = new short[weight.length*2];
			
		m_Weight_Color = new float[(weight.length)*4*2];		
		
		float max_weight=-9999.0f;
		float min_weight=9999.0f;
		for(int count=0;count<weight.length;count++){
			if(max_weight<weight[count])
				max_weight=weight[count];
			if(min_weight>weight[count])
				min_weight=weight[count];
		}
		float space=(max_weight-min_weight)/5.0f;
		float max_axis=max_weight+space;
		float min_axis=min_weight-space;
		float bet=MAX_AXIS_VALUE_Y/(max_axis-min_axis);
		
		for(int count=0;count<weight.length;count++)
		{
			m_Weight_3Dpos[count*2*3+0]=count*((MAX_AXIS_VALUE_X)/weight.length);
			m_Weight_3Dpos[count*2*3+1]=bet*(weight[count]-min_axis);
			m_Weight_3Dpos[count*2*3+2]=0.0f;
			
			m_Weight_3Dpos[(count*2+1)*3+0]=count*((MAX_AXIS_VALUE_X)/weight.length);
			m_Weight_3Dpos[(count*2+1)*3+1]=bet*(weight[count]-min_axis);
			m_Weight_3Dpos[(count*2+1)*3+2]=MAX_AXIS_VALUE_Z;
			
			m_Weight_Color[count*2*4+0]=0.8f;
			m_Weight_Color[count*2*4+1]=0.6f;
			m_Weight_Color[count*2*4+2]=1.0f;
			m_Weight_Color[count*2*4+3]=1.0f;
			
			m_Weight_Color[(count*2+1)*4+0]=0.6f;
			m_Weight_Color[(count*2+1)*4+1]=0.4f;
			m_Weight_Color[(count*2+1)*4+2]=1.0f;
			m_Weight_Color[(count*2+1)*4+3]=1.0f;
			
			
			m_Weight_index[count*2+0]=(short)(count*2);
			m_Weight_index[count*2+1]=(short)(count*2+1);
			
		}
		
		
		
	}

}
