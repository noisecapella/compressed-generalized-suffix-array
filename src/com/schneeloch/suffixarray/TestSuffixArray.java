package com.schneeloch.suffixarray;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class TestSuffixArray {
	private class Tuple implements ObjectWithString {
		private final int lineNum;
		private final String line;

		public Tuple(int wordNum, String line) {
			this.lineNum = wordNum;
			this.line = line;
		}

		@Override
		public String getString() {
			return line;
		}

		public int getLineNum() {
			return lineNum;
		}
	}

	public static void main(String[] args) {
		new TestSuffixArray().run(args);
	}
	
	public void run(String[] args) {
		if (args.length == 0) {
			args = new String[] {"test-data/pg2701.txt.gz"};

		}

		System.out.println("Reading test data...");
		try
		{
			InputStream in = new FileInputStream(args[0]); 
			try
			{
				GZIPInputStream gzipIn = new GZIPInputStream(in);
				in = gzipIn;
			}
			finally
			{
				// TODO: what would crash GZIPInputStream?
			}
			StringBuilder builder = new StringBuilder();
			byte[] buffer = new byte[4096];
			int len;
			while ((len = in.read(buffer, 0, buffer.length)) > 0) {
				builder.append(new String(buffer));
			}
			
			System.out.println("Splitting into lines...");
			String s = builder.toString();
			String[] lines = s.split("\n");
			ArrayList<Tuple> tuples = new ArrayList<Tuple>();
			int i = 0;
			for (String line : lines) {
				Tuple tuple = new Tuple(i, line);
				tuples.add(tuple);
				i++;
			}
			
			System.out.println("Creating suffix array from tuples...");
			SuffixArray suffixArray = new SuffixArray(true);
			for (Tuple tuple : tuples) {
				suffixArray.add(tuple);
			}
			System.out.println("Suffix array is size " + suffixArray.size() + ". Sorting suffix array...");
			suffixArray.sort();
			
			String key = "whale";
			System.out.println("Searching for " + key + "...");
			for (ObjectWithString result : suffixArray.search(key)) {
				Tuple tuple = (Tuple)result;
				System.out.println("Line: " + tuple.getLineNum() + ", " + tuple.getString());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
