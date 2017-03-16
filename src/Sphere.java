

import processing.core.PApplet;
import processing.core.PVector;

public class Sphere extends PApplet{

	public PVector pos;
	public float radius;
	
	Sphere(float x, float y, float z, float radius){
		this.pos = new PVector(x, y, z);
		this.radius = radius;
	}
	
	public float distance(PVector p1, PVector p2){
		float sqrtX = p2.x - p1.x;
		float sqrtY = p2.y - p1.y;
		float sqrtZ = p2.z - p1.z;
		sqrtX *= sqrtX;
		sqrtY *= sqrtY;
		sqrtZ *= sqrtZ;
		return (float) Math.sqrt(sqrtX+sqrtY+sqrtZ);
	}
	
	public PVector dist_Sphere(PMat p){
		
		PMat nextPoint = new Dog(p);
		
		nextPoint.UpdateLeapFrog(1/200f);
		
		//nextPoint.(p.getVit().copy());
		float d = distance(nextPoint.getPos().copy(), this.pos.copy());
		//float d = nextPoint.getPos().dist(this.pos);
		
		if(d > this.radius){
			return null;
		}
		else{
			d = (float) (radius /  Math.sqrt(d) - radius);
			PVector penetration = new PVector();
			//penetration = nextPoint.getPos().copy();
			//float alpha = 0.01f;
			penetration.x = p.getPos().x * d;
			if(p.getPos().x > this.pos.x) penetration.x *= -1;
			penetration.y = p.getPos().y * d;
			if(p.getPos().y > this.pos.y) penetration.y *= -1;
			penetration.z = p.getPos().z * d;
			if(p.getPos().z > this.pos.y) penetration.z *= -1;
			//penetration.mult(d);
			return penetration;
		}
	}
	
}
