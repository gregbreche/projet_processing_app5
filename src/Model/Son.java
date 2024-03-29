package Model;

import ddf.minim.*;
import processing.core.PApplet;

public class Son {
	private Minim minim;
	private AudioInput in;

	private PApplet my_parent;

	private AudioPlayer  music;

	public Son(Minim m, PApplet p, String title) {
		my_parent = p;
		minim = m;
		music = minim.loadFile(title); //ajouter de la music au jeu
		//music.mute();
	}
	
	public Son(Minim m) {
		minim = m;
	}

	public AudioPlayer getMusicMenu() {
		return music;

	}

	/*public AudioInput getVoice() {
		return minim.getLineIn(Minim.MONO , 512);
	}*/

	public void stop() {
		//music.close();
		//minim.stop();
		//music.setVolume(0);
		//minim.stop();
		music.close();
		//System.out.println("STOP MUSIQUE");
	}
	
	public void play() {
		music.play(0);
	}
	

}





//AudioSample -- loadSample --- song.trigger() (plusieurs fois possible)
/*void setup()
{
 // size(512, 200);

  minim = new Minim(this);

  musicTest = minim.loadSnippet("music.mp3"); // ajouter de la music au jeu

  // use the getLineIn method of the Minim object to get an AudioInput
  in = minim.getLineIn(); // get voice

}

void draw()
{

  if (musicTest.isPlaying() == false){
      musicTest.play(); //rewind() possible
   }

  background(0);
  stroke(255);

  String monitoringState = in.isMonitoring() ? "enabled" : "disabled";
  text( "Input monitoring is currently " + monitoringState + ".", 5, 15 );
  if (in.isMonitoring()) {
    rect( 0, 40, in.left.level()*width, 20 );
    int level = (int)(in.left.level() * 100);
    text(level + " %", 5, 100);
  }
}

void keyPressed()
{
  if ( key == 'm' || key == 'M' )
  {
    if ( in.isMonitoring() )
    {
      in.disableMonitoring();
    }
    else
    {
      in.enableMonitoring();
    }
  }
}

void stop() {
  musicTest.close();
  minim.stop();
  super.stop();
}*/