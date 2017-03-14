import processing.core.PVector;

public class Sheep extends PMat{

	public Sheep(PVector _pos, float _m, boolean isFix) {
		super(_pos, _m, isFix);
	}
	
	@Override
	public void act(Sheep sheep){
		PVector distanceVec = sheep.getPos().copy();
		   distanceVec.sub(this.getPos());
		   distanceVec.x = Math.signum(distanceVec.x);
		   distanceVec.y = Math.signum(distanceVec.y);
		   distanceVec.z = Math.signum(distanceVec.z);
		   distanceVec.mult(0.1f);
		   
		   this.getFrc().add(distanceVec.x, distanceVec.y, distanceVec.z);
	}
	
	@Override
	public void act(Dog dog){
		PVector distanceVec = dog.getPos().copy();
		   distanceVec.sub(this.getPos());
		   distanceVec.x = Math.signum(distanceVec.x);
		   distanceVec.y = Math.signum(distanceVec.y);
		   distanceVec.z = Math.signum(distanceVec.z);
		   distanceVec.mult(0.1f);
		   
		   this.getFrc().add(distanceVec.x, distanceVec.y, distanceVec.z);
	}
	
	@Override
	public void act(Wolf wolf){
		PVector distanceVec = wolf.getPos().copy();
		   distanceVec.sub(this.getPos());
		   distanceVec.x = Math.signum(distanceVec.x);
		   distanceVec.y = Math.signum(distanceVec.y);
		   distanceVec.z = Math.signum(distanceVec.z);
		   distanceVec.mult(0.5f);
		   //sheep.getPos().add(distanceVec.x, distanceVec.y, distanceVec.z);
		   this.getFrc().sub(distanceVec.x, distanceVec.y, distanceVec.z);
	}

}
