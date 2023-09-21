/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.text.DateFormat;
import java.text.Format;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author PC
 */
public class FormatRenderer extends DefaultTableCellRenderer {

    private Format formatter;

    /*
	 *   Use the specified formatter to format the Object
     */
    public FormatRenderer(Format formatter) {
        this.formatter = formatter;
    }

    public void setValue(Object value) {
        //  Format the Object before setting its value in the renderer

        try {
            if (value != null) {
                value = formatter.format(value);
            }
        } catch (IllegalArgumentException e) {
        }

        super.setValue(value);
    }

    /*
	 *  Use the default date/time formatter for the default locale
     */
    public static FormatRenderer getDateTimeRenderer() {
        return new FormatRenderer(DateFormat.getDateTimeInstance());
    }

    /*
	 *  Use the default time formatter for the default locale
     */
    public static FormatRenderer getTimeRenderer() {
        return new FormatRenderer(DateFormat.getTimeInstance());
    }
}
