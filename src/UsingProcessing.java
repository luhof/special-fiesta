import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class UsingProcessing extends PApplet{
	
	static float Fe = 200f;
	static float h = 1/Fe;
	static List<PMat> pMats;
	static List<Link> links;
	static Sphere mySphere;
	static Cube myCube;
	public PImage img;
	
	static PVector gravity = new PVector(0, 19.8f, 0);
	static float width = 620f;
	static float height = 620f;
	static int flagWidth = 60;
	static int flagHeight = 30;
	
	public static void main(String[] args) {
		PApplet.main("UsingProcessing");
	}

	public void init(){
		
		pMats = new ArrayList<PMat>();
		links = new ArrayList<Link>();
		
		//createParticles();
		createSphere();
		createCube();
		createFlag();
		//reset();
				
	}
	
	private void createSphere() {
		mySphere = new Sphere(width/2-100, height/2, 0, 80);
	}
	
	private void createCube(){
		myCube = new Cube(width/2+100, height/2, 0, 100);
	}

	public void reset(){
		pMats.clear();
		links.clear();
		createFlag();
		myCube.pos.x = (float) (Math.random()*width/2+100);
		myCube.pos.y = (float) (Math.random()*height/2 + 100);
		myCube.pos.z = (float) (Math.random()*50);
		mySphere.pos.x = (float) (Math.random()*width/2+100);
		mySphere.pos.y = (float) (Math.random()*height/2 + 100);
		mySphere.pos.z = (float) (Math.random()*50);
	}
	
	public void update(){
		camera(mouseX, mouseY, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
		
		for(int i = 0; i < pMats.size(); ++i)
		{
			
			PVector penetration = mySphere.dist_Sphere(pMats.get(i));
			if(penetration != null){
				
				pMats.get(i).getFrc().add(penetration);
				
			}
			
			penetration = myCube.dist_Cube(pMats.get(i));
			if(penetration != null){
				
				pMats.get(i).getFrc().add(penetration);
				
			}
			
			
				
			
		}
		
		for(int i = 0; i < pMats.size(); i++){
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
    	img = loadImage("./incerti.jpg");

    }

    public void draw(){
    	update();
    	clear();
    	
    	//draw LINKS
    	drawLinks();
    	
    	//draw PMATS
    	//drawPMats();
    	
    	
    	
    	/*
    	 * 
    	 * trying to draw texture
    	 * 
    	 */
    	
    	
    	// "laDefense.jpg" is 100x100 pixels in size so
    	// the values 0 and 100 are used for the
    	// parameters "u" and "v" to map it directly
    	// to the vertex points
		/*beginShape(TRIANGLE_FAN);
    	texture(img);
    	for(int i = 0; i< flagWidth-1; i++){
    		for(int j = 0; j< flagHeight-1; j++){
    			int indice = (j*flagWidth +i);

    			vertex(pMats.get(indice).getPos().x, pMats.get(indice).getPos().y, pMats.get(indice).getPos().z, (float)838f/flagWidth *i, (float)1102f/flagHeight * j);
    			vertex(pMats.get(indice).getPos().x, pMats.get(indice).getPos().y, pMats.get(indice).getPos().z, (float)838f/flagWidth *(i+1), (float)1102f/flagHeight * (j+1));
    			vertex(pMats.get(indice).getPos().x, pMats.get(indice).getPos().y, pMats.get(indice).getPos().z, (float)838f/flagWidth *i, (float)1102f/flagHeight * (j+1));
    			
    			
    		}
    	}
    	endShape(CLOSE);*/
    	
    
    	
    	
    	noStroke();
    	fill(255, 0, 255);
    	directionalLight(255, 120, 255, 0.4f, -1, 0.3f);
    	
    	fill(0, 255, 255);
    	directionalLight(255, 255, 255, 0, 1, 0);
    	ambientLight(120, 0, 120);
    	pushMatrix();
    	translate(mySphere.pos.x, mySphere.pos.y, mySphere.pos.z);
    	sphere(mySphere.radius);
    	popMatrix();
    	pushMatrix();
    	translate(myCube.pos.x, myCube.pos.y, myCube.pos.z);
    	box(myCube.size);
    	popMatrix();
    	
    	
    	
    	 

    }
    
    public void drawLinks(){
    	strokeWeight(0.75f);
    	stroke(255, 255, 120);
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
    	
    	
    	strokeWeight(3);  // Beastly
    	for(int i = 0; i < pMats.size(); i++){
    		stroke(255, 0, 0);
    		if(pMats.get(i).getClass().getName() == "Dog") stroke(255, 255, 255);
    		if(pMats.get(i).getClass().getName() == "Sheep") stroke(0, 0, 255);
    		if(pMats.get(i).getClass().getName() == "Wolf") stroke(255, 0, 0);
    		
    		point(pMats.get(i).getPos().x, pMats.get(i).getPos().y, pMats.get(i).getPos().z);
    	}
    }
    
    public void keyPressed() {
    	
    	 if (key == CODED) {
    		    if (keyCode == UP) {
    		    	mySphere.pos.z -= 10;
    		    }
    		    else if(keyCode == DOWN){
    		    	mySphere.pos.z += 10;
    		    }
    		    else if(keyCode == LEFT){
    		    	mySphere.pos.x -=10;
    		    }
    		    else if(keyCode == RIGHT){
    		    	mySphere.pos.x +=10;
    		    }
    		    else{
    		    	 reset();
    		    }
    	 }
    	 else reset();
    }
    
   
    //flag
    public void createFlag(){
    	h = (float) (1. / Fe);
		float alpha = 0.3f;
		float k = alpha * Fe * Fe;
		float z = (float) (alpha/10. * Fe); /*alpha / 10 = beta*/
		
		float randPosX = (float) (Math.random() * width/3);
		float randPosZ = (float) (Math.random() * 200);
		//float randPosY = (float) (Math.random() * 50);
		//pMats.add(new Dog(new PVector(width/2, height/2, 0), 1, true));
		for(int i = 0; i < flagWidth; ++i){
			for(int j = 0; j < flagHeight; ++j){
				boolean isFix = false;
				//if(i==0) isFix = true;
				pMats.add(new PMat(new PVector((float) (i)*7+randPosX, 0, j*7 -randPosZ), isFix ? 0 : 1, isFix));
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
		
		 //diagonal bottom left links
		 
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
				
    }
    
    
   public void updateParticles(){
	   
	   for(int i = 0; i< pMats.size(); i++){
		   for(int j = 0; j < pMats.size(); j++){
			  
		   }
	   }

   }
   
    

}
