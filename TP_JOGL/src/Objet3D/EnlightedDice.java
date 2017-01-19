package Objet3D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class EnlightedDice extends JFrame implements GLEventListener
{

	GLU glu = new GLU();
	private float X = 0.0f;
	private float Y = 0.0f;
	private float Z = 0.0f;
	private float angle = 0.0f;
	private float angleX = 0.0f;
	private float angleY = 0.0f;
	private float angleZ = 0.0f;
	private int tex[] = new int[6];
	
	
	public EnlightedDice(int width, int height){
		super( "CUBE OpenGL ! ! ! " ) ;
		GLProfile profil = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profil);
		
		GLCanvas canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(this);
		canvas.setSize(width, height);
		
		this.getContentPane().add(canvas);
		
		this.setSize(width, height) ;
		this.setLocationRelativeTo(null) ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		this.setVisible(true);
		
		
		//Création et instanciation du FPSAnimator
		final FPSAnimator animator = new FPSAnimator(canvas, 60, true);
		animator.start() ;
		
		
		this.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				switch(e.getKeyCode()){
					case KeyEvent.VK_UP:
						angleX -= 1f;
						break;
					case KeyEvent.VK_DOWN:
						angleX += 1f;
						break;
					case KeyEvent.VK_LEFT:
						angleZ -= 1f;
						break;
					case KeyEvent.VK_RIGHT:
						angleZ += 1f;
						break;	
				}
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	@Override
	public void display(GLAutoDrawable drawable)
	{
		//Récupérons notre objet OpenGL
		GL2 gl = drawable.getGL().getGL2();
		//On efface le buffer couleur (ce qu’on affiche) et le buffer de profondeur
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT) ;
		
		float[] specularColor = {1.0f, 1.0f, 1.0f, 1.0f};
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularColor, 0);
		
		gl.glLoadIdentity();
		gl.glTranslatef(0f, 0f, -6f);
		
		/*
		gl.glRotatef(angleX, 1f, 0f, 0f);
		gl.glRotatef(angleY, 0f, 1f, 0f);
		gl.glRotatef(angleZ, 0f, 0f, 1f);
		*/
		
		angle += 0.5f;
		gl.glRotatef(angle, 1f, 1f, 1f);
		 
		 
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, tex[3]) ;
		gl.glNormal3f(-1f,0f,0f);
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(-1f, 1f, -1f ); //1ere face
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(-1f, 1f, 1f);  //1ere face
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(-1f, -1f, -1f); //1ere face
		
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(-1f, 1f, 1f);  //1ere face
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(-1f, -1f, -1f); //1ere face
		gl.glTexCoord2f(1, 0);
		gl.glVertex3f(-1f, -1f, 1f); //1ere face
		gl.glEnd();

		gl.glBindTexture(GL2.GL_TEXTURE_2D, tex[1]) ;
		gl.glNormal3f(0f,0f,1f);
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(-1f, -1f, 1f); //2
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(-1f, 1f, 1f);  //2
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(1f, 1f, 1f); 	//2
		
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(1f, 1f, 1f); 	//2
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(-1f, -1f, 1f); //2
		gl.glTexCoord2f(1,0);
		gl.glVertex3f(1f, -1f, 1f);	//2
		gl.glEnd();

		gl.glBindTexture(GL2.GL_TEXTURE_2D, tex[2]) ;
		gl.glNormal3f(1f,0f,0f);
		gl.glBegin(GL2.GL_TRIANGLES);
		//gl.glColor3f(0f,1f,0f);
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(1f, 1f, 1f); //3
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(1f, -1f, 1f);  //3
		gl.glTexCoord2f(1, 0);
		gl.glVertex3f(1f, -1f, -1f); 	//3
		
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(1f, 1f, -1f); 	//3
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(1f, 1f, 1f); //3
		gl.glTexCoord2f(1, 0);
		gl.glVertex3f(1f, -1f, -1f); 	//3
		gl.glEnd();

		gl.glBindTexture(GL2.GL_TEXTURE_2D, tex[4]) ;
		gl.glNormal3f(0f,0f,-1f);
		gl.glBegin(GL2.GL_TRIANGLES);		
		//gl.glColor3f(0f,0f,1f);
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(1f, 1f, -1f); //4
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(1f, -1f, -1f);  //4
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(-1f, -1f, -1f); 	//4
		
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(-1f, -1f, -1f); 	//4
		gl.glTexCoord2f(1, 0);
		gl.glVertex3f(-1f, 1f, -1f); //4
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(1f, 1f, -1f);	//4
		gl.glEnd();

		gl.glBindTexture(GL2.GL_TEXTURE_2D, tex[0]) ;
		gl.glNormal3f(0f,1f,0f);
		gl.glBegin(GL2.GL_TRIANGLES);
		//gl.glColor3f(0f,1f,1f);
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(1f, 1f, -1f); //5
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(-1f, 1f, -1f);  //5
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(-1f, 1f, 1f); 	//5
		
		gl.glTexCoord2f(0,0);
		gl.glVertex3f(-1f, 1f, 1f); 	//5
		gl.glTexCoord2f(1,0);
		gl.glVertex3f(1f, 1f, 1f); //5
		gl.glTexCoord2f(1,1);
		gl.glVertex3f(1f, 1f, -1f);	//5
		gl.glEnd();

		gl.glBindTexture(GL2.GL_TEXTURE_2D, tex[5]) ;
		gl.glNormal3f(0f,-1f,0f);
		gl.glBegin(GL2.GL_TRIANGLES);
		//gl.glColor3f(1f,1f,0f);
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(-1f, -1f, -1f); //6
		gl.glTexCoord2f(1,0);
		gl.glVertex3f(1f, -1f, -1f);  //6
		gl.glTexCoord2f(1,1);
		gl.glVertex3f(1f, -1f, 1f); 	//6
		
		gl.glTexCoord2f(1,1);
		gl.glVertex3f(1f, -1f, 1f); 	//6
		gl.glTexCoord2f(0,1);
		gl.glVertex3f(-1f, -1f, 1f); //6
		gl.glTexCoord2f(0,0);
		gl.glVertex3f(-1f, -1f, -1f);	//6
		
		gl.glEnd() ; // Indique que nous avons fini de dessiner des triangles
		
		
		
		//Pour forcer l’éxécution des différentes instructions OpenGL
		gl.glFlush();
	}
	
	

	@Override
	public void dispose(GLAutoDrawable arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		
		
		gl.glEnable(GL2.GL_TEXTURE_2D);
		try{
			File im = new File ("data/dice_1.png");
			Texture t0 = TextureIO.newTexture(im, true);
			
			tex[0] = t0.getTextureObject(gl) ;
		} 
		catch (Exception e) {e.printStackTrace();}
		
		try{
			File im = new File ("data/dice_2.png");
			Texture t1 = TextureIO.newTexture(im, true);
			tex[1] = t1.getTextureObject(gl) ;
		} 
		catch (Exception e) {e.printStackTrace();}
		
		try{
			File im = new File ("data/dice_3.png");
			Texture t2 = TextureIO.newTexture(im, true);
			tex[2] = t2.getTextureObject(gl) ;
		} 
		catch (Exception e) {e.printStackTrace();}
		
		try{
			File im = new File ("data/dice_4.png");
			Texture t3 = TextureIO.newTexture(im, true);
			tex[3] = t3.getTextureObject(gl) ;
		} 
		catch (Exception e) {e.printStackTrace();}
		
		try{
			File im = new File ("data/dice_5.png");
			Texture t4 = TextureIO.newTexture(im, true);
			tex[4] = t4.getTextureObject(gl) ;
		} 
		catch (Exception e) {e.printStackTrace();}
		
		try{
			File im = new File ("data/dice_6.png");
			Texture t5 = TextureIO.newTexture(im, true);
			tex[5] = t5.getTextureObject(gl) ;
		} 
		catch (Exception e) {e.printStackTrace();}
	}

	@Override
	public void reshape (GLAutoDrawable drawable , int x , int y , int width , int height){
		final GL2 gl = drawable.getGL().getGL2();
		if (height <= 0) height = 1;
		final float h = (float) width/(float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION );
		gl.glLoadIdentity() ;
		glu.gluPerspective(45.0f, h, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glEnable(GL2.GL_DEPTH_TEST);
	}
}
