package cn.mahjong.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
  * <p>
  *   TimeLevelConverter描述
  * </p>
  *  
  * @author chenwen
  * @since 0.0.1
  */
public class TimeLevelConverter extends ClassicConverter {

    /* (non-Javadoc)
     * @see ch.qos.logback.core.pattern.Converter#convert(java.lang.Object)
     */
    @Override
    public String convert(ILoggingEvent event) {
            int level = 1;
            switch (event.getLevel().levelInt) {
            case Level.DEBUG_INT:
                    level = 1;
                    break;
            case Level.INFO_INT:
                    level = 2;
                    break;
            case Level.ERROR_INT:
                    level = 8;
                    break;
            case Level.WARN_INT:
                    level = 4;
                    break;
            default:
                    break;
            }
            return level + "\037" + event.getTimeStamp() + "\037";
    }

}