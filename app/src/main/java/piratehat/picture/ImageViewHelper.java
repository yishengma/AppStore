package piratehat.picture;

import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.lang.reflect.Field;

public class ImageViewHelper {

    //默认的图片宽高
    private static int DEFAULT_WIDTH = 200;
    private static int DEFAULT_HEIGHT = 200;

    /**
     * 获取ImageView控件的宽度
     * 1.getWidth（绘制完成，如果视图没有绘制完成没有值）
     * 2.layout_width（有可能设置的是WRAP_CONTENT）
     * 3.maxWidth
     * 拿到ImageView的宽高
     * @param imageView
     * @return
     */
    public static int getImageViewWidth(ImageView imageView) {
        if (imageView != null) {
            //			imageView.getWidth()==0;
            LayoutParams params = imageView.getLayoutParams();
            int width = 0;
            if (params != null && params.width != LayoutParams.WRAP_CONTENT) {
                width = imageView.getWidth();
            }

            if (width <= 0 && params != null) {
                width = params.width;
            }

            if (width <= 0) {
                width = getImageViewFieldValue(imageView, "mMaxWidth");
            }
            return width;
        }

        return DEFAULT_WIDTH;
    }

    /**
     * 获取图片的高度
     * @param imageView
     * @return
     */
    public static int getImageViewHeight(ImageView imageView) {
        if (imageView != null) {
            LayoutParams params = imageView.getLayoutParams();
            int height = 0;
            if (params != null && params.height != LayoutParams.WRAP_CONTENT) {
                height = imageView.getWidth();
            }
            if (height <= 0 && params != null) {
                height = params.height;
            }
            if (height <= 0) {
                height = getImageViewFieldValue(imageView, "mMaxHeight");
            }
            return height;
        }
        return DEFAULT_HEIGHT;
    }


    private static int getImageViewFieldValue(ImageView imageView, String fieldName) {
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(imageView);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                return fieldValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
