import java.io.*;

public class ExternalSort {
	
	public void externalSort(String inputFile,String outputFile,int n,int k) throws IOException {
	
		int numOfChunks=(int) Math.ceil(n/k);
		String[] tempFiles=new String[numOfChunks];
		createTempFiles(tempFiles);
		BufferedReader br=new BufferedReader(new FileReader(inputFile));
		
		try {
			
			// Sorts the initial chunks and places them into a temp file
			for(int i=0;i<numOfChunks;i++) {
				
				float[] array=new float[k];
				// Reads k-number of elements and places them into an array
				for(int j=0;j<k;j++) {
					
					String line=br.readLine();
					array[j]=Float.valueOf(line);
				}
				sort(array);
				writeToTempFile(tempFiles[i],array);
			}
			
			// Merges adjacent chunks.
			// Continues to merge chunks until they are all merged back into one
			while(numOfChunks>1) {
				
				// Prepares files for tempFiles to be merged into
				String[] mergedFiles=new String[(int) Math.ceil(numOfChunks/2)];
				createMergeFiles(mergedFiles);
				int mergeFileIndex=0;
				for(int j=0;j<numOfChunks;j+=2) {
					
					chunkMergeSort(mergedFiles[mergeFileIndex],tempFiles[j],tempFiles[j+1]);
					++mergeFileIndex;
				}
				// tempFiles is set to mergedFiles after all chunks in this iteration have been merged
				numOfChunks=(int) Math.ceil(numOfChunks/2);
				tempFiles=new String[mergedFiles.length];
				
				// Moves merged files to temp files so that the problem of
				// same name files is mitigated
				createTempFiles(tempFiles);
				for(int j=0;j<tempFiles.length;j++) {
					
					mergeFileToTempFile(mergedFiles[j],tempFiles[j]);
				}
			}
			
			copyToFile(tempFiles[0],outputFile);
		}
		finally {
			
			br.close();
		}
	}
	
	// Sorts the given array
	public void sort(float[] a) {
		
		if(a.length<2) {
			return;
		}
		// Splits the array into two sub-arrays
		int leftSize=a.length/2;
		int rightSize=a.length-leftSize;
		float[] left=new float[leftSize];
		float[] right=new float[rightSize];
		copyArray(a,left,right);
		
		sort(left);
		sort(right);
		
		mergeSort(a,left,right);
	} // sort - apart of merge sort implementation
	
	// Merges the two smaller arrays into one sorted array
	public void mergeSort(float[] target,float[] left,float[] right) { 
		
		int indexL=0;
		int indexR=0;
		int indexTarget=0;
		
		while(indexL<left.length && indexR<right.length) {
			if(left[indexL]<=right[indexR])
				target[indexTarget++]=left[indexL++];
			else
				target[indexTarget++]=right[indexR++];
		}
		
		while(indexL<left.length)
			target[indexTarget++]=left[indexL++];
		while(indexR<right.length)
			target[indexTarget++]=right[indexR++];
	} // merge sort - apart of merge sort implementation
	
	// Copies elements from array 'a' to their respective sub-arrays
	public void copyArray(float[] a,float[] left,float[] right) { 
		
		for(int i=0;i<left.length;i++)
			left[i]=a[i];
		for(int i=0;i<right.length;i++)
			right[i]=a[i+left.length];
	} // copyArray - apart of merge sort implementation
	
	// Writes the given chunk into the temp file
	public void writeToTempFile(String filename,float[] array) throws IOException{
		
		BufferedWriter bw=new BufferedWriter(new FileWriter(filename));
		for(int i=0;i<array.length;i++) {
			
			bw.write(String.valueOf(array[i]));
			bw.newLine();
		}
		bw.close();
	}
	
	// Creates the temp files needed to record each chunk created
	public void createTempFiles(String[] tempFiles) throws IOException {
		
		for(int i=0;i<tempFiles.length;i++) {
			
			File file=new File("temp"+i+".txt");
			file.createNewFile();
			tempFiles[i]="temp"+i+".txt";
		}
	}
	
	// Creates temporary files to hold the merged temp files (merged chunks)
	public void createMergeFiles(String[] mergeFiles) throws IOException{
		
		for(int i=0;i<mergeFiles.length;i++) {
			
			File file=new File("merge"+i+".txt");
			file.createNewFile();
			mergeFiles[i]="merge"+i+".txt";
		}
	}
	
	// Merges the two smaller chunks into one sorted chunk.
	// The resultant chunk is placed into a new file.
	public void chunkMergeSort(String targetFile,String leftFile,String rightFile) throws IOException { 
		
		BufferedReader file1=new BufferedReader(new FileReader(leftFile));
		BufferedReader file2=new BufferedReader(new FileReader(rightFile));
		BufferedWriter bw=new BufferedWriter(new FileWriter(targetFile));
		String line1=file1.readLine();
		String line2=file2.readLine();
		
		while(line1!=null && line2!=null) {
			
			if(Float.valueOf(line1)<=Float.valueOf(line2)) {
				
				bw.write(line1);
				bw.newLine();
				line1=file1.readLine(); // Goes to the next element in the left chunk
			}
			else {
				
				bw.write(line2);
				bw.newLine();
				line2=file2.readLine(); // Goes to the next element in the right chunk
			}
		}
		
		while(line1!=null) {
			
			bw.write(line1);
			bw.newLine();
			line1=file1.readLine();
		}
		while(line2!=null) {
			
			bw.write(line2);
			bw.newLine();
			line2=file2.readLine();
		}
		
		file1.close();
		file2.close();
		// Deletes the temp files, as they are no longer needed
		File temp1=new File(leftFile);
		temp1.delete();
		File temp2=new File(rightFile);
		temp2.delete();
		bw.close();
	} // chunk merge sort
	
	// Copies elements from an input file to an output file
	public void copyToFile(String input,String output) throws IOException {
		
		BufferedReader br=new BufferedReader(new FileReader(input));
		BufferedWriter bw=new BufferedWriter(new FileWriter(output));
		String line;
		
		while((line=br.readLine())!=null) {
			
			bw.write(line);
			bw.newLine();
		}
		br.close();
		bw.close();
		File temp=new File(input);
		temp.delete(); // Deletes the temp file, as it is no longer needed
	}
	
	public void mergeFileToTempFile(String merge,String temp) throws IOException {
		
		BufferedReader br=new BufferedReader(new FileReader(merge));
		BufferedWriter bw=new BufferedWriter(new FileWriter(temp));
		String line;
		
		while((line=br.readLine())!=null) {
			
			bw.write(line);
			bw.newLine();
		}
		br.close();
		File mergeFile=new File(merge);
		mergeFile.delete(); // Deletes the merge file, as it is no longer needed
		bw.close();
	}
	
	// Driver Code
	public static void main(String[] args) throws IOException {
		
		ExternalSort extSort=new ExternalSort();
		extSort.externalSort("C:\\Users\\Dan Tat\\eclipse-workspace\\cs-245-assignment-1\\src\\input.txt",
				"C:\\Users\\Dan Tat\\eclipse-workspace\\cs-245-assignment-1\\src\\output.txt",20,5);
	}
}
