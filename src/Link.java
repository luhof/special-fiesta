import processing.core.PVector;

public class Link {
	
	private PMat  p1 = null;
	private PMat  p2 = null;
	private float k, z, l0;
	
	public Link(float _k, float _z){
		this.k = _k;
		this.z = _z;
		this.l0 = 0;
	}
	
	public float distance(PMat _p1, PMat _p2){
		float dist = (this.p2.getPos().x - this.p1.getPos().x)*(this.p2.getPos().x - this.p1.getPos().x);
		dist += (this.p2.getPos().y - this.p1.getPos().y)*(this.p2.getPos().y - this.p1.getPos().y);
		dist += (this.p2.getPos().z - this.p1.getPos().z)*(this.p2.getPos().z - this.p1.getPos().z);
		return (float) Math.sqrt(dist);
	}
	
	public void LinkConnect(PMat _p1, PMat _p2){
		this.setP1(_p1);
		this.setP2(_p2);
		this.l0 = _p1.getPos().dist(_p2.getPos());
	}
	
	public void LinkRessort(){
		float d = this.getP2().getPos().dist(this.getP1().getPos());
	
		// * dir (sub des 2)
		float f = -this.k * (1-this.l0 / d);
		PVector dir = new PVector(
				this.getP2().getPos().x - this.getP1().getPos().x,
				this.getP2().getPos().y - this.getP1().getPos().y,
				this.getP2().getPos().z - this.getP1().getPos().z);
		dir.mult(f);
		
		this.getP2().getFrc().add(dir.x, dir.y, dir.z);
		this.getP1().getFrc().sub(dir.x, dir.y, dir.z);
	}
	
	public void LinkFrein(){
		PVector dv = new PVector(
				this.getP1().getVit().x - this.getP2().getVit().x,
				this.getP1().getVit().y - this.getP2().getVit().y,
				this.getP1().getVit().z - this.getP2().getVit().z
				);
				
				
		dv.mult(-this.z);
		this.getP1().getFrc().add(dv);
		this.getP2().getFrc().sub(dv);
	}
	
	public void LinkGravite(PVector gravity){
		if(!this.getP1().isFix)
			this.getP1().getFrc().add(gravity);
		if(!this.getP2().isFix)
			this.getP2().getFrc().add(gravity);
	}

	public PMat getP1() {
		return p1;
	}

	public void setP1(PMat p1) {
		this.p1 = p1;
	}

	public PMat getP2() {
		return p2;
	}

	public void setP2(PMat p2) {
		this.p2 = p2;
	}
	

}
