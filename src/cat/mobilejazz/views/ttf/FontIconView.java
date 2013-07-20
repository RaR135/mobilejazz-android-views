package cat.mobilejazz.views.ttf;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import cat.mobilejazz.drawable.FontIconDrawable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.InflateException;
import android.widget.ImageView;

public class FontIconView extends ImageView {

	public FontIconView(Context context) {
		super(context);
	}

	public FontIconView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public FontIconView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		FontIconDrawable drawable = new FontIconDrawable();
		try {
			drawable.inflate(context.getResources(), null, attrs);
		} catch (XmlPullParserException e) {
			throw new InflateException(e);
		} catch (IOException e) {
			throw new InflateException(e);
		}
		setImageDrawable(drawable);
	}
}
