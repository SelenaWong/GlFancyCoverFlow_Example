
/**   
 * @Title: CustomCoverFlowAdpater.java 
 * @Package: at.technikum.mti.fancycoverflow 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年9月27日 上午10:48:49 
 * @version 1.3.1 
 */


package at.technikum.mti.fancycoverflow;

import java.util.List;

import com.app.glfancycoverflow.R;
import com.app.model.Product;
import com.gl.glfancycoverflow.UILApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/** 
 * @Description 
 * @author lenovo
 * @date 2016年9月27日 上午10:48:49 
 * @version V1.3.1
 */

public class CustomFancyCoverFlowAdpater extends FancyCircleCoverFlowAdapter{
	
    private final static int ImageLayoutWidth =160;//280
	private final static int ImageLayoutHeight =280;//500
	
    // =============================================================================
    // Private members
    // =============================================================================
	private List<Product> list;
	private ImageLoader imageLoader;
	private Context context;
	private LayoutInflater inflater;
	public CustomFancyCoverFlowAdpater( List<Product> proList,Context context) {
		super( proList);
		list = proList;
		this.context = context;
		imageLoader = ImageLoader.getInstance();
		inflater = LayoutInflater.from(context);
		// TODO Auto-generated constructor stub
	}

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
       CustomViewGroup customViewGroup = null;
        if (reuseableView != null) {
            customViewGroup = (CustomViewGroup) reuseableView;
        } else {
            customViewGroup = new CustomViewGroup(viewGroup.getContext());
            customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(ImageLayoutWidth, ImageLayoutHeight));
        }
		imageLoader.displayImage( list.get(i%list.size())
				.getProUrl(), customViewGroup.getImageView(),UILApplication.getDisplayImageOptions());
        customViewGroup.getTextView().setText(String.format("Item %d", i));
        return customViewGroup;
     }
    

	private static class CustomViewGroup extends LinearLayout {

	        // =============================================================================
	        // Child views
	        // =============================================================================
	        private TextView textView;

	        private ImageView imageView;

	        private Button button;
	        private LayoutInflater inflater;


	        // =============================================================================
	        // Constructor
	        // =============================================================================

	        private CustomViewGroup(Context context) {
	            super(context);
	            this.setOrientation(VERTICAL);
	            inflater = LayoutInflater.from(context);
	            View view =inflater.inflate(R.layout.item_gallery, null,false);
	            textView = (TextView) view.findViewById(R.id.coverflow_tv);
	            imageView = (ImageView) view.findViewById(R.id.coverflow_image);
	            button = (Button) view.findViewById(R.id.coverflow_btn);   
	            this.button.setText("￥3");
	            this.addView(view);
	        }

	        // =============================================================================
	        // Getters
	        // =============================================================================

	        private TextView getTextView() {
	            return textView;
	        }
	        
	        private ImageView getImageView() {
	            return imageView;
	        }
	    }

}
