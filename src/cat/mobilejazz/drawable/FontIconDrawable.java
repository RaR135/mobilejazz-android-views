package cat.mobilejazz.drawable;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.InflateException;
import cat.mobilejazz.views.R;
import cat.mobilejazz.views.ttf.TypefaceHelper;

public class FontIconDrawable extends Drawable {

	private Paint mPaint;
	private Typeface mTypeface;

	private char mCharacter;
	private int mColor;
	private float mTextSize;
	private String mText;

	private int mWidth;
	private int mHeight;

	private boolean mIsOpaque;

	public FontIconDrawable() {
	}

	public FontIconDrawable(Typeface typeface, int character, int color, float textSize) {
		this(typeface, (char) character, color, textSize);
	}

	public FontIconDrawable(Typeface typeface, char character, int color, float textSize) {
		init(typeface, character, color, textSize);
	}

	private void init(Typeface typeface, char character, int color, float textSize) {
		mTypeface = typeface;
		mPaint = new Paint();
		mPaint.setTypeface(mTypeface);
		mCharacter = character;
		mColor = color;
		mTextSize = textSize;
		mText = new String(new char[] { mCharacter });

		mPaint.setColor(mColor);
		mPaint.setTextSize(mTextSize);
		mWidth = (int) Math.ceil(mPaint.measureText(mText));
		mHeight = (int) Math.ceil(-mPaint.getFontMetrics().ascent + mPaint.getFontMetrics().descent);
		mIsOpaque = true;
	}

	@Override
	public int getIntrinsicWidth() {
		return mWidth;
	}

	@Override
	public int getIntrinsicHeight() {
		return mHeight;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawText(mText, 0, mHeight - mPaint.getFontMetrics().descent, mPaint);
	}

	@Override
	public void setAlpha(int alpha) {
		mPaint.setAlpha(alpha);
		mIsOpaque = alpha == 255;
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		return (mIsOpaque) ? PixelFormat.OPAQUE : PixelFormat.TRANSPARENT;
	}

	@Override
	public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException,
			IOException {
		super.inflate(r, parser, attrs);

		TypedArray a = r.obtainAttributes(attrs, R.styleable.TtfCapableView);

		String typefaceDesc = a.getString(R.styleable.TtfCapableView_typeface);
		Typeface typeface = TypefaceHelper.getTypeface(r, typefaceDesc);

		a.recycle();
		a = r.obtainAttributes(attrs, R.styleable.FontIconView);

		int color = a.getColor(R.styleable.FontIconView_color, Color.rgb(0, 0, 0));
		float textSize = a.getDimension(R.styleable.FontIconView_textSize, 32.0f);
		CharSequence character = a.getText(R.styleable.FontIconView_character);

		if (typefaceDesc == null) {
			throw new InflateException("Must provide a typeface");
		}

		if (character == null || character.length() > 1) {
			throw new InflateException("Must provide exactly one character");
		}

		init(typeface, character.charAt(0), color, textSize);

		a.recycle();
	}

}
