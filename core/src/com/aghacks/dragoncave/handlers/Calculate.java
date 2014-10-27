package com.aghacks.dragoncave.handlers;

public class Calculate {
	static public final float map(float value, 
            float istart, 
            float istop, 
            float ostart, 
            float ostop) {
return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
}
	
	public static float random(float min, float max) {
		  return (float) (Math.random() * (max-min) + min);
		}
}
