import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class UsingProcessing extends PApplet{
	
	static float Fe = 100f;
	static float h = 1/Fe;
	static List<PMat> pMats;
	static List<Link> links;
	static PVector gravity = new PVector(0, 9.8f, 0);
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
		
		//createParticles();
		createFlag();
		//reset();
				
	}
	
	public void reset(){
		double randomIndex = Math.random() * pMats.size();
		if(!pMats.get((int)randomIndex).isFix)
			pMats.get((int)randomIndex).getPos().add(0f, 0f, 10f);
	}
	
	public void update(){
		camera(mouseX, mouseY, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
		
		updateParticles();
		
		
		for(int i = 0; i < pMats.size(); ++i)
		{
			pMats.get(i).UpdateLeapFrog(h); 
		}
		
		for(int i = 0; i < links.size(); ++i)
		{
			
			links.get(i).LinkRessort();
			links.get(i).LinkFrein();
			links.get(i).LinkGravite(gravity);
		}

		
		
		
		
	}
	
	public void settings(){
	  
	  size(640, 640, P3D);

	}

    public void setup(){
    	init();

    }

    public void draw(){
    	update();
    	clear();
    	
    	//draw LINKS
    	drawLinks();
    	
    	//draw PMATS
    	drawPMats();
    	
    	strokeWeight(0.5f);
    	stroke(0, 0, 255f);
    	line(0f, 640f, 100f, 640f, 640f, 100f);
    	line(0f, 640f, -100f, 640f, 640f, -100f);
    	line(0f, 640f, 100f, 0f, 640f, -100f);
    	line(640f, 640f, 100f, 640f, 640f, -100f);
    	
    	 

    }
    
    public void drawLinks(){
    	strokeWeight(0.25f);
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
    		stroke(255, 255, 255);
    		if(pMats.get(i).getClass().getName() == "Dog") stroke(0, 255, 0);
    		if(pMats.get(i).getClass().getName() == "Sheep") stroke(0, 0, 255);
    		if(pMats.get(i).getClass().getName() == "Wolf") stroke(255, 0, 0);
    		
    		point(pMats.get(i).getPos().x, pMats.get(i).getPos().y, pMats.get(i).getPos().z);
    	}
    }
    
    public void keyPressed() {
    	 reset();
    }
    
    
    public void createParticles(){

    	h = (float) (1. / Fe);
		float alpha = 0.7f;
		float k = alpha * Fe * Fe;
		float z = (float) (alpha/10. * Fe); /*alpha / 10 = beta*/
		
		//create random pmats
		for(int i = 0; i< 10; i++){
			for(int j = 0; j< 10; j++){
				int randX = (int) (Math.random() * width);
				int randY = (int) (Math.random() * height);
				int randZ = (int) (Math.random() * 100) - 100;
				pMats.add(new Sheep(new PVector(randX, randY, randZ), 1, false));
			}
		}
		
		// FIX POINTS
		pMats.add(new Sheep(new PVector(0, 0, -100), 0, true));
		pMats.add(new Sheep(new PVector(0, 0, 100), 0, true));
		//pMats.add(new Sheep(new PVector(0, height, -100), 0, true));
		//pMats.add(new Sheep(new PVector(0, height, 100), 0, true));
		pMats.add(new Sheep(new PVector(width, 0, -100), 0, true));
		pMats.add(new Sheep(new PVector(width, 0, 100), 0, true));
		//pMats.add(new Sheep(new PVector(width, height, -100), 0, true));
		//pMats.add(new Sheep(new PVector(width, height, 100), 0, true));
		
		for(int i = 0; i< 20; i++){
			int randX = (int) (Math.random() * width);
			int randY = (int) (Math.random() * height);
			int randZ = (int) (Math.random() * 200) - 200;
			pMats.add(new Dog(new PVector(randX, randY, randZ), 1, false));
		}
		for(int i = 0; i< 5; i++){
			int randX = (int) (Math.random() * width);
			int randY = (int) (Math.random() * height);
			int randZ = (int) (Math.random() * 200) - 200;
			pMats.add(new Wolf(new PVector(randX, randY, randZ), 1, false));
		}
		
		//links each pmat with each other
		//List<PMat> pMatsToLink = new ArrayList<PMat>(pMats.size());
		//List<Dog> clone = new ArrayList<Dog>(list.size());
	    
		for(int i = 0; i< pMats.size(); i++){
			for(int j = i; j<pMats.size(); j++){
						if(i!=j){
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
		float alpha = 0.3f;
		float k = alpha * Fe * Fe;
		float z = (float) (alpha/10. * Fe); /*alpha / 10 = beta*/
		
		for(int i = 0; i < flagWidth; ++i){
			for(int j = 0; j < flagHeight; ++j){
				boolean isFix = false;
				if(i==0) isFix = true;
				pMats.add(new Dog(new PVector((float) (i)*40 + 20, j*20 + height/2, 0), isFix ? 0 : 1, isFix));
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
		
		/*
		 * //diagonal bottom left links
		 
		for(int i = 0; i< flagWidth -1; i++){
			for(int j = 0; j< flagHeight-1; j++){
				Link currLink = new Link(k, z);
				currLink.LinkConnect(pMats.get(i*flagHeight+j), pMats.get((i+1)*flagHeight + j +1 ));
				links.add(currLink);
			}
		}
		
		//diagonal bottom right links
				for(int i = 1; i< flagWidth; i++){
					for(int j = 0; j< flagHeight-1; j++){
						Link currLink = new Link(k, z);
						currLink.LinkConnect(pMats.get(i*flagHeight+j), pMats.get((i-1)*flagHeight + j +1 ));
						links.add(currLink);
					}
				}
				*/
    }
    
    
   public void updateParticles(){
	   
	   for(int i = 0; i< pMats.size(); i++){
		   for(int j = 0; j < pMats.size(); j++){
			   PMat p1 = pMats.get(i);
			   PMat p2 = pMats.get(j);
			   float dist = p1.getPos().dist(p2.getPos());
			  
				  
				
				   if(dist < 100 && dist > 5){
					   if(p2.getClass().getName() == "Dog"){
						   p1.act((Dog)p2);
					   }
					   else if(p2.getClass().getName() == "Sheep")
						   p1.act((Sheep)p2);
					   else if(p2.getClass().getName() == "Wolf")
						   p1.act((Wolf)p2);
					   
				   }
				   else{
					   /*distanceVec.mult(0.05f);
					   p1.getPos().add(distanceVec.x, distanceVec.y, distanceVec.z);
					   p2.getPos().sub(distanceVec.x, distanceVec.y, distanceVec.z);
				   
				   */}
		   }
	   }

   }
   
    

}
