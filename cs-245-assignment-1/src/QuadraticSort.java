/**
 * A class that sorts a given array by using a pivot and partitioning 
 * the array around the chosen pivot. This is an implementation of insertion sort.
 * Of the three quadratic sort algorithms (bubble, selection, insertion), insertion sort
 * has the quickest consistent run time.
 * @author Dan Tat
 *
 */
public class QuadraticSort {

	public void sort(double[] a,int left,int right) {
		
		double temp;
		for(int i=1;i<a.length;i++) {
			temp=a[i]; // Holds the value of the element that is being sorted
			int j=1;
			while(i>=j && temp<a[i-j]) { // Shifts elements that are greater than the element being sorted to the right
				a[i-(j-1)]=a[i-j];
				++j;
			}
			a[i-(j-1)]=temp; // Places the element being sorted into its correct position in the array
		}
	}
}