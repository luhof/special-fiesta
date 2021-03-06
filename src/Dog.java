import processing.core.PVector;

public class Dog extends PMat{

	public Dog(PVector _pos, float _m, boolean isFix) {
		super(_pos, _m, isFix);
	}
	
	public Dog(PMat p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act(Sheep sheep){
		   PVector distanceVec = sheep.getPos().copy();
		   distanceVec.sub(this.getPos());
		   distanceVec.x = Math.signum(distanceVec.x);
		   distanceVec.y = Math.signum(distanceVec.y);
		   distanceVec.z = Math.signum(distanceVec.z);
		   distanceVec.mult(10);
		   //sheep.getPos().add(distanceVec.x, distanceVec.y, distanceVec.z);
		   this.getFrc().add(distanceVec.x, distanceVec.y, distanceVec.z);
	}
	
	@Override
	public void act(Dog dog){
		 	PVector distanceVec = dog.getPos().copy();
		   distanceVec.sub(this.getPos());
		   distanceVec.x = Math.signum(distanceVec.x);
		   distanceVec.y = Math.signum(distanceVec.y);
		   distanceVec.z = Math.signum(distanceVec.z);
		   //dog.getPos().sub(distanceVec.x, distanceVec.y, distanceVec.z);
		   //this.getPos().add(distanceVec.x, distanceVec.y, distanceVec.z);
	}
	
	@Override
	public void act(Wolf wolf){
		 	PVector distanceVec = wolf.getPos().copy();
		   distanceVec.sub(this.getPos());
		   distanceVec.x = Math.signum(distanceVec.x);
		   distanceVec.y = Math.signum(distanceVec.y);
		   distanceVec.z = Math.signum(distanceVec.z);
		   //dog.getPos().sub(distanceVec.x, distanceVec.y, distanceVec.z);
		   //this.getPos().add(distanceVec.x, distanceVec.y, distanceVec.z);
	}
	

	
}
