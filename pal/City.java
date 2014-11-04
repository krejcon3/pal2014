package pal;

import java.util.HashSet;

/**
 * Created by krejcir on 2.11.14.
 */
public class City {
	public int index;
	public HashSet<int[]> edges;
	public boolean hasOyster = false;

	public City() {
		this.edges = new HashSet<int[]>();
	}

	public City(int index) {
		this.index = index;
		this.edges = new HashSet<int[]>();
	}
}
