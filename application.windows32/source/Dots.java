import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Dots extends PApplet {

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

public void setup() {
  
  surface.setResizable(true);
  
  noStroke();
  ps = new ParticleSystem(new PVector(width/2, height/2));
}

public void draw() {
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
      textfade = textfade - 0.7f;
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
  public void addParticle() {
    particles.add(new Particle(origin));
  }
  public void run() {
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
  int c;
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
    if (k==0) c = color(0xffF2A25C);
    if (k==1) c = color(0xffE55B4B);
    if (k==2) c = color(0xffFCA4A4);
    if (k==3) c = color(0xffFFEB9B);
    if (k==4) c = color(0xffB7A4FC);
    if (k==5) c = color(0xffFFFFFF);
    
    if (j <=4){
      radiant = true;
    }
    
    acceleration = new PVector(random(-0.03f, 0.03f), random(-0.03f, 0.03f));
    velocity = new PVector(random(-1, 1), random(-1, 1));
    position = l.copy();
    lifespan = 500.0f;
  }

  public void run() {
    update();
    display();
  }

  public void update() {
    if (position.x > (width) || position.x<0) {
      velocity.x = velocity.x * -1;
    }
    if (position.y > (height) || position.y<0) {
      velocity.y = velocity.y * -1;
    }
    velocity.add(acceleration);
    position.add(velocity);
    if (mode == 1){
      lifespan -= 1.0f;
    }
    if (mousePressed) {
      PVector mouse = new PVector(mouseX, mouseY);
      PVector dir = PVector.sub(mouse, position);

      dir.normalize();

      dir.mult(0.5f);
      if (!mousePressed){
        dir.x = dir.x + random(-0.7f, 0.7f);
        dir.y = dir.y + random(-0.7f, 0.7f);
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

  public void display() {
    fill(c, lifespan);
    ellipse(position.x, position.y, 2, 2);
    fill(c, lifespan/10);
    ellipse(position.x,position.y,6,6);
    if (radiant == true){
      fill(c, lifespan/60);
      ellipse(position.x,position.y,70,70);
    }
  }

  public boolean isDead() {
    if (lifespan < 0.0f) {
      return true;
    } else {
      return false;
    }
  }
}
  public void settings() {  size(800, 800);  smooth(5); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Dots" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
