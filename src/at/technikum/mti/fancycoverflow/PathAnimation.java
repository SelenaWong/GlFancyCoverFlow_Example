
/**   
 * @Title: PathAnimation.java 
 * @Package: at.technikum.mti.fancycoverflow 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年9月28日 上午10:11:25 
 * @version 1.3.1 
 */


package at.technikum.mti.fancycoverflow;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Animation;

/** 
 * @Description 
 * @author lenovo
 * @date 2016年9月28日 上午10:11:25 
 * @version V1.3.1
 */

public class PathAnimation extends Animation {
	
	private PathMeasure pathMeasure;
	private float position[] = new float[2];
	
	
	public PathAnimation( Path path){
		pathMeasure = new PathMeasure(path,false);
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, android.view.animation.Transformation t) {
	
		pathMeasure.getPosTan(pathMeasure.getLength()*interpolatedTime, position, null);
		t.getMatrix().setTranslate(position[0], position[1]);
		
	};

}
