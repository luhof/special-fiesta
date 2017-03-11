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
	
	public void LinkConnect(PMat _p1, PMat _p2){
		this.setP1(_p1);
		this.setP2(_p2);
		this.l0 = this.getP1().getPos().dist(this.getP2().getPos());
	}
	
	public void LinkRessort(){
		float d = this.getP1().getPos().dist(this.getP2().getPos());
		float f = this.k * (1 -this.l0 / d);
		
		this.getP1().getFrc().add(f, f);
		this.getP2().getFrc().sub(f, f);

	}
	
	public void LinkFrein(){
		PVector dv = new PVector(this.getP2().getVit().x - this.getP1().getVit().x, this.getP2().getVit().y - this.getP1().getVit().y);
		dv.x *= this.z;
		dv.y *= this.z;
		this.getP1().getFrc().add(dv);
		this.getP2().getFrc().sub(dv);
	}
	
	public void LinkGravite(PVector gravity){
		this.getP1().getFrc().add(gravity);
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
