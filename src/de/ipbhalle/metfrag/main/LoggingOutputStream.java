/*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
*/

package de.ipbhalle.metfrag.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An OutputStream that writes contents to a Logger upon each call to flush()
 */
class LoggingOutputStream extends ByteArrayOutputStream {
    
    private String lineSeparator;
    
    private Logger logger;
    private Level level;
    
    /**
     * Constructor
     * @param logger Logger to write to
     * @param level Level at which to write the log message
     */
    public LoggingOutputStream(Logger logger, Level level) {
        super();
        this.logger = logger;
        this.level = level;
        lineSeparator = System.getProperty("line.separator");
    }
    
    /**
     * upon flush() write the existing contents of the OutputStream to the logger as 
     * a log record.
     * @throws java.io.IOException in case of error
     */
    public void flush() throws IOException {

        String record;
        synchronized(this) {
            super.flush();
            record = this.toString();
            super.reset();
        }
        
        if (record.length() == 0 || record.equals(lineSeparator)) {
            // avoid empty records
            return;
        }

        logger.logp(level, "", "", record);
    }
}
