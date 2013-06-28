package de.jutzig.jabylon.ui.components;

import java.text.MessageFormat;

import com.vaadin.ui.Label;

public class StaticProgressIndicator extends Label {

    private static final String PATTERN = "<div class=\"jabylon-progress\">" //$NON-NLS-1$
            + "<div class=\"jabylon-progress-bar\" style=\"width:{0}%; background-color: {1};\">&nbsp;</div>" //$NON-NLS-1$
            + "<div class=\"jabylon-progress-label\">{0}%</div>" + "</div>"; //$NON-NLS-1$ //$NON-NLS-2$
    private int percentage;
    private boolean invert;

    public StaticProgressIndicator() {
        setContentMode(CONTENT_XHTML);
    }

    public void setPercentage(int percentage) {
        int rgb = calculateBackgroundColor(percentage);
        this.percentage = percentage;
        String color = Integer.toHexString(rgb);
        if (color.length() == 4) // pure green, no red left
            color = "00" + color; //$NON-NLS-1$
        else if (color.length() == 5)
            color = "0" + color; //$NON-NLS-1$
        setValue(MessageFormat.format(PATTERN, percentage, "#" + color)); //$NON-NLS-1$

    }


    public void setInvertColors(boolean invert)
    {
        this.invert = invert;
    }

    protected int calculateBackgroundColor(int percentage) {

        int ticks = (int) ((512 / 100.0) * percentage);
        int red, green;
        if(percentage==50) //exactly 50 => yellow
            return 0xFFFF00;
        if (percentage < 50) {
            // first, red stays at 100%, green raises to 100%
            red = 255;
            green = ticks;
        } else {
            // then green stays at 100% and red decays
            green = 255;
            red = 512 - ticks;
        }
        int color = 0;

        if(invert)
        {
            color = red;
            red = green;
            green = color;
            color = 0;
        }

        color += red << 16;
        color += green << 8;
        return color;

    }

    public int getPercentage() {
        return percentage;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof StaticProgressIndicator) {
            StaticProgressIndicator indicator = (StaticProgressIndicator) o;
            return getPercentage() - indicator.getPercentage();
        }
        return -1;
    }
}
