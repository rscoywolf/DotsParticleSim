ParticleSystem ps;
int mode = 1;
int xposes[];
int yposes[];
int tick = 0;
int count = 0;
float textfade = 255;
boolean texton = true;
boolean startfading = false;
boolean radiant = false;
boolean mouseintro = false;

void setup() {
  size(800, 800);
  surface.setResizable(true);
  smooth(5);
  noStroke();
  ps = new ParticleSystem(new PVector(width/2, height/2));
}

void draw() {
  println("particles: " + count);
  tick++;
  background(31, 38, 35);
  if (mode == 1){
    count = count + 1;
    ps.addParticle();
    
  }
  ps.run();
  if (texton == true){
    fill(31, 38, 35, textfade);
    textSize(100);
    textAlign(CENTER);
    text("DOTS", width/2, height/2); 
    textSize(32);
    text("BY RYAN SPURGEON", width/2, height/2+50); 
  }
  if(mouseintro == false){
    if (tick > 500 || startfading == true){
      startfading = true;
      textfade = textfade - 0.7;
      if (textfade <= 0) texton = false;
    }
  }
  if (mousePressed || mouseintro == true){
   mouseintro = true; 
   if (!mousePressed){
     texton = false;
   }
  }
  textAlign(LEFT);
  fill(255);
  textSize(10);
  text("© Copyright 2019 by Ryan Spurgeon", 10, height-20); 
  text("All rights reserved", 10, height-10); 
  
}
class ParticleSystem {
  ArrayList<Particle> particles;
  PVector origin;

  ParticleSystem(PVector position) {
    origin = position.copy();
    particles = new ArrayList<Particle>();
  }
  void addParticle() {
    particles.add(new Particle(origin));
  }
  void run() {
    for (int i = particles.size()-1; i >= 0; i --) {
      Particle p = particles.get(i);
      p.run();
      if (p.isDead()) {
        count = count -1;
        particles.remove(i);
      }
    }
  }
}

class Particle {
  PVector position;
  PVector velocity;
  PVector acceleration;
  float lifespan;
  color c;
  boolean radiant = false;
  Particle(PVector l) {
    int k = (int)random(0, 6);
    int j = (int)random(0, 6);
    
    //Fun color scheme
    //if (k==0) c = color(#D81A95);
    //if (k==1) c = color(#44B7FF);
    //if (k==2) c = color(#42F48C);
    //if (k==3) c = color(#EBF442);
    //if (k==4) c = color(#F48042);
    //if (k==5) c = color(#FFFFFF);
    
    //Space color scheme
    if (k==0) c = color(#F2A25C);
    if (k==1) c = color(#E55B4B);
    if (k==2) c = color(#FCA4A4);
    if (k==3) c = color(#FFEB9B);
    if (k==4) c = color(#B7A4FC);
    if (k==5) c = color(#FFFFFF);
    
    if (j <=4){
      radiant = true;
    }
    
    acceleration = new PVector(random(-0.03, 0.03), random(-0.03, 0.03));
    velocity = new PVector(random(-1, 1), random(-1, 1));
    position = l.copy();
    lifespan = 500.0;
  }

  void run() {
    update();
    display();
  }

  void update() {
    if (position.x > (width) || position.x<0) {
      velocity.x = velocity.x * -1;
    }
    if (position.y > (height) || position.y<0) {
      velocity.y = velocity.y * -1;
    }
    velocity.add(acceleration);
    position.add(velocity);
    if (mode == 1){
      lifespan -= 1.0;
    }
    if (mousePressed) {
      PVector mouse = new PVector(mouseX, mouseY);
      PVector dir = PVector.sub(mouse, position);

      dir.normalize();

      dir.mult(0.5);
      if (!mousePressed){
        dir.x = dir.x + random(-0.7, 0.7);
        dir.y = dir.y + random(-0.7, 0.7);
      }
      acceleration = dir;
      //acceleration.x = dir.x + random(-0.7, 0.7);
      //acceleration.y = dir.y + random(-0.7, 0.7);

      velocity.add(acceleration);
      velocity.limit(1);
      position.add(velocity);
      mode = 2;
      
    }
    else{
      mode = 1;
    }
  }

  void display() {
    fill(c, lifespan);
    ellipse(position.x, position.y, 2, 2);
    fill(c, lifespan/10);
    ellipse(position.x,position.y,6,6);
    if (radiant == true){
      fill(c, lifespan/60);
      ellipse(position.x,position.y,70,70);
    }
  }

  boolean isDead() {
    if (lifespan < 0.0) {
      return true;
    } else {
      return false;
    }
  }
}
