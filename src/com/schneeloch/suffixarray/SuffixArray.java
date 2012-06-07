package com.schneeloch.suffixarray;

import java.util.ArrayList;
import java.util.Collections;


public class SuffixArray {

    private final SuffixDualList dualList = new SuffixDualList();

    public Iterable<ObjectWithString> search(String search) {
    	int start = 0;
    	int end = dualList.size() - 1;
    	
    	while (start < end) {
    		int mid = start + (end - start)/2;
    		String s = dualList.getSuffix(mid, search.length());
    		int comparison = s.compareToIgnoreCase(search);
    		if (comparison == 0) {
    			return getResults(mid, search);
    		} else if (comparison < 0) {
    			start = mid + 1;
    		} else {
    			end = mid - 1;
    		}
    	}
    	
    	return Collections.emptyList();
    }

	private Iterable<ObjectWithString> getResults(int mid, String search) {
		ArrayList<ObjectWithString> ret = new ArrayList<ObjectWithString>();

		ret.add(dualList.getStop(mid));
		int strLen = search.length();
		String s = dualList.getSuffix(mid, strLen);
		for (int i = mid - 1; i >= 0; i--) {
			if (!dualList.getSuffix(i, strLen).equalsIgnoreCase(s)) {
				break;
			}
			else
			{
				ret.add(dualList.getStop(i));
			}
		}
		
		for (int i = mid + 1; i < dualList.size(); i++) {
			if (!dualList.getSuffix(i, strLen).equalsIgnoreCase(s)) {
				break;
			}
			else
			{
				ret.add(dualList.getStop(i));
			}
		}
		return ret;
	}

	public void add(ObjectWithString stop) {
		dualList.add(stop);
	}

	public void sort() {
		dualList.sort();
	}
}