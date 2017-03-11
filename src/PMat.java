import processing.core.PVector;

public class PMat {
	
	private PVector pos;
	private PVector vit;
	float m;
	private PVector frc;
	
	public PMat(PVector _pos, float _m) {
	    this.setPos(new PVector(_pos.x, _pos.y, _pos.z));
	    this.m = _m;
	    this.setVit(new PVector(0, 0, 0));
	    this.setFrc(new PVector(0, 0, 0));
	}
	
	public void UpdateLeapFrog(double h)
	{
		this.getVit().x += h / this.m * this.getFrc().x;
		this.getVit().y += h / this.m * this.getFrc().y;
		this.getPos().x += h * this.getVit().x;
		this.getPos().y += h * this.getVit().y;
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
