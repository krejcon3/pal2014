package pal;

import java.util.ArrayList;

/**
 * Created by krejcir on 15.12.14.
 */
public class Node {
	public int name;
	public int index = 0;
	public ArrayList<Node> childs;
	public int weight = 0;
	public Node previous = null;
	public boolean instack = false;
	public int lowlink = 0;

	public Node(int name) {
		this.name = name;
		this.childs = new ArrayList<Node>();
	}

	public Node(int name, int weight) {
		this.name = name;
		this.weight = weight;
		this.childs = new ArrayList<Node>();
	}

	public void addChild(Node child) {
		this.childs.add(child);
	}

	public void reset() {
		this.index = 0;
		this.previous = null;
		this.instack = false;
		this.lowlink = 0;
	}
}
