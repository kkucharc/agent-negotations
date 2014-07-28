package start;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    public static synchronized void log(String message) { 
      PrintWriter out;
	try {
		out = new PrintWriter(new FileWriter("output.txt"), true);
	      out.write(message);
	      out.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

	}
}
