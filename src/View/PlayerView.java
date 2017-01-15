package View;

import java.util.HashMap;
import Model.Player;
import Model.Son;
import processing.core.PApplet;
import processing.core.PGraphics;
import Model.PairAnim;
import Model.Animation;
import Main.GlitchesBattle;

public class PlayerView {
	private Player my_model;
	private int deltaX = 512/2;
	private int deltaY = 512-50;
	private int deltaZ = 512-50;
	private String pathAnims = "./ressources/character/";
	private String pathAnimsMeteor = "./ressources/meteorite/";
	public PApplet parent;
	public HashMap<String,PairAnim> anims;
	public PairAnim currentAnim;
	public PairAnim lastAnim;
	private static Son son_validation;
	private boolean meteor = false;
	private static Son sonHit;
	private static Son sonBlessure;
	
	private final static int METEOR_SIZE = 140;

	public PlayerView(Player p) {
		my_model = p;

		son_validation = new Son(((GlitchesBattle) my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/epee.mp3");
		sonHit = new Son(((GlitchesBattle) my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/epee.mp3");
		sonBlessure = new Son(((GlitchesBattle) my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/coup_ventre.mp3");
		parent = my_model.getParent();
		if (my_model.color == Player.BLUE) 
			pathAnims += "blue/";
		else
			pathAnims += "red/";
		anims = new HashMap<String,PairAnim>();
		addAnimation("death", 1, true);
		addAnimation("hurt", 11, false);
		addAnimation("idle", 15, true);
		addAnimation("jump", 12, false);
		addAnimation("run", 8, true);
		addAnimation("slash", 11, false);
		addAnimation("slashjump", 12, false);
		addAnimation("walk", 8, true);
		addAnimation("meteor", 10, false);

		idle();

		//PVDisplay = parent.createGraphics(parent.width, 100);
	}

	public void display_pv() {
		float rectWidth = 200;

		// Change color
		if (my_model.get_pv() < 25)
		{
			my_model.getParent().fill(255, 0, 0);
		}  
		else if (my_model.get_pv() < 50)
		{
			my_model.getParent().fill(255, 200, 0);
		}
		else
		{
			my_model.getParent().fill(0, 255, 0);
		}

		// Draw bar
		my_model.getParent().noStroke();
		// Get fraction 0->1 and multiply it by width of bar
		float drawWidth = (my_model.get_pv() / Player.MAX_HEALTH) * rectWidth;
		my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 10, drawWidth, 10);

		// Outline
		my_model.getParent().stroke(0);
		my_model.getParent().noFill();
		my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 10, rectWidth, 10);

	}

	void display_mana() {
		float rectWidth = 200;

		// Change color
		my_model.getParent().fill(196, 254, 246);


		// Draw bar
		my_model.getParent().noStroke();
		// Get fraction 0->1 and multiply it by width of bar
		float drawWidth = (my_model.get_mana() / Player.MAX_MANA) * rectWidth;
		my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 30, drawWidth, 10);

		// Outline
		my_model.getParent().stroke(0);
		my_model.getParent().noFill();
		my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 30, rectWidth, 10);

	}

	void displayPlayer() {
		//my_model.getParent().image(my_model.getSprite(), my_model.getX(), my_model.getY(), my_model.getWidht(), my_model.getHeight());
		int x = my_model.getX();
		int y = my_model.getY();
		int z = my_model.getZ();
		boolean lastFrame = sens(currentAnim).display(x-deltaX,y-deltaY,z);
		if (!sens(currentAnim).loopable)
			if (lastFrame) {
				currentAnim = lastAnim;
				my_model.hurting = false;
			}
		
		if (meteor) {
			int xm = my_model.getX()-METEOR_SIZE;
			int ym = my_model.getY()-METEOR_SIZE;

			boolean lastFrameMeteor = sens(anims.get("meteor")).display(xm,ym,-1000);
			if (lastFrameMeteor) {
				meteor = false;
				my_model.controler.getView().hurt();
			}
		}
	}

	// loopable anims
	public void idle() {
		lastAnim = currentAnim = anims.get("idle");
	}
	public void run() {
		lastAnim = currentAnim = anims.get("run");
	}
	
	public void walk() {
		lastAnim = currentAnim = anims.get("walk");
	}
	
	public void death() {
		lastAnim = currentAnim = anims.get("death");
	}

	// not loopable anims
	public void hurt() {
		sonBlessure.getMusicMenu().play(0);
		currentAnim = anims.get("hurt");
	}
	
	public void jump() {
		currentAnim = anims.get("jump");
	}
	
	public void slash() {
		sonHit.getMusicMenu().play(0);
		currentAnim = anims.get("slash"); 
	}
	
	public void slashjump() {
		currentAnim = anims.get("slashjump"); 
	}
	
	public void meteor() {
		//currentAnim = anims.get("meteor");
		my_model.getEnnemie().getView().setMeteor(true);
	}
	
	public void setMeteor(boolean value) {
		meteor = value;
	}

	private void addAnimation(String nameAnim, int count, boolean loop) {
		String l_path;
		String r_path;
		if (nameAnim.equals("meteor"))
		{
			l_path = pathAnimsMeteor+"lr_"+nameAnim+"/";
			r_path = pathAnimsMeteor+"lr_"+nameAnim+"/";
		}else {
			l_path = pathAnims+"l_"+nameAnim+"/";
			r_path = pathAnims+"r_"+nameAnim+"/";
		}
		PairAnim panims = new PairAnim();
		panims.right = new Animation(parent, r_path,count, loop, nameAnim);
		panims.left = new Animation(parent, l_path,count, loop, nameAnim);
		anims.put(nameAnim, panims);
	}

	private Animation sens(PairAnim pa){
		return (my_model.right())? pa.right : pa.left;
	}

	public PairAnim getCurrentAnim() {
		return currentAnim;
	}

	public PairAnim getAnim(String anim) {
		return anims.get(anim);
	}

	public void play_validation() {

		son_validation.getMusicMenu().play(0);

		//	son_validation.getMusicMenu().close();
	}
	
	public void stopMusic() {
		sonHit.stop();
		sonBlessure.stop();
	}
}
