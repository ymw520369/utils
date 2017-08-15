/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 *
 * 2016年9月1日 	
 */
package org.alan.utils;

import java.util.concurrent.CyclicBarrier;

/**
 *
 * 
 * @scene 1.0
 * 
 * @author Alan
 *
 */
public class Solver {
	// final int N;
	// final float[][] data;
	// final CyclicBarrier barrier;

	class Worker implements Runnable {
		int myRow;

		Worker(int row) {
			myRow = row;
		}

		public void run() {
			// while (!done()) {
			// processRow(myRow);
			//
			// try {
			// barrier.await();
			// } catch (InterruptedException ex) {
			// return;
			// } catch (BrokenBarrierException ex) {
			// return;
			// }
			// }
		}
	}

	public Solver(float[][] matrix) {
		// data = matrix;
		// N = matrix.length;
		// barrier = new CyclicBarrier(N,
		// new Runnable() {
		// public void run() {
		// mergeRows(...);
		// }
		// });
		// for (int i = 0; i < N; ++i)
		// new Thread(new Worker(i)).start();
		//
		// waitUntilDone();
	   }
}
