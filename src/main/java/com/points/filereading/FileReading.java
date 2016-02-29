package com.points.filereading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReading {
	
	public static List<String> readFile(String fileName ) {		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName))) ) {
			List<String> fileLines = new ArrayList<>();
			for (String line; (line = bufferedReader.readLine()) != null; ) {
				fileLines.add(line);
			}
			return fileLines;
		} catch (IOException e ) {
			return null;
		}
	}
}
