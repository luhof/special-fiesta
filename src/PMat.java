import processing.core.PApplet;
import processing.core.PVector;

public class PMat extends PApplet{
	
	private PVector pos;
	private PVector vit;
	float m;
	private PVector frc;
	boolean isFix;
	
	public PMat(PVector _pos, float _m, boolean isFix) {
	    this.setPos(new PVector(_pos.x, _pos.y, _pos.z));
	    this.m = _m;
	    this.setVit(new PVector(0, 0, 0));
	    this.setFrc(new PVector(0, 0, 0));
	    this.isFix = isFix;
	}
	
	public void UpdateLeapFrog(double h)
	{
		if(this.isFix) return;
		
		this.getVit().x += h / this.m * this.getFrc().x;
		this.getVit().y += h / this.m * this.getFrc().y;
		this.getVit().z += h / this.m * this.getFrc().z;
		if(this.getPos().x + h * this.getVit().x > 620 && this.getPos().x + h * this.getVit().x < 0){
			this.getVit().x *= -1;
		}
		this.getPos().x += h * this.getVit().x;
	
		if(this.getPos().y + h * this.getVit().y > 620 && this.getPos().y + h * this.getVit().y < 0){
			this.getPos().y = 620;
			this.getVit().y *= -1;
		}
		this.getPos().y += h * this.getVit().y;
		//else this.getPos().y -= h * this.getVit().y;
		if(this.getPos().z + h * this.getVit().z > 50 && this.getPos().z + h * this.getVit().z < -50){
			this.getVit().z *= -1;
		}
		this.getPos().z += h * this.getVit().z;
		//else this.getPos().z -= h * this.getVit().z;
			
		this.getFrc().set(0, 0, 0);
	}

	public PVector getPos() {
		return pos;
	}

	public void setPos(PVector pos) {
		this.pos = pos;
	}

	public PVector getFrc() {
		return frc;
	}

	public void setFrc(PVector frc) {
		this.frc = frc;
	}

	public PVector getVit() {
		return vit;
	}

	public void setVit(PVector vit) {
		this.vit = vit;
	}
	
	

}
