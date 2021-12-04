package com.vv.PNR_Enquiry_IndianRailways;

import java.util.logging.*;

/**
 * @author Vivek
 * @version 1.0
 * @since 01-08-2017
 */

public class LoggerFormatter {

    public static Logger formatTheLoggerOutput(Logger logger) {
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                //return record.getSourceClassName() + " :: " + record.getSourceMethodName() + " -- " + record.getMessage() + "\n";
                //changing this line for the DirtyRunner file testing
                return record.getMessage() + "\n";
            }
        });
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        return logger;
    }
}
