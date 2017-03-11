import processing.core.PApplet;
import processing.core.PVector;

public class UsingProcessing extends PApplet{
	
	static float Fe = 70;
	static float h = 5;
	static PMat[] pMats;
	static Link[] links;
	static PVector gravity = new PVector(0, 0.90f);
	
	public static void main(String[] args) {
		PApplet.main("UsingProcessing");
	
	}

	public static void init(){
		pMats = new PMat[10];
		links = new Link[9];
		h = (float) (1. / Fe);
		float alpha = 0.5f;
		float k = alpha * Fe * Fe;
		float z = (float) (alpha/10. * Fe); /*alpha / 10 = beta*/
		
		for(int i = 0; i < 10; ++i){
			pMats[i] = new PMat(new PVector((float) (i)*40 + 20, 50), 1);
		}

		for(int i = 0; i < pMats.length - 1; ++i){
			links[i] = new Link(k, z);
			links[i].LinkConnect(pMats[i], pMats[i+1]);
		}
		
		pMats[5].getPos().sub(20, 15);
		
		
		
		
	}
	
	public static void update(){
		
		for(int i = 1; i < pMats.length-1; ++i)
		{
			pMats[i].UpdateLeapFrog(h); 
		}

		for(int i = 0; i < links.length; ++i)
		{
			links[i].LinkRessort();
			links[i].LinkFrein();
			links[i].LinkGravite(gravity);
		}
		
	}
	
	public void settings(){
	  size(640,480);
	}

    public void setup(){
    	//fill(255,0,0);
    	init();

    }

    public void draw(){
    	update();
    	clear();
    	//draw PMATS
    	fill(255, 0, 0);
    	stroke(0, 0, 0);
    	for(int i = 0; i < pMats.length; i++){
    		ellipse(pMats[i].getPos().x, pMats[i].getPos().y, 4, 5);
    	}
    	
    	//draw LINKS
    	stroke(0, 255, 0);
    	for(int i = 0; i< links.length; i++){
    		line(
    			links[i].getP1().getPos().x,
    			links[i].getP1().getPos().y,
    			links[i].getP2().getPos().x,
    			links[i].getP2().getPos().y
    			);
    	}
    }

}
