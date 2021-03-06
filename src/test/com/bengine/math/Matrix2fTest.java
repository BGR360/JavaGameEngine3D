/**
 * Created by Ben on 8/26/15.
 *
 * Unit test for Matrix2f
 */

package com.bengine.math;

import junit.framework.TestCase;

public class Matrix2fTest extends TestCase
{

    static class Util
    {
        public static void assertFloatArrayEquals(float[][] expected, float[][] actual)
        {
            int rows = expected.length;
            int cols = expected[0].length;
            assertEquals("Rows: ", rows, actual.length);
            assertEquals("Cols: ", cols, actual[0].length);
            for(int i = 0; i < rows; i++)
            {
                for(int j = 0; j < cols; j++)
                {
                    assertEquals(
                            "At [" + i + "][" + j + "]: ",
                            expected[i][j], actual[i][j]
                    );
                }
            }
        }
    }

    public void testIndexOutOfBounds() throws Exception
    {
        Matrix2f mat = new Matrix2f();

        try
        {
            float value = mat.get(7, -1);
            fail("get() did not throw ArrayIndexOutOfBoundsException");
        }
        catch(ArrayIndexOutOfBoundsException ignored) {}

        try
        {
            mat.set(-1, 5, 0.0f);
            fail("set() did not throw ArrayIndexOutOfBoundsException");
        }
        catch(ArrayIndexOutOfBoundsException ignored) {}
    }

    public void testArrayCopy() throws Exception
    {
        Matrix2f src = new Matrix2f(
                1, 2,
                0, 1
        );
        float[][] arrayref = src.getArray();
        float[][] array = src.copyArray();
        Matrix2f copy = src.copy();

        float[][] newArray =
                {
                        {1, 2},
                        {3, 4}
                };
        src.setArray(newArray);

        float[][] originalArray =
                {
                        {1, 2},
                        {0, 1}
                };

        Util.assertFloatArrayEquals(newArray, src.getArray());
        Util.assertFloatArrayEquals(originalArray, arrayref);
        Util.assertFloatArrayEquals(originalArray, array);
        assertEquals(new Matrix2f(originalArray), copy);
    }

    public void testAdjugate() throws Exception
    {
        Matrix2f matrix = new Matrix2f(
            1, 2,
            3, 4
        );
        Matrix2f expected = new Matrix2f(
            4, -2,
            -3, 1
        );

        Matrix2f result = Matrix2f.adjugate(matrix);
        assertEquals(expected, result);
    }

    public void testDeterminant() throws Exception
    {
        Matrix2f matrix = new Matrix2f(
            1, 2,
            3, 4);
        float expected = -2.0f;

        float result = Matrix2f.determinant(matrix);

        assertEquals(expected, result);
    }

    public void testInverse() throws Exception
    {
        Matrix2f matrix = new Matrix2f(
            1, 2,
            3, 4);
        Matrix2f expected = new Matrix2f(
            -2f, 1f,
            1.5f, -0.5f);

        Matrix2f result = Matrix2f.inverse(matrix);

        assertEquals(expected, result);
    }

    public void testTranspose() throws Exception
    {
        Matrix2f matrix = new Matrix2f(
            1, 2,
            3, 4);
        Matrix2f expected = new Matrix2f(
            1, 3,
            2, 4);

        Matrix2f result = Matrix2f.transpose(matrix);
        assertEquals(expected, result);
    }

    public void testScale() throws Exception
    {
        Vector2f vec = new Vector2f(-1, 3);
        Vector2f scale = new Vector2f(2, 4);
        Vector2f expected = new Vector2f(-2, 12);

        Vector2f result = Matrix2f.scale(scale).mul(vec);

        assertEquals(expected, result);
    }

    public void testRotate() throws Exception
    {
        Vector2f vector = new Vector2f(2, 0);
        Matrix2f op = Matrix2f.rotate(45);
        Vector2f expected = new Vector2f((float)Math.sqrt(2), (float)Math.sqrt(2));

        Vector2f result = op.mul(vector);

        assertEquals(expected, result);
    }

    public void testGetSet() throws Exception
    {
        Matrix2f mat = new Matrix2f();
        float a = mat.get(0, 0);
        float b = mat.get(1, 1);
        float c = mat.get(0, 1);
        assertEquals(1.0f, a);
        assertEquals(1.0f, b);
        assertEquals(0.0f, c);

        mat.set(0, 0, 1);
        mat.set(0, 1, 2);
        mat.set(1, 0, 3);
        mat.set(1, 1, 4);
        
        a = mat.get(0, 0);
        b = mat.get(1, 1);
        c = mat.get(0, 1);
        assertEquals(1.0f, a);
        assertEquals(4.0f, b);
        assertEquals(2.0f, c);
    }

    public void testAdd() throws Exception
    {
        Matrix2f left = new Matrix2f(
            1, 2,
            3, 4);
        Matrix2f right = new Matrix2f(
            5, 2,
            -5, 4);
        Matrix2f expected = new Matrix2f(
            6, 4,
            -2, 8);

        Matrix2f result = left.add(right);
        assertEquals(expected, result);
    }

    public void testSub() throws Exception
    {
        Matrix2f left = new Matrix2f(
            1, 2,
            3, 4);
        Matrix2f right = new Matrix2f(
            5, 2,
            -5, 4);
        Matrix2f expected = new Matrix2f(
            -4, 0,
            8, 0);

        Matrix2f result = left.sub(right);
        assertEquals(expected, result);
    }

    public void testMatrixMul() throws Exception
    {
        Matrix2f left = new Matrix2f(
            1, 2,
            -3, 4);
        Matrix2f right = new Matrix2f(
            5, 2,
            1, -1);
        Matrix2f expected = new Matrix2f(
            7, 0,
            -11, -10);

        Matrix2f result = left.mul(right);
        assertEquals(expected, result);

        expected = new Matrix2f(
                -1, 18,
                4, -2);
        result = right.mul(left);
        assertEquals(expected, result);
    }

    public void testScalarMul() throws Exception
    {
        float op = 2.0f;
        Matrix2f matrix = new Matrix2f(
            1, 2,
            3, 4);
        Matrix2f expected = new Matrix2f(
            2, 4,
            6, 8);

        Matrix2f result = matrix.mul(op);

        assertEquals(expected, result);
    }

    public void testVectorMul() throws Exception
    {
        Matrix2f op = new Matrix2f(
            1, 2,
            3, 4);
        Vector2f vec = new Vector2f(7, -6);
        Vector2f expected = new Vector2f(-5, -3);

        Vector2f result = op.mul(vec);
        assertEquals(expected, result);
    }
}