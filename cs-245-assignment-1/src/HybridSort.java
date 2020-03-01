/**
 * A class that utilizes both quick sort and insertion sort to sort an array.
 * The class will utilize quick sort while the array/sub-array is larger and will
 * utilize insertion sort when the array-sub-array is smaller.
 * @author Dan Tat
 *
 */
public class HybridSort {
	
	protected QuadraticSort quadSort=new QuadraticSort(); // Creates an instance of the quadratic sort class
	protected QuickSort quickSort=new QuickSort(); // Creates an instance of the quick sort class
	
	public void hybridsort (double[] arr,int left,int right) {
		
		// If the size of the array/sub-array is smaller than 10000****,
		// then quadratic sort is used.
		if(arr.length<10000)
			quadSort.sort(arr,left,right);
		// If the size of the array/sub-array is larger, then quick
		// sort is used.
		else
			quickSort.quickSort(arr,left,right);
	}
}
