package com.sballestero;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SolutionBruteForce implements SolutionI{
	
	public int[] solution(int K, int M, int[] originalArray) {
		int N = originalArray.length;
		// We create a copy of the original array to not modify it in the code
		int[] A = Arrays.copyOf(originalArray, N);
		
		Set<Integer> leaders = new HashSet<>();
		int targetTimes = N%2==0 ? N/2 : (N/2)+1;
		for (int i=0; i<N; i++) {
			if (i<K-1) {
				A[i] = A[i]+1;
			}else if (i== (K-1)) {
				A[i] = A[i]+1;
				addLeader(findLeader(A, N, targetTimes), leaders);
			}else {
				A[i-K] = A[i-K]-1;
				A[i] = A[i]+1;
				addLeader(findLeader(A, N, targetTimes), leaders);
			}	
		}
		
		if (leaders.isEmpty()) {
			return new int[] {};
		}else {
			return leaders.stream().sorted().mapToInt(i->i).toArray();
		}
	}
	
	private void addLeader(int candidate, Set<Integer> leaders) {
		if (candidate != -1) {
			leaders.add(candidate);
		}
	}
	
	private int findLeader(int[] A, int N, int targetTimes) {
		int leader = -1;
		AuxMap auxMap = new AuxMap();
		
		for (int i=0; i<N && leader==-1; i++) {
			int times = auxMap.increment(A[i]);
			if (becomesLeader(times, targetTimes)){
				leader = A[i];
			}
		}
		return leader;
	}
	
	private boolean becomesLeader(int value, int target) {
		return value==target;
	}
}
