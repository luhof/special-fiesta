import processing.core.PApplet;
import processing.core.PVector;

abstract class PMat extends PApplet{
	
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
		/*if(this.getPos().x + h * this.getVit().x > 640 || this.getPos().x + h * this.getVit().x < 0){
			this.getVit().x *= -1;
		}*/
		this.getPos().x += h * this.getVit().x;
	
		/*if(this.getPos().y + h * this.getVit().y > 640 || this.getPos().y + h * this.getVit().y < 0){
			this.getVit().y *= -5;
		}*/
		this.getPos().y += h * this.getVit().y;
		
		/*if(this.getPos().z + h * this.getVit().z > 100 || this.getPos().z + h * this.getVit().z < -100){
			this.getVit().z *= -1;
		}*/
		this.getPos().z += h * this.getVit().z;
		
			
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
	
	public void act(){
		
	}

	public void act(Sheep sheep) {
		// TODO Auto-generated method stub
	}
	public void act(Dog dog) {
		// TODO Auto-generated method stub
	}
	
	public void act(Wolf wolf) {
		// TODO Auto-generated method stub
	}
	
	

}
