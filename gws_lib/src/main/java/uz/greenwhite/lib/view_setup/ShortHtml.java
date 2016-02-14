package uz.greenwhite.lib.view_setup;

import android.text.Html;

import uz.greenwhite.lib.error.AppError;

public class ShortHtml {

    final StringBuilder sb;

    private boolean bold;
    private boolean italic;
    private boolean red;
    private boolean color;


    public ShortHtml() {
        sb = new StringBuilder();
        bold = false;
        italic = false;
    }

    public ShortHtml v(CharSequence s) {
        sb.append(s);
        return this;
    }

    public ShortHtml b() {
        if (bold) {
            sb.append("</b>");
            bold = false;
        } else {
            sb.append("<b>");
            bold = true;
        }
        return this;
    }

    public ShortHtml i() {
        if (italic) {
            sb.append("</i>");
            italic = false;
        } else {
            sb.append("<i>");
            italic = true;
        }
        return this;
    }

    public ShortHtml br() {
        sb.append("<br/>");
        return this;
    }

    public ShortHtml fRed() {
        if (red) {
            sb.append("</font>");
            red = false;
        } else {
            sb.append("<font color='#990000'>");
            red = true;
        }
        return this;
    }

    public ShortHtml c() {
        if (color) {
            sb.append("</font>");
            color = false;
        } else {
            throw AppError.Unsupported();
        }
        return this;
    }

    public ShortHtml c(String cl) {
        if (color) {
            throw AppError.Unsupported();
        } else {
            sb.append("<font color='").append(cl).append("'>");
            color = true;
        }
        return this;
    }

    public CharSequence html() {
        return Html.fromHtml(sb.toString());
    }
}
