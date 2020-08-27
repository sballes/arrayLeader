package com.sballestero;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solution implements SolutionI{
	
	@Override
	public int[] solution(int K, int M, int[] originalArray) {
		int N = originalArray.length;
		// We create a copy of the original array to not modify it in the code
		int[] A = Arrays.copyOf(originalArray, N);
		
		//printArray(0, A);
		
		// Map we will fill with the number of times each value appears in the array
		AuxMap auxMap = new AuxMap();
		Set<Integer> leaders = new HashSet<>();
		
		int targetTimes = N%2==0 ? N/2 : (N/2)+1;
		
		// 1.Apply initial segment and build auxiliar map
		Integer initialLeader = handleInitialSegment(K, N, A, targetTimes, auxMap);
		if (initialLeader != null) {
			leaders.add(initialLeader);
		}
		// 2. We iterate over k-1 to N in order to 'move' the segment one position at a time
		for (int i=K; i<N; i++) {
			handleOtherSegment(i, K, N, A, targetTimes, auxMap, leaders);
		}
		
		if (leaders.isEmpty()) {
			return new int[] {};
		}else {
			return leaders.stream().sorted().mapToInt(i->i).toArray();
		}
	}
	
	/**
	 * We iterate to the array making the first segment changes and creating
	 * an auxiliar map with all the times each value appears on the array 
	 * after the first segment modification)
	 * @param K
	 * 		Segment Size
	 * @param N
	 * 		Array size
	 * @param A
	 * 		Array
	 * @param targetTimes
	 * 		Number times an element must appear in the array to become leader
	 * @param auxMap
	 * 		Auxiliar map to count the number of times every element appears in the array
	 * 
	 * @return
	 * 		Leader of the array after applying the first segment or null if there is no leader
	 */
	private Integer handleInitialSegment(int K, int N, int[] A, int targetTimes, AuxMap auxMap) {
		Integer initialSolution = null;
		for (int i=0; i<N; i++) {
			// For the first k elements, I make changes on the array by adding 1 to each element. Then we fill the map
			if (i<K) {
				int newVal = A[i]+1;
				A[i] = newVal;
				int times = auxMap.increment(newVal);
				
				// We check if the leader is found (can happen if the segment's size is similar to the array)
				if (becomesLeader(times, targetTimes)) {
					initialSolution = newVal;
				}
			}
			// For the rest of the elements, we just add them to the map and check if they become leaders
			else {
				int val = A[i];
				int times = auxMap.increment(val);
				if (becomesLeader(times, targetTimes)) {
					initialSolution = val;
				}
			}
		}
		//printArray(1, A);
		return initialSolution;
	}
	
	/**
	 * Helper to 'move' the segment applied to the array:
	 * By doing this, we only have to change 2 values of all the array: 
	 * <ul>
	 * <li>i-k doesn't belongs to the new segment. Decrement value on the array and modify map accordingly</li>
	 * <li>i does belong to the array, Increment value on the array and modify map accordingly</li>
	 * </ul>
	 * @param i
	 * 		Position over which we are iterating
	 * @param K
	 * 		Segment Size
	 * @param N
	 * 		Array size
	 * @param A
	 * 		Array
	 * @param targetTimes
	 * 		Number times an element must appear in the array to become leader
	 * @param auxMap
	 * 		Auxiliar map to count the number of times every element appears in the array
	 * @param leaders
	 * 		Set with the already calculated leaders
	 */
	private void handleOtherSegment(int i, int K, int N, int[] A, int targetTimes, AuxMap auxMap, Set<Integer> leaders) {
		int v1 = A[i-K];
		int v2 = A[i];
			
		auxMap.decrement(v1);
		auxMap.decrement(v2);
		int times = auxMap.increment(v1-1);
		if (becomesLeader(times, targetTimes)) {
			leaders.add(v1-1);
		}	
		times = auxMap.increment(v2+1);
		if (becomesLeader(times, targetTimes)) {
			leaders.add(v2+1);
		}
		
		// We make the changes effective on the array for the next iteration
		A[i-K] = A[i-K]-1;
		A[i] = A[i]+1;
		//printArray(i- (K-1) + 1, A);
	}
	
	/**
	 * A value becomes leader if appears at least target times in the array
	 * It only return true where it is equals and not greater, because the 
	 * value is incremented 1 by 1. If it is greater it is already a leader
	 * 
	 * @param value
	 * 		Value to check if becomes leader
	 * 
	 * @param target
	 * 		Target value to reach to becme leader
	 * @return
	 * 	<tt>true if the value becomes leader, <tt>false</tt> otherwise
	 */
	private boolean becomesLeader(int value, int target) {
		return value==target;
	}
}
