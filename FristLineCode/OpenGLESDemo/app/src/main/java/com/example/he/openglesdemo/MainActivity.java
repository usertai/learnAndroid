package com.example.he.openglesdemo;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GLSurfaceView是一种比较特殊的View，我们可以在该View中绘制OpenGL ES图形，
 * 不过它自己并不做太多和绘制图形相关的任务。
 * 绘制对象的任务是由你在该View中配置的GLSurfaceView.Renderer所控制的
 */

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);

    }


}

class MyGLSurfaceView extends GLSurfaceView {

    private Renderer mRenderer;
    //投影矩阵
    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];


    //触摸监听
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;


    public MyGLSurfaceView(Context context) {
        super(context);
        //设置版本
        setEGLContextClientVersion(2);
        mRenderer = new MyRenderer();
        setRenderer(mRenderer);

        //如果选用这一配置选项，那么除非调用了requestRender()，否则GLSurfaceView不会被重新绘制，这样做可以让应用的性能及效率得到提高。

//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);//仅在你的绘制数据发生变化时才在视图中进行绘制操作
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    //自定义渲染类,执行与绘制相关的工作
    private class MyRenderer implements Renderer {

        private Triangle mTriangle;
        private Square   mSquare;
        private float[] mRotationMatrix = new float[16];


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // Set the background frame color
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

            mTriangle=new Triangle();
            mSquare=new Square();

        }

        //如果View的几何形态发生变化时会被调用，例如当设备的屏幕方向发生改变时。
        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);

            float ratio = (float) width / height;
            // this projection matrix is applied to object coordinates
            // in the onDrawFrame() method
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        }

        //每次重绘View时调用
        @Override
        public void onDrawFrame(GL10 gl) {
            gl.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            float[] scratch = new float[16];

            // Set the camera position (View matrix)
            Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            // Calculate the projection and view transformation
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

            // Draw shape
//            mTriangle.draw(mMVPMatrix);

            // Create a rotation transformation for the triangle
            long time = SystemClock.uptimeMillis() % 4000L;
            float angle = 0.090f * ((int) time);
            Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

            // Combine the rotation matrix with the projection and camera view
            // Note that the mMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

            // Draw triangle
            mTriangle.draw(scratch);

        }
    }


}