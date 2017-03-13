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
		
		pMats = new ArrayList<PMat>();
		links = new ArrayList<Link>();
		
		createParticles();
		
		
		//reset();
		
		
		
		
	}
	
	public void reset(){
		double randomIndex = Math.random() * pMats.size();
		if(!pMats.get((int)randomIndex).isFix)
			pMats.get((int)randomIndex).getPos().add(10f, 0.1f, 0);
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
		updateParticles();
		
	}
	
	public void settings(){
	  
	  size(640, 640, P3D);

	}

    public void setup(){
    	//fill(255,0,0);
    	init();
    	for(int i = 0; i< pMats.size(); i++){
    		float randDir = (float)Math.random() - 1;
    		randDir *= 0.5;
    		pMats.get(i).getPos().add(randDir, randDir, randDir);
    	}

    }

    public void draw(){
    	update();
    	clear();
    	
    	//draw LINKS
    	drawLinks();
    	
    	//draw PMATS
    	drawPMats();
    	
    	

    }
    
    public void drawLinks(){
    	    strokeWeight(0.2f);
    	stroke(120, 120, 120);
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
    }
    
    public void drawPMats(){
    	fill(255, 0, 0);
    	
    	strokeWeight(10);  // Beastly
    	for(int i = 0; i < pMats.size(); i++){
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
    
    
    public void createParticles(){
    	h = (float) (1. / Fe);
		float alpha = 0.01f;
		float k = alpha * Fe * Fe;
		float z = (float) (alpha/10. * Fe); /*alpha / 10 = beta*/
		
		//create random pmats
		for(int i = 0; i< 200; i++){
			int randX = (int) (Math.random() * width);
			int randY = (int) (Math.random() * height);
			int randZ = (int) (Math.random() * 200) - 200;
			pMats.add(new PMat(new PVector(randX, randY, randZ), 1, false));
		}
		
		//links each pmat with each other
		
		for(int i = 0; i< pMats.size(); i++){
			for(int j = 0; j<pMats.size(); j++){
				if(j != i){
					Link tempLink = new Link(k, z);
					tempLink.LinkConnect(pMats.get(i), pMats.get(j));
					links.add(tempLink);
				}
				
			}
		}
		
		
    }
    
    //flag
    public void createFlag(){
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
    }
    
    
   public void updateParticles(){
	   
	   for(int i = 0; i< pMats.size(); i++){
		   for(int j = 0; j < pMats.size(); j++){
			   PMat p1 = pMats.get(i);
			   PMat p2 = pMats.get(j);
			   float dist = p1.getPos().dist(p2.getPos());
			  
				   PVector distanceVec = p2.getPos().copy();
				   distanceVec.sub(p1.getPos());
				   distanceVec.x = Math.signum(distanceVec.x);
				   distanceVec.y = Math.signum(distanceVec.y);
				   distanceVec.z = Math.signum(distanceVec.z);
				   if(dist < 100){
					   p2.getPos().add(distanceVec.x, distanceVec.y, distanceVec.z);
					   p1.getPos().sub(distanceVec.x, distanceVec.y, distanceVec.z);
				   }
				   else{
					   distanceVec.mult(0.05f);
					   p1.getPos().add(distanceVec.x, distanceVec.y, distanceVec.z);
					   p2.getPos().sub(distanceVec.x, distanceVec.y, distanceVec.z);
				   }
		   }
	   }

   }
   
    

}
