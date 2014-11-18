package pal;

import java.io.*;
import java.util.*;

public class Main {

	public static int N;
	public static int D1;
	public static int D2;
	public static HashMap<String, PriorityQueue<Integer>> map;
	public static HashSet<String> vocabulary;

    public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream("./src/test/4/pub05.in"));
		read();
	}

	public static void read() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		StringTokenizer st;
		if ((line = br.readLine()) != null) {
			st = new StringTokenizer(line);
			N = Integer.parseInt(st.nextToken());
			D1 = Integer.parseInt(st.nextToken());
			D2 = Integer.parseInt(st.nextToken());
		}
		map = new HashMap<String, PriorityQueue<Integer>>(2 * N);
		vocabulary = new HashSet<String>(2 * N * D2);
		int rowId;
		String setIdentificator;
		String setIdentificatorReversed;
		String diskIdentificator;
		String diskIdentificatorReversed;
		String string = "";
		PriorityQueue<Integer> ids;
		int count = 0;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			rowId = Integer.parseInt(st.nextToken());
			setIdentificator = "";
			setIdentificatorReversed = "";
			for (int j = 0; j < D2; j++) {
				string = st.nextToken();
				diskIdentificator = getToken(string);
				if (!vocabulary.contains(diskIdentificator)) {
					diskIdentificatorReversed = getToken(new StringBuilder(string).reverse().toString());
					if (!vocabulary.contains(diskIdentificatorReversed)) {
						vocabulary.add(diskIdentificator);
						setIdentificator += diskIdentificator;
						setIdentificatorReversed = diskIdentificator + "" + setIdentificatorReversed;
					} else {
						setIdentificator += diskIdentificatorReversed;
						setIdentificatorReversed = diskIdentificatorReversed + "" + setIdentificatorReversed;
					}
				} else {
					setIdentificator += diskIdentificator;
					setIdentificatorReversed = diskIdentificator + "" + setIdentificatorReversed;
				}
			}
			setIdentificator = getToken(setIdentificator);
			setIdentificatorReversed = getToken(setIdentificatorReversed);
			if (!map.containsKey(setIdentificator)) {
				if (!map.containsKey(setIdentificatorReversed)) {
					ids = new PriorityQueue<Integer>(2 * N, new Comparator<Integer>() {
						@Override
						public int compare(Integer integer, Integer integer2) {
							if (integer > integer2) {
								return 1;
							} else if(integer < integer2) {
								return -1;
							} else {
								return 0;
							}
						}
					});
					ids.add(rowId);
					map.put(setIdentificator, ids);
					count++;
				} else {
					map.get(setIdentificatorReversed).add(rowId);
				}
			} else {
				map.get(setIdentificator).add(rowId);
			}
		}
		ArrayList<PriorityQueue<Integer>> list = new ArrayList<PriorityQueue<Integer>>(map.values());
		Collections.sort(list,
				new Comparator<PriorityQueue<Integer>>() {
					@Override
					public int compare(PriorityQueue<Integer> first, PriorityQueue<Integer> second) {
						if (first.size() > second.size()) {
							return -1;
						} else if (first.size() < second.size()) {
							return 1;
						} else {
							if (first.peek() > second.peek()) {
								return 1;
							} else {
								return -1;
							}
						}
					}
				}
				);
		System.out.println(count);
		for (PriorityQueue<Integer> s : list) {
			for (int i : s) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}

	/*
	 * Booth's Algorithm
	 * http://en.wikipedia.org/wiki/Lexicographically_minimal_string_rotation
	 */
	public static String getToken(String word) {
		int n = word.length();
		word = Character.MAX_VALUE + word + word;
		int[] f = new int[2 * n + 1];
		Arrays.fill(f, -1);
		int k = 0;
		for (int j = 1; j < 2 * n + 1; j++) {
			int i = f[j - k - 1];
			while (i != -1 && word.charAt(j) != word.charAt(k + i + 1)) {
				if (word.charAt(j) < word.charAt(k + i + 1)) {
					k = j - i - 1;
				}
				i = f[i];
			}
			if (i == -1 && word.charAt(j) != word.charAt(k + i + 1)) {
				if (word.charAt(j) < word.charAt(k + i + 1)) {
					k = j;
				}
				f[j - k] = -1;
			} else {
				f[j - k] = i + 1;
			}
		}
		return word.substring(k, k + n);
	}
}
