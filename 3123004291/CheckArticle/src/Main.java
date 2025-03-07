import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.text.DecimalFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        String originalPath = "F:\\IntelliJ IDEA 2023.3.5\\projects\\testscheck\\org.txt";
        String plagiarizedPath = "F:\\IntelliJ IDEA 2023.3.5\\projects\\testscheck\\org_add.txt";
        String outputPath = "F:\\IntelliJ IDEA 2023.3.5\\projects\\testscheck\\ans.txt";

        try {
            // 读取文件内容
            String originalText = PlagiarismChecker.readFile(originalPath);
            String plagiarizedText = PlagiarismChecker.readFile(plagiarizedPath);

            // 分词并统计词频
            Map<String, Integer> wordFreqOrig = PlagiarismChecker.getWordFrequency(originalText);
            Map<String, Integer> wordFreqPlag = PlagiarismChecker.getWordFrequency(plagiarizedText);


            // 计算余弦相似度
            double similarity = PlagiarismChecker.calculateCosineSimilarity(wordFreqOrig, wordFreqPlag);

            System.out.println("重复率为: " + similarity);

            // 输出结果
            PlagiarismChecker.writeResult(outputPath, similarity);

        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
            System.exit(1);
        }
    }
}

