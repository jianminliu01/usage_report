package com.prototype.cellusage.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.springframework.stereotype.Repository;

@Repository
public class CsvReader {
  public <T> Set<T> read (String fileName, Function<String, T> convert) {
    Set<T> results = new HashSet<>();
    try {
      InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
      BufferedReader bf = new BufferedReader(new InputStreamReader(is));
      String line = bf.readLine();
      //skip file header, for a real-world project, validate the header
      while ((line = bf.readLine()) != null) {
        results.add(convert.apply(line));
      }
      return results;
    } catch (IOException e) {
      throw new RuntimeException("Failed read file: " + fileName, e);
    }
  }
}
