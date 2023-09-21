/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import javax.swing.SwingConstants;

/**
 *
 * @author PC
 */
public class NumberRenderer extends FormatRenderer {

    /*
	 *  Use the specified number formatter and right align the text
     */
    public NumberRenderer(NumberFormat formatter) {
        super(formatter);
        setHorizontalAlignment(SwingConstants.RIGHT);
    }
    public static final DateTimeFormatter GLOBAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter GLOBAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DecimalFormat GLOBAL_VIE_CURRENCY_FORMATTER = new DecimalFormat("###,### VND");
    public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("###,###");

    /*
	 *  Use the default currency formatter for the default locale
     */
    public static NumberRenderer getCurrencyRenderer() {

        return new NumberRenderer(GLOBAL_VIE_CURRENCY_FORMATTER);
    }

    public static NumberRenderer getNumberRenderer() {
        return new NumberRenderer(NUMBER_FORMAT);
    }

    /*
	 *  Use the default integer formatter for the default locale
     */
    public static NumberRenderer getIntegerRenderer() {
        return new NumberRenderer(NumberFormat.getIntegerInstance());
    }

    /*
	 *  Use the default percent formatter for the default locale
     */
    public static NumberRenderer getPercentRenderer() {
        return new NumberRenderer(NumberFormat.getPercentInstance());
    }

}
