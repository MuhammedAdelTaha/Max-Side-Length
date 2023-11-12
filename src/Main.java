import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    private static final String RED = "\033[0;31m";
    private static final String RESET = "\033[0m";
    public static void writeRandArray(String pathname, int size, int lowerBound, int upperBound) {
        File file = new File(pathname);
        File path = file.getParentFile();
        if (path != null)
            path.mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathname))) {
            writer.write(size + "\n");
            Set<AbstractMap.SimpleEntry<Integer, Integer>> exceptions = new HashSet<>();
            for (int i = 0; i < size; i++) {
                try {
                    Random random = new Random();
                    AbstractMap.SimpleEntry<Integer, Integer> point = new AbstractMap.SimpleEntry<>(
                            random.nextInt(lowerBound, upperBound), random.nextInt(lowerBound, upperBound));

                    while (exceptions.contains(point)) {
                        point = new AbstractMap.SimpleEntry<>(random.nextInt(lowerBound, upperBound),
                                random.nextInt(lowerBound, upperBound));
                    }
                    exceptions.add(point);
                    writer.write(point.getKey() + " " + point.getValue() + "\n");
                } catch (IOException e) {
                    System.out.println(RED + "Error happened when writing the file..." + RESET);
                }
            }
        } catch (IOException e) {
            System.out.println(RED + "Enter a valid file name..." + RESET);
        }
    }

    public static void main(String[] args) {
        MaxSideLength mx = new MaxSideLength();
        System.out.println(mx.solve("test4.txt"));
    }
}
