package pal;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("file.txt"));
		read();
    }

	public static void read() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		StringTokenizer st;
		if ((line = br.readLine()) != null) {
			st = new StringTokenizer(line);
		}
	}
}
