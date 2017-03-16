import processing.core.PApplet;
import processing.core.PVector;

public class Cube extends PApplet{
	public PVector pos;
	public float size;
	
	Cube(float x, float y, float z, float size){
		this.pos = new PVector(x, y, z);
		this.size = size;
	}
	
	public float distance(PVector p1, PVector p2){
		float normX = norm(abs(p2.x-p1.x), 0f, 1);
		float normY = norm(abs(p2.y-p1.y), 0f, 1);
		float normZ = norm(abs(p2.z-p1.z), 0f, 1);
		return max(normX, normY, normZ);
	}
	
	public PVector dist_Cube(PMat p){
		
		PMat nextPoint = new Dog(p);
		
		nextPoint.UpdateLeapFrog(1/100f);
		
		//nextPoint.(p.getVit().copy());
		float d = distance(nextPoint.getPos().copy(), this.pos.copy());
		//float d = nextPoint.getPos().dist(this.pos);
		
		if(d > this.size/2){
			return null;
		}
		else{
			
			PVector penetration = new PVector(0f, 0f, 0f);
			float normX = norm(abs(nextPoint.getPos().x-pos.x), 0f, 1);
			float normY = norm(abs(nextPoint.getPos().y-pos.y), 0f, 1);
			float normZ = norm(abs(nextPoint.getPos().z-pos.z), 0f, 1);
			if(d - normX < 0.1 && d-normX > -0.1){
				penetration.x = nextPoint.getPos().x * (1 / (d) - 1);
				if(nextPoint.getPos().x > pos.x) penetration.x *= -1;
				
			}
			if(d - normY < 0.1 && d-normY > -0.1){
				penetration.y = nextPoint.getPos().y * (1 / (d)- 1);
				if(nextPoint.getPos().y > pos.y) penetration.x *= -1;
			}
			if(d - normZ < 0.1 && d-normZ > -0.1){
				penetration.z = nextPoint.getPos().z * (1 / (d) - 1);
				if(nextPoint.getPos().z > pos.z) penetration.x *= -1;
			}
			penetration.mult(size);
			return penetration;
		}
	}
}
