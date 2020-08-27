package com.sballestero;

import java.util.HashMap;

/**
 * Auxiliar class to
 *
 */
public class AuxMap extends HashMap<Integer, Integer>{

	private static final long serialVersionUID = -4041442149272258528L;
	
	int increment(Integer key) {
		Integer times = get(key);
		if (times == null) {
			times = 1;
		}else {
			times += 1;
		}
		put(key, times);
		return times;
	}
	
	int decrement(Integer key) {
		Integer times = get(key);
		// should not happen
		if (times == null) {
			times = 0;
		}else {
			times -= 1;
		}
		put(key, times);
		return times;
	}
}
