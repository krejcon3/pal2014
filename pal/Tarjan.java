package pal;

import java.util.ArrayList;

public class Tarjan {
	private Node stackTop;
	private int index;
	private int pointCount;
	public int maxTotal = 0;
	private ArrayList<Node> nodes;
	private long possible = 0;
	private long maximum;


	public Tarjan(int pointCount, ArrayList<Node> nodes) {
		this.pointCount = pointCount;
		this.nodes = nodes;
		this.resetData();
	}

	private void resetData() {
		this.stackTop = null;
		this.index = 0;
		this.maxTotal = 0;
	}

	public void run(int[] edge, long possible, long maximum) {
		this.maximum = maximum;
		this.possible = possible;
		this.resetData();
		Node start = nodes.get(edge[1]);
		Node end = nodes.get(edge[0]);
		start.addChild(end);
		end.childs.remove(start);
		for (Node no : this.nodes) {
			this.stackTop = null;
			this.index = 0;
			if (no.index == 0) {
				this.find_scc(no);
				if (this.possible < this.maxTotal || this.possible <= this.maximum) {
					break;
				}
			}
		}
		end.addChild(start);
		start.childs.remove(end);
		for (Node n : this.nodes) {
			n.reset();
		}
	}

	private void push(Node n) {
		n.previous = this.stackTop;
		n.instack = true;
		this.stackTop = n;
	}

	private Node pop(Node n) {
		this.stackTop = n.previous;
		n.previous = null;
		n.instack = false;
		return n;
	}

	private int find_scc(Node n) {
		n.index = ++this.index;
		n.lowlink = n.index;
		this.push(n);
		for (Node child : n.childs) {
			if (child.index == 0) {
				this.find_scc(child);
				if (this.possible < this.maxTotal || this.possible + this.maxTotal <= this.maximum) {
					return this.maxTotal;
				}
				n.lowlink = Math.min(n.lowlink, child.lowlink);
			} else if (child.instack) {
				n.lowlink = Math.min(n.lowlink, child.index);
			}
		}
		if (n.lowlink == n.index) {
			Node x;
			int total = 0;
			int ccount = 0;
			while (this.stackTop != null) {
				x = this.pop(this.stackTop);
				total += x.weight;
				this.possible -= x.weight;
				ccount++;
				if (x.index == x.lowlink) {
					break;
				}
			}
			if (ccount > 2 && this.maxTotal < total) {
				this.maxTotal = total;
			}
			if (this.possible < this.maxTotal || this.possible + this.maxTotal <= this.maximum) {
				return this.maxTotal;
			}
		}
		return 0;
	}
}
