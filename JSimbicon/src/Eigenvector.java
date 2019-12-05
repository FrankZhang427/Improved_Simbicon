
import java.io.PrintWriter;

/**
 * An Eigenvector class that can print and compare different eigenvectors 
 * based on their eigenvalues.
 * 
 * @author Frank
 *
 */

public class Eigenvector implements Comparable<Eigenvector> {
	double eval;
	double evec[];
	public Eigenvector (double val, double[] vec) {
		eval = val;
		evec = vec;
	}
	
	public void print (PrintWriter pw, int i) {
		i++;
		pw.println("The "+ i+ " eigenvalue is " + eval);
		pw.print("The "+ i+ " eigenvector is [ ");
		for (int j = 0; j < evec.length; j++) {
			pw.print(evec[j] + " ");
		}
		pw.println("]");
	}
	
	public int compareTo(Eigenvector e) {
		if (eval > e.eval) return -1;
		if (eval < e.eval) return +1;
		return 0;
	}
}
