/**
 * A class that sorts a given array by using a pivot and partitioning 
 * the array around the chosen pivot.
 * @author Dan Tat
 *
 */
import java.util.Random;

public class QuickSort {

	protected QuadraticSort quadSort=new QuadraticSort(); // Creates an instance of the quadratic sort class
	
	public void sort(double[] a) {
		
		quickSort(a,0,a.length-1);
	}
	
	// Sorts the array or given sub-array from index 'start' to index 'end'	
	public void quickSort(double[] a,int start,int end) { 
		
		// If the array/sub-array is too small, quadratic sort will be called.
		if(a.length<10000)
			quadSort.sort(a,start,end);
		else {
			int pivot=partition(a,start,end);
			quickSort(a,start,pivot-1);
			quickSort(a,pivot+1,end);
		}
	} // quickSort
	
	/* Moves the 'pivot' element to the correct index and
	   moves smaller elements to the left of the 'pivot'
	   and larger elements to the right of the 'pivot' */
	public int partition(double[] a,int start,int end) {
		
		Random r=new Random();
		int pivot=r.nextInt(((end-start)+1)-start); // Randomly assigns the pivot index
		int right=end;
		
		for(int i=end;i>start;i--) {
			// Checks if the current element is bigger than the pivot
			if(a[i]>a[pivot]) {
				swap(a,i,right);
				right--;
			}
		}
		swap(a,right,pivot);
		
		// Returns index of 'pivot'
		return right; 
	} // partition
	
	// Swaps the elements of the two given indices
	public void swap(double[] a,int i,int j) {
		
		double temp=a[i];
		a[i]=a[j];
		a[j]=temp;
	} // swap
}
