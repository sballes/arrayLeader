package com.sballestero;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class SolutionTest{

	private SolutionI s = new Solution();
	private SolutionI bruteForce = new SolutionBruteForce();
	
	@Test
	public void test1() {
		int[] sol = s.solution(3, 5, new int[]{2,1,3,1,2,2,3});
		Assert.assertArrayEquals(new int[] {2,3}, sol);
	}
	
	@Test
	public void test2() {
		int[] sol = s.solution(4, 2, new int[]{1,2,2,1,2});
		Assert.assertArrayEquals(new int[] {2,3}, sol);
	}
	
	@Test
	public void test3() {
		// Fijo valores que se que me van a generar arrays que cumplen las condiciones y no son muy costosos de calcular
		int N = 10;
		int M = 5;
		int K = 4;
		// Genero 50 problemas aleatorios y los resuelvo con ambos algoritmos para comprobar que dan el mismo resultado
		for (int i=0; i<50; i++) {
			testBothAlgorithms(M, N, K);
		}
	}
	
	@Test
	public void test4() {
		measureAlgorythm("Solution:", s);
		measureAlgorythm("bruteForce", bruteForce);
	}
	
	/** Testea que los dos algoritmos desarrollados dan la misma solucion a un problema */
	private void testBothAlgorithms(int M, int N, int K) {
		int[] a1 = generateRandomArray(M, N);
		int[] a2 = Arrays.copyOf(a1, N);
		
		int[] sol1 = s.solution(K, M, a1);
		int[] sol2 = bruteForce.solution(K, M, a2);
		Assert.assertArrayEquals(sol1, sol2);
		if (sol1.length > 0) {
			System.out.println("Leaders: " + Arrays.toString(sol1));
		}else {
			System.out.println("No leaders");
		}
	}

	/** Mide el tiempo de ejecucion de un algoritmo dado*/
	private void measureAlgorythm(String algorythmName, SolutionI algorythm) {
		int testIterations = 10;
		int M = 500;
		int K = 4;
		// Realizo pruebas para N=10, N=100; N=1000; N=10000; N=100000
		for (int N=10; N<=100000; N=N*10) {
			long solAverageTime =0l;
			// Realizo un numero de iteraciones para calcular una media
			for (int i=0; i<testIterations; i++) {
				int[] a1 = generateRandomArray(M, N);
				long l1 = System.currentTimeMillis();
				algorythm.solution(K, M, a1);
				long l2 = System.currentTimeMillis();
				solAverageTime+=(l2-l1);
			}
			System.out.println(algorythmName + ": For N: " + N +". Average time: " + (solAverageTime/testIterations) + "ms");
		}
	}
	
	/** Genera un array aleatorio de enteros como pide el problema */
	private int[] generateRandomArray(int M, int N) {
		Random r = new Random();
		return r.ints(N, 0, M).toArray();
	}
}
