package com.aghacks.dragoncave.handlers;


public class Timer{
    private final long NANO = 1000000000; // Number of nanoseconds in a second
  
    long start; // Tracks when the timer was started
    long end;  // Tracks when the timer ends
    
    private float seconds; // Number of seconds to wait
    private long nanoSec;
    public boolean active = false;
    
    public Timer(float seconds){
        this.seconds = seconds;
        this.nanoSec = (long) (seconds * NANO);
        start = end = -1; // I use negative one to see if the timer is active
    }

    // Activate the timer
    public void start(){
    	active = true;
        start = System.nanoTime();
        end  = start + nanoSec;
    }

    public boolean isDone(){
    	active = false;
        if (start > 0 && end > 0)
            if (System.nanoTime() >= end) {
                start = end = -1;
                return true;
            }

        return false;
    }
    
    public void setTime(float seconds){
    	this.seconds = seconds;
    	this.nanoSec = (long) (seconds*NANO);
    }
    
    public double elapsedTime(){
    	if(start == -1) return -1;
    	double tmp = System.nanoTime() - start;
    	double tmp2 = NANO;
    	return tmp/tmp2;
    }
    public float getSeconds(){
    	return seconds;
    }
}
