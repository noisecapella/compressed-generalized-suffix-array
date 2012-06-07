package com.schneeloch.suffixarray;

import java.util.ArrayList;

import cern.colt.Sorting;
import cern.colt.function.IntComparator;
import cern.colt.list.IntArrayList;

/**
 * Presents a list of stops, sorted by suffix (after sort() is called)
 * @author schneg
 *
 */
public class SuffixDualList implements IntComparator{
	private final CompressedList<ObjectWithString> compressedList = new CompressedList<ObjectWithString>();
    private int[] indexes;

    public void add(ObjectWithString objectWithString) {
    	int len = objectWithString.getString().length();
    	compressedList.add(objectWithString, len);
	}
    
    /**
     * must be done right after adding, before anything else
     */
    public void sort() {
    	if (indexes == null)
    	{
    		indexes = new int[compressedList.size()];
			for (int i = 0; i < indexes.length; i++)
			{
				indexes[i] = i;
			}
    	}
    	
    	Sorting.mergeSort(indexes, 0, indexes.length - 1, this);
    }
    
    public ObjectWithString getStop(int i) {
    	int index = indexes[i];
    	
    	return compressedList.get(index);
    }
    
    /**
     * Get suffix at index i, then truncate to strLen
     * @param i
     * @param strLen
     * @return
     */
    public String getSuffix(int i, int strLen) {
    	int index = indexes[i];

    	ObjectWithString objectWithString = compressedList.get(index);
    	int startIndex = compressedList.getStartIndex(index);
    	int position = index - startIndex; 

    	String string = objectWithString.getString();
    	int end = Math.min(string.length(), strLen + position);
    	return string.substring(position, end);
    }

	@Override
	public int compare(int arg0, int arg1) {
		String str0 = compressedList.get(arg0).getString();
		int p0 = arg0 - compressedList.getStartIndex(arg0);
		String sub0 = str0.substring(p0);

		String str1 = compressedList.get(arg1).getString();
		int p1 = arg1 - compressedList.getStartIndex(arg1);
		String sub1 = str1.substring(p1);

		int ret = sub0.compareToIgnoreCase(sub1);
		return ret;
	}

	public int size() {
		return compressedList.size();
	}
}
