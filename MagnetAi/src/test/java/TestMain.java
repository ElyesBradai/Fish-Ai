import com.example.magnetai.MathFunctions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TestMain {


    @Test
    public void testCrossProduct1() {

        double[] result = MathFunctions.crossProduct(new double[]{0,0,1},new double[]{1,0,0});
        assertArrayEquals(new double[]{0,1,0}, result, "cross product result should be {0,1,0}");
    }
    @Test
    public void testCrossProduct2() {

        double[] result = MathFunctions.crossProduct(new double[]{0,0,0},new double[]{0,0,0});
        assertArrayEquals(new double[]{0,0,0}, result, "cross product result should be {0,0,0}");
    }
    @Test
    public void testCrossProduct3() {
        assertThrows(IllegalArgumentException.class, () -> {
            MathFunctions.crossProduct(null, new double[]{1, 0, 0});
        },"cross product result should throw an exception");
    }

}
