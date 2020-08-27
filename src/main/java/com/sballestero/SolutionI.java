package com.sballestero;

import java.util.Arrays;

public interface SolutionI {
	
	public int[] solution(int K, int M, int[] A);
	
	default void printArray(int iteration, int[] array) {
		StringBuffer sb = new StringBuffer();
		sb.append("Iteration ");
		sb.append(iteration);
		sb.append(": ");
		sb.append(Arrays.toString(array));
		System.out.println(sb.toString());
	}
}
