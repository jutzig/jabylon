package de.jutzig.jabylon.rest.ui.wicket.components;

import org.apache.wicket.markup.html.link.Link;

public class PlainLink extends Link<String> {

    private CharSequence path;

    public PlainLink(String id, CharSequence path) {
        super(id);
        this.path = path;
    }

    public PlainLink(String id, String... segments) {
        this(id, createPath(segments));
    }

    private static StringBuilder createPath(String[] segments) {
        StringBuilder builder = new StringBuilder();
        if(segments==null || segments.length==0)
            return builder;
        for (String segment : segments) {
            builder.append(segment);
            builder.append("/"); //$NON-NLS-1$
        }
        builder.setLength(builder.length()-1);
        return builder;
    }

    @Override
    public void onClick() {
        // nothing to do

    }

    @Override
    protected CharSequence getURL() {
        return path;
    }

    @Override
    protected boolean getStatelessHint() {
        return true;
    }

}
