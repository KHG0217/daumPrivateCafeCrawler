package com.tapacross.sns.crawler.daum.cafe.util;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class JavaCompileVersionTest {
	public static void main(String[] args) throws IOException {
		String dir = System.getProperty("user.dir");
		if (args.length < 1) {
			System.err
					.println("Usage:JavaCompileVersionTest <알고자 하는 .class파일의 경로:/package path/filename.class");
			System.exit(1);
		}
		checkClassVersion(dir + args[0]);
	}

	private static void checkClassVersion(String filename) throws IOException {
		DataInputStream in = new DataInputStream(new FileInputStream(filename));

		int magic = in.readInt();
		if (magic != 0xcafebabe) {
			System.out.println(filename + " is not a valid class!");
			;
		}
		int minor = in.readUnsignedShort();
		int major = in.readUnsignedShort();
		System.out.println(filename + ": " + major + " . " + minor);
		in.close();
	}
}
