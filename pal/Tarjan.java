package pal;

import java.util.ArrayList;

public class Tarjan {
	private Node stackTop;
	private int index;
	private int pointCount;
	public int maxTotal = 0;
	private ArrayList<Node> nodes;


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

	public void run(int[] edge) {
		this.resetData();
		nodes.get(edge[1]).addChild(nodes.get(edge[0]));
		nodes.get(edge[0]).childs.remove(nodes.get(edge[1]));
		for (int i = 1; i <= this.pointCount; i++) {
			this.stackTop = null;
			this.index = 0;
			Node no = this.nodes.get(i - 1);
			if (no.index == 0) {
				this.find_scc(no);
			}
		}
		nodes.get(edge[0]).addChild(nodes.get(edge[1]));
		nodes.get(edge[1]).childs.remove(nodes.get(edge[0]));
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

	private void find_scc(Node n) {
		n.index = ++this.index;
		n.lowlink = n.index;
		this.push(n);
		for (Node child : n.childs) {
			if (child.index == 0) {
				this.find_scc(child);
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
				ccount++;
				if (x.index == x.lowlink) {
					break;
				}
			}
			if (ccount > 2 && maxTotal < total) {
				maxTotal = total;
			}
		}
	}
}
