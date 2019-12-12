
import Jama.Matrix;
import java.util.Arrays;
import Jama.EigenvalueDecomposition;
import java.io.File;
import java.io.PrintWriter;

/**
 * This is a helper class to do PCA on the given data
 * 
 * @author Frank
 */


public class PCA {
	Matrix data;
	float mean[];
	Matrix cov;
	int nrSample = 10000;  //default value
	int dim = 7; //default value
	
	EigenvalueDecomposition evd;
	Matrix V;
	Matrix D;
	Eigenvector evec[];
	
	public PCA() {
	}
	
	public PCA(Matrix m) {
		data = m.copy();
		nrSample = m.getRowDimension();
		dim = m.getColumnDimension();
		mean = new float[dim];
	}
	
	// center the observed data around mean
	public void center() {
		// calculate mean vector
		for (int j = 0; j < dim; j++) {
			for (int i = 0; i < nrSample; i++) {
				mean[j] += data.get(i, j);
			}
			mean[j] /= nrSample;
		}
		// center data
		for (int j = 0; j < dim; j++) {
			for (int i = 0; i < nrSample; i++) {
				data.set(i, j, data.get(i, j) - mean[j]);
			}
		}
	}
	
	// compute covariance matrix
	public void covariance() {
		cov = new Matrix(dim, dim);
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				float sum = 0;
				for (int k = 0; k < nrSample; k++) {
					sum += data.get(k,i) * data.get(k, j);
				}
				cov.set(i, j, sum/(nrSample - 1));
				cov.set(j, i, sum/(nrSample - 1));
			}
		}
	}
	
	// compute eigenvalue-decomposition
	public void eigen() {
		evd = cov.eig();
		V = evd.getV();
		V = V.transpose(); // easier to extract eigenvectors from columns
		D = evd.getD();
		double real[] = evd.getRealEigenvalues();
		System.out.println("Real eigenvalues are :");
		System.out.println(Arrays.toString(real));
		double evec_array[][] = V.getArray();
		evec = new Eigenvector[real.length];
		for (int i = 0; i < real.length; i++) {
			evec[i] = new Eigenvector(real[i], evec_array[i]);
		}
		Arrays.sort(evec);
		V = V.transpose(); // reverse the transpose
	}
	
	// print the pca information
	public void print() {
		try {
			File file = new File( "pca/pca_Cov.txt" );                                             
	        file = new File(file.getAbsolutePath().trim()); 
	        PrintWriter pw = new PrintWriter(file);
        		cov.print(pw, 10, 5);
        		pw.close();
        } catch (Exception e) {    
            System.err.println("trouble writing Covariance matrix");
            e.printStackTrace();
            return;
        }
		try {
			File file = new File( "pca/pca_V.txt" );                                             
	        file = new File(file.getAbsolutePath().trim()); 
	        PrintWriter pw = new PrintWriter(file);
        		V.print(pw, 10, 5);
        		pw.close();
        } catch (Exception e) {    
            System.err.println("trouble writing V matrix");
            e.printStackTrace();
            return;
        }
		try {
			File file = new File( "pca/pca_D.txt" );                                             
	        file = new File(file.getAbsolutePath().trim()); 
	        PrintWriter pw = new PrintWriter(file);
        		D.print(pw, 10, 5);
        		pw.close();
        } catch (Exception e) {    
            System.err.println("trouble writing D matrix");
            e.printStackTrace();
            return;
        }
		try {
			File file = new File( "pca/pca_evec.txt" );                                             
	        file = new File(file.getAbsolutePath().trim()); 
	        PrintWriter pw = new PrintWriter(file);
        		for (int i = 0; i < evec.length; i++) {
        			evec[i].print(pw, i);
        		}
        		pw.close();
        } catch (Exception e) {    
            System.err.println("trouble writing eigenvectors");
            e.printStackTrace();
            return;
        }
	}
}
