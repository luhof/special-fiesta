import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class UsingProcessing extends PApplet{
	
	static float Fe = 100;
	static float h = 1;
	static float x, y, z;
	static List<PMat> pMats;
	static List<Link> links;
	static PVector gravity = new PVector(0, 0.90f, 0);
	static float width = 620f;
	static float height = 620f;
	static int flagWidth = 10;
	static int flagHeight = 7;
	
	public static void main(String[] args) {
		PApplet.main("UsingProcessing");
		

	}

	public void init(){
		x = 320;
		y = 320;
		z = 0;
		
		
		pMats = new ArrayList<PMat>();
		links = new ArrayList<Link>();
		
		h = (float) (1. / Fe);
		float alpha = 0.1f;
		float k = alpha * Fe * Fe;
		float z = (float) (alpha/10. * Fe); /*alpha / 10 = beta*/
		
		for(int i = 0; i < flagWidth; ++i){
			for(int j = 0; j < flagHeight; ++j){
				boolean isFix = false;
				if(i==0) isFix = true;
				pMats.add(new PMat(new PVector((float) (i)*40 + 20, j*20 + height/2, 0), isFix ? 0 : 1, isFix));
			}
		}

		//vertical links
		for(int i = 0; i < flagWidth ; ++i){
			for(int j = 0; j < flagHeight-1; ++j){
				Link currLink = new Link(k, z);
				currLink.LinkConnect(pMats.get(i * flagHeight +j ), pMats.get(i * flagHeight + j + 1));
				links.add(currLink);
			}
		}
		
		for(int i = 0; i < flagWidth -1 ; ++i){
			for(int j = 0; j < flagHeight; ++j){
				Link currLink = new Link(k, z);
				currLink.LinkConnect(pMats.get(i * flagHeight + j ), pMats.get((i+1) * flagHeight + j));
				links.add(currLink);
			}
		}
		
		
		//reset();
		
		
		
		
	}
	
	public void reset(){
		double randomIndex = Math.random() * pMats.size();
		if(!pMats.get((int)randomIndex).isFix)
			pMats.get((int)randomIndex).getPos().add(2, 2, 1);
	}
	
	public void update(){
		camera(mouseX, mouseY, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
		
		for(int i = 0; i < pMats.size(); ++i)
		{
			pMats.get(i).UpdateLeapFrog(h); 
		}

		for(int i = 0; i < links.size(); ++i)
		{
			
			links.get(i).LinkRessort();
			links.get(i).LinkFrein();
			//links.get(i).LinkGravite(gravity);
			
		}
		
	}
	
	public void settings(){
	  
	  size(640, 640, P3D);
	

	}

    public void setup(){
    	//fill(255,0,0);
    	init();

    }

    public void draw(){
    	update();
    	clear();
    	
    	//draw LINKS
    	strokeWeight(2);
    	stroke(0, 255, 0);
    	for(int i = 0; i< links.size(); i++){
    		line(
    			links.get(i).getP1().getPos().x,
    			links.get(i).getP1().getPos().y,
    			links.get(i).getP1().getPos().z,
    			links.get(i).getP2().getPos().x,
    			links.get(i).getP2().getPos().y,
    			links.get(i).getP2().getPos().z
    			);
    	}
    	
    	//draw PMATS
    	fill(255, 0, 0);
    	
    	strokeWeight(10);  // Beastly
    	for(int i = 0; i < pMats.size(); i++){
    		//translate(0, 0, 0);
    		//translate(pMats[i].getPos().x, pMats[i].getPos().y, pMats[i].getPos().z);
    		//sphere(40);
    		if(pMats.get(i).isFix){
    			stroke(0, 0, 255);
    		}
    		else stroke(255, 0, 0);
    		point(pMats.get(i).getPos().x, pMats.get(i).getPos().y, pMats.get(i).getPos().z);
    	}
    	

    }
    
    public void keyPressed() {
    	 reset();
    }

}
