package OctagonApp;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UniqueNumbGen {
    private Set<Integer> numbers;
    private Random randomGenerator;
    private int leftBound, rightBound, rangeSize;

    public UniqueNumbGen(int leftBound, int rightBound) {
        numbers = new HashSet<>();
        randomGenerator = new Random();
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.rangeSize = rightBound - leftBound + 1;
    }

    public UniqueNumbGen() {
        numbers = new HashSet<>();
        randomGenerator = new Random();
        this.leftBound = this.rightBound = this.rangeSize = -1;
    }

    public int getNext() {
        int num;
        if (numbers.size()>=rangeSize && rangeSize!=-1) {
            throw new IllegalStateException("All of the numbers from range have been generated");
        }
        do {
            num = (rangeSize==-1) ? randomGenerator.nextInt() :
                    this.leftBound + randomGenerator.nextInt(rangeSize);
        } while (numbers.contains(num));
        numbers.add(num);
        return num;
    }
}
